package com.ypy.pyojbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ypy.pyojbackend.app.AppResponse;
import com.ypy.pyojbackend.exception.AppException;
import com.ypy.pyojbackend.model.dto.UserAuthDTO;
import com.ypy.pyojbackend.model.entity.User;
import com.ypy.pyojbackend.model.request.UserAuthRequest;
import com.ypy.pyojbackend.model.request.UserUpdateRequest;
import com.ypy.pyojbackend.model.vo.UserVO;

import javax.servlet.http.HttpServletRequest;

public interface UserService extends IService<User> {

    /**
     * get login user through session or jwt
     * @param request
     * @return
     * @throws AppException
     */
    UserAuthDTO getLoginUserAuthDTO(HttpServletRequest request) throws AppException;

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
    AppResponse<?> register(UserAuthRequest userAuthRequest) throws AppException;

    /**
     * implement step:
     * 1. encrypt password
     * 2. select in database
     *
     * @param request
     * @return
     * @throws AppException
     */
    AppResponse<UserVO> login(UserAuthRequest userAuthRequest, HttpServletRequest request) throws AppException;

    AppResponse<UserVO> getLoginUserVO(HttpServletRequest request) throws AppException;

    AppResponse<?> logout(HttpServletRequest request) throws AppException;

    AppResponse<?> resetPassword(UserAuthRequest userAuthRequest, HttpServletRequest request) throws AppException;

    AppResponse<UserVO> userUpdate(UserUpdateRequest userUpdateRequest, HttpServletRequest request) throws AppException;
}
