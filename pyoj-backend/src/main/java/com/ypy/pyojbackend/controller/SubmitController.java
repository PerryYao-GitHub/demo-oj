package com.ypy.pyojbackend.controller;

import com.ypy.pyojbackend.common.AppResponse;
import com.ypy.pyojbackend.exception.AppException;
import com.ypy.pyojbackend.model.entity.Submit;
import com.ypy.pyojbackend.model.entity.User;
import com.ypy.pyojbackend.model.request.SubmitRequest;
import com.ypy.pyojbackend.service.SubmitService;
import com.ypy.pyojbackend.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/submit")
public class SubmitController {

    @Resource
    private SubmitService submitService;

    @Resource
    private UserService userService;

    @PostMapping("")
    public AppResponse<?> submit(HttpServletRequest request, @RequestBody SubmitRequest submitRequest) throws AppException {
        User loginUser = userService.getLoginUser(request);
        Submit submit = submitService.toSubmit(submitRequest);
        submit.setUserId(loginUser.getId());
        return submitService.doSubmit(submit);
    }
}
