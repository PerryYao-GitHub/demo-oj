package com.ypy.pyojbackend.controller;

import com.ypy.pyojbackend.aop.LoginRequired;
import com.ypy.pyojbackend.app.AppResponse;
import com.ypy.pyojbackend.exception.AppException;
import com.ypy.pyojbackend.model.query.SubmitPageQuery;
import com.ypy.pyojbackend.model.request.SubmitRequest;
import com.ypy.pyojbackend.model.vo.PageVO;
import com.ypy.pyojbackend.model.vo.SubmitVO;
import com.ypy.pyojbackend.service.SubmitService;
import com.ypy.pyojbackend.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@LoginRequired
@RestController
@RequestMapping("/submit")
public class SubmitController {

    @Resource
    private SubmitService submitService;

    @Resource
    UserService userService;

    @PostMapping("/do")
    public AppResponse<SubmitVO> doSubmit(@RequestBody SubmitRequest submitRequest, HttpServletRequest request) throws AppException {
        submitRequest.setUserId(userService.getLoginUserAuthDTO(request).getId());
        return submitService.doSubmit(submitRequest);
    }

    @GetMapping("")
    public AppResponse<SubmitVO> getSubmitById(@RequestParam("id") Long id) throws AppException {
        return submitService.getSubmitVOById(id);
    }

    @PostMapping("")
    public AppResponse<PageVO<SubmitVO>> getSubmitList(@RequestBody SubmitPageQuery submitPageQuery) throws AppException {
        return submitService.getSubmitVOListByUserIdOrQuestionId(submitPageQuery);
    }
}
