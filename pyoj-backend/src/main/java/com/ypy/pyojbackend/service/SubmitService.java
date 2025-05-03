package com.ypy.pyojbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ypy.pyojbackend.app.AppResponse;
import com.ypy.pyojbackend.exception.AppException;
import com.ypy.pyojbackend.model.entity.Submit;
import com.ypy.pyojbackend.model.query.SubmitPageQuery;
import com.ypy.pyojbackend.model.request.SubmitRequest;
import com.ypy.pyojbackend.model.vo.SubmitVO;

import java.util.List;

public interface SubmitService extends IService<Submit> {
    Submit toSubmit(SubmitRequest submitRequest) throws AppException;

    SubmitVO toSubmitVO(Submit submit);

    AppResponse<SubmitVO> doSubmit(Submit submit) throws AppException;

    AppResponse<SubmitVO> getSubmitById(Long id);

    /**
     * check all submit by One User, of One Question
     * @param submitPageQuery
     * @return
     * @throws AppException
     */
    AppResponse<List<SubmitVO>> getSubmitListByUserIdOrQuestionId(SubmitPageQuery submitPageQuery);
}
