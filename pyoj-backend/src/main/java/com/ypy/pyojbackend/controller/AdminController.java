package com.ypy.pyojbackend.controller;

import com.ypy.pyojbackend.aop.AuthCheck;
import com.ypy.pyojbackend.common.AppCode;
import com.ypy.pyojbackend.common.AppResponse;
import com.ypy.pyojbackend.common.UserRoleEnum;
import com.ypy.pyojbackend.job.QuestionSyncTask;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/admin")
@AuthCheck(mustRole = UserRoleEnum.ADMIN)
public class AdminController {

    @Resource
    private QuestionSyncTask questionSyncTask;

    @GetMapping("/sync/question")
    public AppResponse<?> syncQuestion() {
        questionSyncTask.syncAll();
        return new AppResponse<>(AppCode.OK, null);
    }
}
