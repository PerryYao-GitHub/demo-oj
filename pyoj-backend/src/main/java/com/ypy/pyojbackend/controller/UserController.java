package com.ypy.pyojbackend.controller;

import com.ypy.pyojbackend.common.AppResponse;
import com.ypy.pyojbackend.exception.AppException;
import com.ypy.pyojbackend.model.request.UserAuthRequest;
import com.ypy.pyojbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public AppResponse<?> register(@RequestBody UserAuthRequest userAuthRequest) throws AppException {
        return userService.register(userService.toUser(userAuthRequest));
    }

    @PostMapping("/login")
    public AppResponse<?> login(@RequestBody UserAuthRequest userAuthRequest) throws AppException {
        return userService.login(userService.toUser(userAuthRequest));
    }
}
