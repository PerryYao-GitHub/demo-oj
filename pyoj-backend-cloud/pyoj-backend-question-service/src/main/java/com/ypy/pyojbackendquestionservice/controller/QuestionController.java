package com.ypy.pyojbackendquestionservice.controller;

import com.ypy.pyojbackendcommon.app.AppResponse;
import com.ypy.pyojbackendcommon.exception.AppException;
import com.ypy.pyojbackendcommon.model.query.QuestionPageQuery;
import com.ypy.pyojbackendcommon.model.request.QuestionRequest;
import com.ypy.pyojbackendcommon.model.vo.PageVO;
import com.ypy.pyojbackendcommon.model.vo.QuestionBriefVO;
import com.ypy.pyojbackendcommon.model.vo.QuestionVO;
import com.ypy.pyojbackendquestionservice.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("")
public class QuestionController {

    @Resource
    private QuestionService questionService;

    @GetMapping("/admin/sync")
    public AppResponse<Void> syncQuestion() {
        return questionService.syncQuestion();
    }

    @PostMapping("/admin/create")
    public AppResponse<Void> adminCreateQuestion(@RequestBody QuestionRequest questionRequest) throws AppException {
        return questionService.createQuestion(questionRequest);
    }

    @PostMapping("/admin/update")
    public AppResponse<Void> adminUpdateQuestion(@RequestBody QuestionRequest questionRequest) throws AppException {
        return questionService.updateQuestion(questionRequest);
    }

    @DeleteMapping("/admin/delete/{id}")
    public AppResponse<Void> adminDeleteQuestion(@PathVariable Long id) throws AppException {
        return questionService.deleteQuestion(id);
    }

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
