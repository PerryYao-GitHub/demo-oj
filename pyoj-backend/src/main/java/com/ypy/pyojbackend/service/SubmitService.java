package com.ypy.pyojbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ypy.pyojbackend.app.AppResponse;
import com.ypy.pyojbackend.exception.AppException;
import com.ypy.pyojbackend.model.entity.Submit;
import com.ypy.pyojbackend.model.query.SubmitPageQuery;
import com.ypy.pyojbackend.model.request.SubmitRequest;
import com.ypy.pyojbackend.model.vo.PageVO;
import com.ypy.pyojbackend.model.vo.SubmitVO;

public interface SubmitService extends IService<Submit> {

    AppResponse<SubmitVO> doSubmit(SubmitRequest submitRequest) throws AppException;

    AppResponse<SubmitVO> getSubmitVOById(Long id);

    /**
     * check all submit by One User, of One Question
     * @param submitPageQuery
     * @return
     * @throws AppException
     */
    AppResponse<PageVO<SubmitVO>> getSubmitVOListByUserIdOrQuestionId(SubmitPageQuery submitPageQuery);
}
