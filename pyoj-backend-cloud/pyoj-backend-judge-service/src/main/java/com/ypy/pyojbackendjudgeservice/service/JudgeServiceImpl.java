package com.ypy.pyojbackendjudgeservice.service;

import com.ypy.pyojbackendcommon.app.AppCode;
import com.ypy.pyojbackendcommon.exception.AppException;
import com.ypy.pyojbackendjudgeservice.codesandbox.CodeSandbox;
import com.ypy.pyojbackendjudgeservice.codesandbox.CodeSandboxFactory;
import com.ypy.pyojbackendjudgeservice.codesandbox.model.CodeSandboxRequest;
import com.ypy.pyojbackendjudgeservice.codesandbox.model.CodeSandboxResponse;
import com.ypy.pyojbackendcommon.model.judge.JudgeResult;
import com.ypy.pyojbackendjudgeservice.strategy.JudgeContext;
import com.ypy.pyojbackendjudgeservice.strategy.JudgeStrategy;
import com.ypy.pyojbackendcommon.model.entity.Question;
import com.ypy.pyojbackendcommon.model.entity.Submit;
import com.ypy.pyojbackendcommon.model.enums.SubmitStatusEnum;
import com.ypy.pyojbackendserviceclient.service.QuestionFeignClient;
import com.ypy.pyojbackendserviceclient.service.SubmitFeignClient;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class JudgeServiceImpl implements JudgeService {
    @Resource
    private QuestionFeignClient questionFeignClient;

    @Resource
    private SubmitFeignClient submitFeignClient;

    @Resource
    private CodeSandboxFactory codeSandboxFactory;

    @Resource
    private JudgeStrategy judgeStrategy;

    @Resource
    private RedissonClient redissonClient;

    private static final String LOCK_PREFIX = "pyoj:judge-service:do-judge:lock:";

    @Override
    public void doJudge(long submitId) throws AppException {
        // step 1
        Submit submit = submitFeignClient.getById(submitId);
        if (submit == null) throw new AppException(AppCode.ERR_NOT_FOUND);
        if (submit.getStatus() != SubmitStatusEnum.WAITING.getValue()) throw new AppException(AppCode.ERR_FORBIDDEN);

        // step 2
        long questionId = submit.getQuestionId();
        Question question = questionFeignClient.getById(questionId);
        if (question == null) throw new AppException(AppCode.ERR_NOT_FOUND);

        // step 3
        submit.setStatus(SubmitStatusEnum.RUNNING.getValue());
        if (!submitFeignClient.updateById(submit)) throw new AppException(AppCode.ERR_SYSTEM);

        // step 4
        CodeSandbox codeSandbox = codeSandboxFactory.getCodeSandbox();
        CodeSandboxRequest request = CodeSandboxRequest.builder()
                .lang(submit.getLang())
                .code(submit.getCode())
                .inputs(question.getJudgeCase().getInputs())
                .build();
        CodeSandboxResponse response = codeSandbox.exec(request);

        // step 5
        JudgeContext judgeContext = JudgeContext.builder()
                .sandboxResponse(response)
                .judgeConfig(question.getJudgeConfig())
                .judgeCase(question.getJudgeCase())
                .build();
        JudgeResult judgeResult = judgeStrategy.doJudge(judgeContext);

        // step 6
        RLock lk = redissonClient.getLock(LOCK_PREFIX + questionId); // ensure question cnt can be update accurately
        try {
            lk.lock();
            submit.setJudgeResult(judgeResult);
            question.setSubmitCnt(question.getSubmitCnt() + 1);
            if (Objects.equals(judgeResult.getStatus(), JudgeResult.Status.AC.getValue())) {
                submit.setStatus(SubmitStatusEnum.AC.getValue());
                question.setAcceptedCnt(question.getAcceptedCnt() + 1);
            } else {
                submit.setStatus(SubmitStatusEnum.FAIL.getValue());
            }
            submitFeignClient.updateById(submit);
            questionFeignClient.updateById(question);
        } finally {
            lk.unlock();
        }
    }
}
