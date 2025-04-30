package com.ypy.pyojbackend.controller;

import com.ypy.pyojbackend.aop.AuthCheck;
import com.ypy.pyojbackend.app.AppCode;
import com.ypy.pyojbackend.app.AppResponse;
import com.ypy.pyojbackend.model.enums.UserRoleEnum;
import com.ypy.pyojbackend.exception.AppException;
import com.ypy.pyojbackend.job.QuestionSyncTask;
import com.ypy.pyojbackend.model.request.QuestionRequest;
import com.ypy.pyojbackend.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/admin")
@AuthCheck(mustRole = UserRoleEnum.ADMIN)
public class AdminController {

    @Resource
    private QuestionSyncTask questionSyncTask;

    @Resource
    private QuestionService questionService;

    @GetMapping("/sync/question")
    public AppResponse<?> syncQuestion() {
        questionSyncTask.syncAll();
        return new AppResponse<>(AppCode.OK, null);
    }

    @PostMapping("/create/question")
    public AppResponse<?> adminCreateQuestion(@RequestBody QuestionRequest questionRequest) throws AppException {
        return questionService.createQuestion(questionService.toQuestion(questionRequest));
    }

    @PostMapping("/update/question")
    public AppResponse<?> adminUpdateQuestion(@RequestBody QuestionRequest questionRequest) throws AppException {
        return questionService.updateQuestion(questionService.toQuestion(questionRequest));
    }

    @DeleteMapping("/delete/question/{id}")
    public AppResponse<?> adminDeleteQuestion(@PathVariable Long id) throws AppException {
        return questionService.deleteQuestion(id);
    }
}
