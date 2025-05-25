package com.ypy.pyojbackenduserservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ypy.pyojbackendcommon.app.AppResponse;
import com.ypy.pyojbackendcommon.exception.AppException;
import com.ypy.pyojbackendcommon.model.dto.UserAuthDTO;
import com.ypy.pyojbackendcommon.model.entity.User;
import com.ypy.pyojbackendcommon.model.request.UserAuthRequest;
import com.ypy.pyojbackendcommon.model.request.UserUpdateRequest;
import com.ypy.pyojbackendcommon.model.vo.UserVO;

import javax.servlet.http.HttpServletRequest;

public interface UserService extends IService<User> {

    /**
     * length username and password between 4 - 16, contains only a-z, A-Z, 0-9, and !@#$%^&*
     * implement step:
     * 1. is username and password valid?
     * 2. have username already existed?
     * 3. encrypt password, and save username and encrypted password into database
     *
     * @param userAuthRequest
     * @return
     * @throws AppException
     */
    AppResponse<Void> register(UserAuthRequest userAuthRequest) throws AppException;

    /**
     * implement step:
     * 1. encrypt password
     * 2. select in database
     *
     * @param request
     * @return
     * @throws AppException
     */
    AppResponse<String> login(UserAuthRequest userAuthRequest, HttpServletRequest request) throws AppException;

    AppResponse<UserVO> getLoginUserVO(HttpServletRequest request) throws AppException;

    AppResponse<Void> logout(HttpServletRequest request) throws AppException;

    AppResponse<Void> resetPassword(UserAuthRequest userAuthRequest, HttpServletRequest request) throws AppException;

    AppResponse<UserVO> userUpdate(UserUpdateRequest userUpdateRequest, HttpServletRequest request) throws AppException;
}
