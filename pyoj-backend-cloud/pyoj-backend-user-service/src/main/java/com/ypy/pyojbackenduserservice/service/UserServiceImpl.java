package com.ypy.pyojbackenduserservice.service;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ypy.pyojbackendcommon.app.AppCode;
import com.ypy.pyojbackendcommon.app.AppResponse;
import com.ypy.pyojbackendcommon.exception.AppException;
import com.ypy.pyojbackendcommon.model.dto.UserAuthDTO;
import com.ypy.pyojbackendcommon.model.entity.User;
import com.ypy.pyojbackendcommon.model.enums.TagEnum;
import com.ypy.pyojbackendcommon.model.enums.UserRoleEnum;
import com.ypy.pyojbackendcommon.model.request.UserAuthRequest;
import com.ypy.pyojbackendcommon.model.request.UserUpdateRequest;
import com.ypy.pyojbackendcommon.model.vo.UserVO;
import com.ypy.pyojbackenduserservice.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private static final String SALT = "pyojpasswordsaltvalue";

    private static final String VALID_REGEX = "^[a-zA-Z0-9!@#$%^&*\\-_+]{4,16}$";

    private static final String LOCK_PREFIX = "pyoj:user-service:register:lock:";

    private static final String SESSION_ATTRIBUTE = "loginUser";

    @Resource
    private RedissonClient redissonClient; // for redis distributed lock

    private UserVO toUserVO(User user) {
        UserVO vo = new UserVO();
        vo.setId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setRole(UserRoleEnum.value2text.get(user.getRole()));
        List<Integer> tags = user.getTags() == null ? Collections.emptyList() : user.getTags();
        vo.setTags(tags.stream().map(TagEnum.value2text::get).collect(Collectors.toList()));
        return vo;
    }

    private UserAuthDTO toUserAuthDTO(User user) {
        UserAuthDTO dto = new UserAuthDTO();
        dto.setId(user.getId());
        dto.setRole(user.getRole());
        return dto;
    }

    private User toUser(UserAuthRequest userAuthRequest) throws AppException {
        if (!userAuthRequest.getUsername().matches(VALID_REGEX) || !userAuthRequest.getPassword().matches(VALID_REGEX)) throw new AppException(AppCode.ERR_INVALID_USR_PWD);
        User user = new User();
        user.setUsername(userAuthRequest.getUsername());
        user.setPassword(userAuthRequest.getPassword());
        return user;
    }

    private void toUser(UserUpdateRequest userUpdateRequest, User user) {
        List<String> tags = userUpdateRequest.getTags() == null ? Collections.emptyList() : userUpdateRequest.getTags();
        user.setTags(tags.stream()
                .map(TagEnum.text2value::get)
                .collect(Collectors.toList())
        );
    }

    @Override
    public UserAuthDTO getLoginUserAuthDTO(HttpServletRequest request) throws AppException {
        return (UserAuthDTO) request.getSession().getAttribute(SESSION_ATTRIBUTE);
    }

    @Override
    public AppResponse<Void> register(UserAuthRequest userAuthRequest) throws AppException {
        User user = toUser(userAuthRequest);
        String lockKey = LOCK_PREFIX + user.getUsername();
        RLock lk = redissonClient.getLock(lockKey);
        boolean acquired = false;
        try {
            acquired = lk.tryLock(3, 10, TimeUnit.SECONDS); // 10s 过期, 3s重试
            if (!acquired) throw new AppException(AppCode.ERR_SYSTEM);

            QueryWrapper<User> qw = new QueryWrapper<>();
            qw.eq("username", user.getUsername());
            if (baseMapper.exists(qw)) throw new AppException(AppCode.ERR_SAME_USERNAME);
            String encryptedPassword = DigestUtil.md5Hex(SALT + user.getPassword());
            user.setPassword(encryptedPassword);
            user.setRole(UserRoleEnum.USER.getValue());
            if (!save(user)) throw new AppException(AppCode.ERR_SYSTEM);
            return new AppResponse<>(AppCode.OK, null);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        } finally {
            if (acquired && lk.isHeldByCurrentThread()) lk.unlock();
            /*
            tryLock() 不一定拿到锁（可能是 acquired == false）；
            或者当前线程已经被中断；
            更重要：Redisson 的锁是“可重入锁”，必须由持有锁的线程释放！
            否则调用 unlock() 会抛出 IllegalMonitorStateException（不是持有者却试图释放锁）
            */
        }
        return new AppResponse<>(AppCode.ERR_SYSTEM, null);
    }

    @Override
    public AppResponse<UserVO> login(UserAuthRequest userAuthRequest, HttpServletRequest request) throws AppException {
        toUser(userAuthRequest); // valid user

        String encryptedPassword = DigestUtil.md5Hex(SALT + userAuthRequest.getPassword());
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("username", userAuthRequest.getUsername());
        qw.eq("password", encryptedPassword);
        User user = baseMapper.selectOne(qw);
        if (user == null) throw new AppException(AppCode.ERR_WRONG_USR_PWD);

        // session login
        UserAuthDTO dto = toUserAuthDTO(user);
        request.getSession().setAttribute(SESSION_ATTRIBUTE, dto);

        return new AppResponse<>(AppCode.OK, toUserVO(user));
        // vo.setToken(TokenUtil.gen(...)); // use jwt token
    }

    @Override
    public AppResponse<UserVO> getLoginUserVO(HttpServletRequest request) throws AppException {
        return new AppResponse<>(AppCode.OK, toUserVO(getById(getLoginUserAuthDTO(request))));
    }

    @Override
    public AppResponse<Void> logout(HttpServletRequest request) throws AppException {
        request.getSession().removeAttribute(SESSION_ATTRIBUTE);
        return new AppResponse<>(AppCode.OK, null);
    }

    @Override
    public AppResponse<Void> resetPassword(UserAuthRequest userAuthRequest, HttpServletRequest request) throws AppException {
        toUser(userAuthRequest);
        User user = getById(getLoginUserAuthDTO(request));
        if (!user.getUsername().equals(userAuthRequest.getUsername())) throw new AppException(AppCode.ERR_FORBIDDEN);

        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("username", userAuthRequest.getUsername());
        qw.eq("password", DigestUtil.md5Hex(SALT + userAuthRequest.getOldPassword()));
        if (count(qw) != 1) throw new AppException(AppCode.ERR_WRONG_USR_PWD);

        user.setPassword(DigestUtil.md5Hex(SALT + userAuthRequest.getPassword()));
        if (!updateById(user)) throw new AppException(AppCode.ERR_SYSTEM);
        logout(request);
        return new AppResponse<>(AppCode.OK, null);
    }

    @Override
    public AppResponse<UserVO> userUpdate(UserUpdateRequest userUpdateRequest, HttpServletRequest request) throws AppException {
        User user = getById(getLoginUserAuthDTO(request));
        toUser(userUpdateRequest, user);
        if (!updateById(user)) throw new AppException(AppCode.ERR_SYSTEM);
        return new AppResponse<>(AppCode.OK, null);
    }
}
