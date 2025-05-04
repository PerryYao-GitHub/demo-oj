package com.ypy.pyojbackend.controller;

import com.ypy.pyojbackend.aop.LoginRequired;
import com.ypy.pyojbackend.app.AppResponse;
import com.ypy.pyojbackend.exception.AppException;
import com.ypy.pyojbackend.model.query.SubmitPageQuery;
import com.ypy.pyojbackend.model.request.SubmitRequest;
import com.ypy.pyojbackend.model.vo.SubmitVO;
import com.ypy.pyojbackend.service.SubmitService;
import com.ypy.pyojbackend.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@LoginRequired
@RestController
@RequestMapping("/api/submit")
public class SubmitController {

    @Resource
    private SubmitService submitService;

    @Resource
    private UserService userService;

    @PostMapping("/do")
    public AppResponse<?> submit(@RequestBody SubmitRequest submitRequest, HttpServletRequest request) throws AppException {
        return submitService.doSubmit(submitRequest, request);
    }

    @GetMapping("")
    public AppResponse<SubmitVO> getSubmitById(@RequestParam("id") Long id) throws AppException {
        return submitService.getSubmitVOById(id);
    }

    @PostMapping("")
    public AppResponse<List<SubmitVO>> getSubmitList(@RequestBody SubmitPageQuery submitPageQuery) throws AppException {
        return submitService.getSubmitVOListByUserIdOrQuestionId(submitPageQuery);
    }
}
