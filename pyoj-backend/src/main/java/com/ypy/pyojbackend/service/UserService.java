package com.ypy.pyojbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ypy.pyojbackend.common.AppResponse;
import com.ypy.pyojbackend.exception.AppException;
import com.ypy.pyojbackend.model.entity.User;
import com.ypy.pyojbackend.model.vo.UserVO;

public interface UserService extends IService<User> {

    /**
     * length username and password between 4 - 16, contains only a-z, A-Z, 0-9, and !@#$%^&*
     * @param user
     * @return
     * @throws AppException
     */
    AppResponse<?> register(User user) throws AppException;

    /**
     *
     * @param user
     * @return
     * @throws AppException
     */
    AppResponse<UserVO> login(User user) throws AppException;

}
