package com.ypy.pyojbackend.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ypy.pyojbackend.common.AppCode;
import com.ypy.pyojbackend.common.AppResponse;
import com.ypy.pyojbackend.exception.AppException;
import com.ypy.pyojbackend.mapper.UserMapper;
import com.ypy.pyojbackend.model.entity.User;
import com.ypy.pyojbackend.common.UserRoleEnum;
import com.ypy.pyojbackend.model.request.UserAuthRequest;
import com.ypy.pyojbackend.model.vo.UserVO;
import com.ypy.pyojbackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private static final String SALT = "pyojpasswordsaltvalue";

    private static final String VALID_REGEX = "^[a-zA-Z0-9!@#$%^&*\\-_+]{4,16}$";

    private static final String LOCK_PREFIX = "pyoj:user-service:register:lock:";

    @Resource
    private RedissonClient redissonClient; // for redis distributed lock

    @Override
    public User toUser(UserAuthRequest userAuthRequest) throws AppException {
        if (!userAuthRequest.getUsername().matches(VALID_REGEX) || !userAuthRequest.getPassword().matches(VALID_REGEX)) throw new AppException(AppCode.ERR_INVALID_USR_PWD);
        User user = new User();
        user.setUsername(userAuthRequest.getUsername());
        user.setPassword(userAuthRequest.getPassword());
        return user;
    }

    @Override
    public User getLoginUser(HttpServletRequest request) throws AppException {
        User fakeUser = new User();
        fakeUser.setUsername("admin-ypy");
        fakeUser.setPassword("admin-ypy");
        fakeUser.setRole(UserRoleEnum.ADMIN);
        return fakeUser;
    }

    @Override
    public AppResponse<?> register(User user) throws AppException {
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
            user.setRole(UserRoleEnum.USER);
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
    public AppResponse<UserVO> login(User user) throws AppException {
        QueryWrapper<User> qw = new QueryWrapper<>();
        String encryptedPassword = DigestUtil.md5Hex(SALT + user.getPassword());
        qw.eq("username", user.getUsername());
        qw.eq("password", encryptedPassword);
        User selectedUser = baseMapper.selectOne(qw);
        if (selectedUser == null) throw new AppException(AppCode.ERR_WRONG_USR_PWD);
        // todo: distributed-session or jwt
        return new AppResponse<>(AppCode.OK, UserVO.fromUser(selectedUser));
    }
}
