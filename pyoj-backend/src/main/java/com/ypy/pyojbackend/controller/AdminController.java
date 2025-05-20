package com.ypy.pyojbackend.controller;

import com.ypy.pyojbackend.aop.AuthCheck;
import com.ypy.pyojbackend.app.AppResponse;
import com.ypy.pyojbackend.exception.AppException;
import com.ypy.pyojbackend.model.enums.UserRoleEnum;
import com.ypy.pyojbackend.model.request.QuestionRequest;
import com.ypy.pyojbackend.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/admin")
@AuthCheck(mustRole = UserRoleEnum.ADMIN)
public class AdminController {

    @Resource
    private QuestionService questionService;

    @GetMapping("/sync/question")
    public AppResponse<Void> syncQuestion() {
        return questionService.syncQuestion();
    }

    @PostMapping("/create/question")
    public AppResponse<Void> adminCreateQuestion(@RequestBody QuestionRequest questionRequest) throws AppException {
        return questionService.createQuestion(questionRequest);
    }

    @PostMapping("/update/question")
    public AppResponse<Void> adminUpdateQuestion(@RequestBody QuestionRequest questionRequest) throws AppException {
        return questionService.updateQuestion(questionRequest);
    }

    @DeleteMapping("/delete/question/{id}")
    public AppResponse<Void> adminDeleteQuestion(@PathVariable Long id) throws AppException {
        return questionService.deleteQuestion(id);
    }
}
