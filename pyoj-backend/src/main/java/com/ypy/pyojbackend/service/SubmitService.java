package com.ypy.pyojbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ypy.pyojbackend.common.AppResponse;
import com.ypy.pyojbackend.exception.AppException;
import com.ypy.pyojbackend.model.entity.Submit;
import com.ypy.pyojbackend.model.query.SubmitPageQuery;
import com.ypy.pyojbackend.model.request.SubmitRequest;
import com.ypy.pyojbackend.model.vo.SubmitPageVO;
import com.ypy.pyojbackend.model.vo.SubmitVO;

import java.util.List;

public interface SubmitService extends IService<Submit> {
    Submit toSubmit(SubmitRequest submitRequest) throws AppException;

    AppResponse<SubmitVO> doSubmit(Submit submit) throws AppException;

    AppResponse<SubmitVO> getOneSubmit(Long id);

    AppResponse<List<SubmitPageVO>> getSubmitPage(SubmitPageQuery submitPageQuery);
}
