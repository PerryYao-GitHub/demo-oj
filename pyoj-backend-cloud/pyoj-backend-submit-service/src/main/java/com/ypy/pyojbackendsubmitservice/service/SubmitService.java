package com.ypy.pyojbackendsubmitservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ypy.pyojbackendcommon.app.AppResponse;
import com.ypy.pyojbackendcommon.exception.AppException;
import com.ypy.pyojbackendcommon.model.entity.Submit;
import com.ypy.pyojbackendcommon.model.query.SubmitPageQuery;
import com.ypy.pyojbackendcommon.model.request.SubmitRequest;
import com.ypy.pyojbackendcommon.model.vo.PageVO;
import com.ypy.pyojbackendcommon.model.vo.SubmitVO;

import javax.servlet.http.HttpServletRequest;

public interface SubmitService extends IService<Submit> {

    AppResponse<SubmitVO> doSubmit(SubmitRequest submitRequest, HttpServletRequest request) throws AppException;

    AppResponse<SubmitVO> getSubmitVOById(Long id);

    /**
     * check all submit by One User, of One Question
     * @param submitPageQuery
     * @return
     * @throws AppException
     */
    AppResponse<PageVO<SubmitVO>> getSubmitVOListByUserIdOrQuestionId(SubmitPageQuery submitPageQuery);
}
