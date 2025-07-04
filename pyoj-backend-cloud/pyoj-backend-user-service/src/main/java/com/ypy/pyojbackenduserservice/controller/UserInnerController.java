package com.ypy.pyojbackenduserservice.controller;

import com.ypy.pyojbackendcommon.feignclient.UserFeignClient;
import com.ypy.pyojbackendcommon.model.entity.User;
import com.ypy.pyojbackenduserservice.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Hidden
@RestController
@RequestMapping("/inner")
public class UserInnerController implements UserFeignClient {

    @Resource
    private UserService userService;

    @Override
    @GetMapping("/get/by/id")
    public User getById(
            @RequestParam Long id
    ) {
        return userService.getById(id);
    }
}
