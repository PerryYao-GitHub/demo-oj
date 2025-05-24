package com.ypy.pyojbackenduserservice.controller;

import com.ypy.pyojbackendcommon.app.AppResponse;
import com.ypy.pyojbackendcommon.exception.AppException;
import com.ypy.pyojbackendcommon.model.request.UserAuthRequest;
import com.ypy.pyojbackendcommon.model.request.UserUpdateRequest;
import com.ypy.pyojbackendcommon.model.vo.UserVO;
import com.ypy.pyojbackenduserservice.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public AppResponse<Void> register(@RequestBody UserAuthRequest userAuthRequest) throws AppException {
        return userService.register(userAuthRequest);
    }

    @PostMapping("/login")
    public AppResponse<String> login(@RequestBody UserAuthRequest userAuthRequest, HttpServletRequest request) throws AppException {
        return userService.login(userAuthRequest, request);
    }

    @GetMapping
    public AppResponse<UserVO> getCurrentUser(HttpServletRequest request) throws AppException {
        return userService.getLoginUserVO(request);
    }

    @GetMapping("/logout")
    public AppResponse<Void> logout(HttpServletRequest request) throws AppException {
        return userService.logout(request);
    }

    @PostMapping("/reset/password")
    public AppResponse<Void> resetPassword(@RequestBody UserAuthRequest userAuthRequest, HttpServletRequest request) throws AppException {
        return userService.resetPassword(userAuthRequest, request);
    }

    @PostMapping("/update")
    public AppResponse<UserVO> update(@RequestBody UserUpdateRequest userUpdateRequest, HttpServletRequest request) throws AppException {
        return userService.userUpdate(userUpdateRequest, request);
    }
}
