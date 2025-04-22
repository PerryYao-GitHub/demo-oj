package com.ypy.pyojbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ypy.pyojbackend.common.AppCode;
import com.ypy.pyojbackend.common.AppResponse;
import com.ypy.pyojbackend.common.LangEnum;
import com.ypy.pyojbackend.common.SubmitStatusEnum;
import com.ypy.pyojbackend.exception.AppException;
import com.ypy.pyojbackend.judge.JudgeService;
import com.ypy.pyojbackend.mapper.SubmitMapper;
import com.ypy.pyojbackend.model.entity.Question;
import com.ypy.pyojbackend.model.entity.Submit;
import com.ypy.pyojbackend.model.entity.User;
import com.ypy.pyojbackend.model.query.SubmitPageQuery;
import com.ypy.pyojbackend.model.request.SubmitRequest;
import com.ypy.pyojbackend.model.vo.SubmitPageVO;
import com.ypy.pyojbackend.model.vo.SubmitVO;
import com.ypy.pyojbackend.service.QuestionService;
import com.ypy.pyojbackend.service.SubmitService;
import com.ypy.pyojbackend.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class SubmitServiceImpl
        extends ServiceImpl<SubmitMapper, Submit>
        implements SubmitService {

    @Resource
    private QuestionService questionService;

    @Resource
    private UserService userService;

    @Resource
    @Lazy
    private JudgeService judgeService;

    @Override
    public Submit toSubmit(SubmitRequest submitRequest) throws AppException {
        Submit submit = new Submit();
        submit.setQuestionId(submitRequest.getQuestionId());
        submit.setLang(LangEnum.textValueMap.get(submitRequest.getLang()));
        submit.setCode(submitRequest.getCode());
        submit.setSubmitStatus(SubmitStatusEnum.WAITING.getValue());
        return submit;
    }

    @Override
    public AppResponse<SubmitVO> doSubmit(Submit submit) throws AppException {
        Question question = questionService.getById(submit.getQuestionId());
        if (question == null) throw new AppException(AppCode.ERR_NOT_FOUND);
        if (!save(submit)) throw new AppException(AppCode.ERR_SYSTEM);

        long submitId = submit.getId(); // once save successfully, the id and some other field will be filled
        CompletableFuture.runAsync(() -> {
            try {
                judgeService.doJudge(submitId);
            } catch (AppException e) {
                throw new RuntimeException(e);
            }
        });
        return new AppResponse<>(AppCode.OK, SubmitVO.from(submit));
    }

    @Override
    public AppResponse<SubmitVO> getOneSubmit(Long id) {
        return null;
    }

    @Override
    public AppResponse<List<SubmitPageVO>> getSubmitPage(SubmitPageQuery submitPageQuery) {
        return null;
    }
}
