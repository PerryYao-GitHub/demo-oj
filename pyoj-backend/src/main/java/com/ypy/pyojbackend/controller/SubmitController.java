package com.ypy.pyojbackend.controller;

import com.ypy.pyojbackend.aop.LoginRequired;
import com.ypy.pyojbackend.app.AppResponse;
import com.ypy.pyojbackend.exception.AppException;
import com.ypy.pyojbackend.model.entity.Submit;
import com.ypy.pyojbackend.model.entity.User;
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
    public AppResponse<?> submit(HttpServletRequest request, @RequestBody SubmitRequest submitRequest) throws AppException {
        User loginUser = userService.getLoginUser(request);
        Submit submit = submitService.toSubmit(submitRequest);
        submit.setUserId(loginUser.getId());
        return submitService.doSubmit(submit);
    }

    @GetMapping("")
    public AppResponse<SubmitVO> getSubmitById(@RequestParam("id") Long id) throws AppException {
        return submitService.getSubmitById(id);
    }

    @PostMapping("")
    public AppResponse<List<SubmitVO>> getSubmitList(@RequestBody SubmitPageQuery submitPageQuery) throws AppException {
        return submitService.getSubmitListByUserIdOrQuestionId(submitPageQuery);
    }
}
