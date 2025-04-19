package com.ypy.pyojbackend.controller;

import com.ypy.pyojbackend.aop.AuthCheck;
import com.ypy.pyojbackend.common.AppResponse;
import com.ypy.pyojbackend.common.UserRoleEnum;
import com.ypy.pyojbackend.exception.AppException;
import com.ypy.pyojbackend.model.request.QuestionRequest;
import com.ypy.pyojbackend.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @AuthCheck(mustRole = UserRoleEnum.ADMIN)
    @PostMapping("/create")
    public AppResponse<?> adminCreateQuestion(@RequestBody QuestionRequest questionRequest) throws AppException {
        return questionService.createQuestion(questionService.toQuestion(questionRequest));
    }

    @AuthCheck(mustRole = UserRoleEnum.ADMIN)
    @PostMapping("/update")
    public AppResponse<?> adminUpdateQuestion(@RequestBody QuestionRequest questionRequest) throws AppException {
        return questionService.updateQuestion(questionService.toQuestion(questionRequest));
    }

    @AuthCheck(mustRole = UserRoleEnum.ADMIN)
    @DeleteMapping("/delete/{id}")
    public AppResponse<?> adminDeleteQuestion(@PathVariable Long id) throws AppException {
        return questionService.deleteQuestion(id);
    }
}
