package com.ypy.pyojbackendquestionservice.controller;

import com.ypy.pyojbackendcommon.app.AppResponse;
import com.ypy.pyojbackendcommon.exception.AppException;
import com.ypy.pyojbackendcommon.model.enums.UserRoleEnum;
import com.ypy.pyojbackendcommon.model.request.QuestionRequest;
import com.ypy.pyojbackendquestionservice.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("")
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
