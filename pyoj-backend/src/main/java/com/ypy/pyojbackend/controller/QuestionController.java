package com.ypy.pyojbackend.controller;

import com.ypy.pyojbackend.aop.LoginRequired;
import com.ypy.pyojbackend.app.AppResponse;
import com.ypy.pyojbackend.exception.AppException;
import com.ypy.pyojbackend.model.query.QuestionPageQuery;
import com.ypy.pyojbackend.model.vo.PageVO;
import com.ypy.pyojbackend.model.vo.QuestionBriefVO;
import com.ypy.pyojbackend.model.vo.QuestionVO;
import com.ypy.pyojbackend.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/question")
public class QuestionController {

    @Resource
    private QuestionService questionService;

    @LoginRequired
    @GetMapping("/{id}")
    public AppResponse<QuestionVO> getOneQuestion(@PathVariable long id) throws AppException {
        return questionService.getQuestionVOById(id);
    }

    @PostMapping("/list")
    public AppResponse<PageVO<QuestionBriefVO>> getAllQuestions(@RequestBody QuestionPageQuery questionPageQuery) throws AppException {
        return questionService.getQuestionBriefVOPage(questionPageQuery);
    }

    @GetMapping("/recommend")
    public AppResponse<List<QuestionBriefVO>> getRecommend(HttpServletRequest request) {
        return null;
    }
}
