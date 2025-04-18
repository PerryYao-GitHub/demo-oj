package com.ypy.pyojbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ypy.pyojbackend.common.AppResponse;
import com.ypy.pyojbackend.model.query.SubmitPageQuery;
import com.ypy.pyojbackend.model.entity.Submit;
import com.ypy.pyojbackend.model.vo.SubmitSimpleVO;
import com.ypy.pyojbackend.model.vo.SubmitVO;

import java.util.List;

public interface SubmitService extends IService<Submit> {

    AppResponse<SubmitVO> doSubmit(Submit submit);

    AppResponse<SubmitVO> getOneSubmit(Long id);

    AppResponse<List<SubmitSimpleVO>> getSubmits(SubmitPageQuery submitPageQuery);
}
