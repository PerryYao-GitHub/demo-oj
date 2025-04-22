package com.ypy.pyojbackend.controller;

import com.ypy.pyojbackend.aop.LoginRequired;
import com.ypy.pyojbackend.common.AppResponse;
import com.ypy.pyojbackend.exception.AppException;
import com.ypy.pyojbackend.model.query.QuestionPageQuery;
import com.ypy.pyojbackend.model.vo.QuestionPageVO;
import com.ypy.pyojbackend.model.vo.QuestionVO;
import com.ypy.pyojbackend.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/question")
public class QuestionController {

    @Resource
    private QuestionService questionService;

    @LoginRequired
    @GetMapping("/{id}")
    public AppResponse<QuestionVO> getOneQuestion(@PathVariable long id) throws AppException {
        return questionService.getOneQuestion(id);
    }

    @PostMapping("/list")
    public AppResponse<List<QuestionPageVO>> getAllQuestions(@RequestBody QuestionPageQuery questionPageQuery) throws AppException {
        return questionService.getQuestionPage(questionPageQuery);
    }
}
