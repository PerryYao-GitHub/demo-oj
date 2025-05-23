package com.ypy.pyojbackend.controller;

import com.ypy.pyojbackend.aop.AuthCheck;
import com.ypy.pyojbackend.aop.LoginRequired;
import com.ypy.pyojbackend.app.AppResponse;
import com.ypy.pyojbackend.exception.AppException;
import com.ypy.pyojbackend.model.enums.UserRoleEnum;
import com.ypy.pyojbackend.model.query.QuestionPageQuery;
import com.ypy.pyojbackend.model.request.QuestionRequest;
import com.ypy.pyojbackend.model.vo.PageVO;
import com.ypy.pyojbackend.model.vo.QuestionBriefVO;
import com.ypy.pyojbackend.model.vo.QuestionVO;
import com.ypy.pyojbackend.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Resource
    private QuestionService questionService;

    @AuthCheck(mustRole = UserRoleEnum.ADMIN)
    @GetMapping("/admin/sync")
    public AppResponse<Void> adminSyncQuestion() {
        return questionService.syncQuestion();
    }

    @AuthCheck(mustRole = UserRoleEnum.ADMIN)
    @PostMapping("/admin/create")
    public AppResponse<Void> adminCreateQuestion(@RequestBody QuestionRequest questionRequest) throws AppException {
        return questionService.createQuestion(questionRequest);
    }

    @AuthCheck(mustRole = UserRoleEnum.ADMIN)
    @PostMapping("/admin/update")
    public AppResponse<Void> adminUpdateQuestion(@RequestBody QuestionRequest questionRequest) throws AppException {
        return questionService.updateQuestion(questionRequest);
    }

    @AuthCheck(mustRole = UserRoleEnum.ADMIN)
    @DeleteMapping("/delete/question/{id}")
    public AppResponse<Void> adminDeleteQuestion(@PathVariable Long id) throws AppException {
        return questionService.deleteQuestion(id);
    }

    @LoginRequired
    @GetMapping("/{id}")
    public AppResponse<QuestionVO> getOneQuestion(@PathVariable long id) throws AppException {
        return questionService.getQuestionVOById(id);
    }

    @PostMapping("/page")
    public AppResponse<PageVO<QuestionBriefVO>> getQuestionPage(@RequestBody QuestionPageQuery questionPageQuery) throws AppException {
        return questionService.getQuestionBriefVOPage(questionPageQuery);
    }

    @GetMapping("/recommend")
    public AppResponse<List<QuestionBriefVO>> getRecommend(HttpServletRequest request) {
        return null;
    }
}
