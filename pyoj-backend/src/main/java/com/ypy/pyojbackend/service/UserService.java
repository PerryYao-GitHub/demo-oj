package com.ypy.pyojbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ypy.pyojbackend.common.AppResponse;
import com.ypy.pyojbackend.exception.AppException;
import com.ypy.pyojbackend.model.entity.User;
import com.ypy.pyojbackend.model.request.UserAuthRequest;
import com.ypy.pyojbackend.model.vo.UserVO;

import javax.servlet.http.HttpServletRequest;

public interface UserService extends IService<User> {
    User toUser(UserAuthRequest userAuthRequest) throws AppException;

    /**
     * get login user through session or jwt
     * @param request
     * @return
     * @throws AppException
     */
    User getLoginUser(HttpServletRequest request) throws AppException;

    /**
     * length username and password between 4 - 16, contains only a-z, A-Z, 0-9, and !@#$%^&*
     * implement step:
     * 1. is username and password valid?
     * 2. have username already existed?
     * 3. encrypt password, and save username and encrypted password into database
     *
     * @param user
     * @return
     * @throws AppException
     */
    AppResponse<?> register(User user) throws AppException;

    /**
     * implement step:
     * 1. encrypt password
     * 2. select in database
     *
     * @param user
     * @return
     * @throws AppException
     */
    AppResponse<UserVO> login(User user) throws AppException;

}
