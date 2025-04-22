package com.ypy.pyojbackend.judge;

import com.ypy.pyojbackend.common.AppCode;
import com.ypy.pyojbackend.common.SubmitStatusEnum;
import com.ypy.pyojbackend.exception.AppException;
import com.ypy.pyojbackend.model.entity.Question;
import com.ypy.pyojbackend.model.entity.Submit;
import com.ypy.pyojbackend.service.QuestionService;
import com.ypy.pyojbackend.service.SubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class JudgeServiceImpl implements JudgeService {
    @Resource
    private QuestionService questionService;

    @Resource
    private SubmitService submitService;

    @Value("${codesandbox.type:example}")
    private String codesandboxType;

    @Override
    public Submit doJudge(long submitId) throws AppException {
        // step 1
        Submit submit = submitService.getById(submitId);
        if (submit == null) throw new AppException(AppCode.ERR_NOT_FOUND);
        if (submit.getSubmitStatus() != SubmitStatusEnum.WAITING.getValue()) throw new AppException(AppCode.ERR_FORBIDDEN);

        // step 2
        long questionId = submit.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) throw new AppException(AppCode.ERR_NOT_FOUND);

        // step 3
        submit.setSubmitStatus(SubmitStatusEnum.RUNNING.getValue());
        if (!submitService.updateById(submit)) throw new AppException(AppCode.ERR_SYSTEM);

        // step 4 todo: use codesandbox

        return null;
    }
}
