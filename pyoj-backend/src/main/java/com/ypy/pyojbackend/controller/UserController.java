package com.ypy.pyojbackend.controller;

import com.ypy.pyojbackend.aop.LoginRequired;
import com.ypy.pyojbackend.app.AppResponse;
import com.ypy.pyojbackend.exception.AppException;
import com.ypy.pyojbackend.model.request.UserAuthRequest;
import com.ypy.pyojbackend.model.request.UserUpdateRequest;
import com.ypy.pyojbackend.model.vo.UserVO;
import com.ypy.pyojbackend.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public AppResponse<Void> register(@RequestBody UserAuthRequest userAuthRequest) throws AppException {
        return userService.register(userAuthRequest);
    }

    @PostMapping("/login")
    public AppResponse<UserVO> login(@RequestBody UserAuthRequest userAuthRequest, HttpServletRequest request) throws AppException {
        return userService.login(userAuthRequest, request);
    }

    @LoginRequired
    @GetMapping("")
    public AppResponse<UserVO> getCurrentUser(HttpServletRequest request) throws AppException {
        return userService.getLoginUserVO(request);
    }

    @LoginRequired
    @GetMapping("/logout")
    public AppResponse<Void> logout(HttpServletRequest request) throws AppException {
        return userService.logout(request);
    }

    @LoginRequired
    @PostMapping("/reset/password")
    public AppResponse<Void> resetPassword(@RequestBody UserAuthRequest userAuthRequest, HttpServletRequest request) throws AppException {
        return userService.resetPassword(userAuthRequest, request);
    }

    @LoginRequired
    @PostMapping("/update")
    public AppResponse<UserVO> update(@RequestBody UserUpdateRequest userUpdateRequest, HttpServletRequest request) throws AppException {
        return userService.userUpdate(userUpdateRequest, request);
    }
}
