package com.ypy.pyojbackendsubmitservice.controller;

import com.ypy.pyojbackendcommon.app.AppResponse;
import com.ypy.pyojbackendcommon.exception.AppException;
import com.ypy.pyojbackendcommon.model.query.SubmitPageQuery;
import com.ypy.pyojbackendcommon.model.request.SubmitRequest;
import com.ypy.pyojbackendcommon.model.vo.PageVO;
import com.ypy.pyojbackendcommon.model.vo.SubmitVO;
import com.ypy.pyojbackendsubmitservice.service.SubmitService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
public class SubmitController {

    @Resource
    private SubmitService submitService;

    @PostMapping("/do")
    public AppResponse<SubmitVO> submit(
            @RequestBody SubmitRequest submitRequest,
            HttpServletRequest request
    ) throws AppException {
        return submitService.doSubmit(submitRequest, request);
    }

    @GetMapping
    public AppResponse<SubmitVO> getSubmitById(
            @RequestParam("id") Long id
    ) throws AppException {
        return submitService.getSubmitVOById(id);
    }

    @PostMapping
    public AppResponse<PageVO<SubmitVO>> getSubmitList(
            @RequestBody SubmitPageQuery submitPageQuery
    ) throws AppException {
        return submitService.getSubmitVOListByUserIdOrQuestionId(submitPageQuery);
    }
}
