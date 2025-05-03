package com.ypy.pyojbackend.judge.strategy;

import com.ypy.pyojbackend.app.AppCode;
import com.ypy.pyojbackend.codesandbox.model.CodeSandboxResponse;
import com.ypy.pyojbackend.exception.AppException;
import com.ypy.pyojbackend.judge.model.JudgeCase;
import com.ypy.pyojbackend.judge.model.JudgeConfig;
import com.ypy.pyojbackend.judge.model.JudgeResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class JudgeStrategyImpl implements JudgeStrategy {
    @Override
    public JudgeResult doJudge(JudgeContext judgeContext) throws AppException {
        JudgeResult judgeResult = new JudgeResult();

        CodeSandboxResponse sandboxResponse = judgeContext.getSandboxResponse();
        if (sandboxResponse == null) throw new AppException(AppCode.ERR_SYSTEM);
        if (!CodeSandboxResponse.Status.OK.getValue().equals(sandboxResponse.getStatus())) {
            judgeResult.setStatus(JudgeResult.Status.RUNNING_WRONG.getValue());
            judgeResult.setMessage(sandboxResponse.getMessage());
            return judgeResult;
        }

        JudgeConfig judgeConfig = judgeContext.getJudgeConfig(); // time and memory cost check
        if (judgeConfig == null) throw new AppException(AppCode.ERR_SYSTEM);
        if (judgeConfig.getTimeLimit() < sandboxResponse.getTime()) {
            judgeResult.setStatus(JudgeResult.Status.TIME_LIMIT_EXCEED.getValue());
            judgeResult.setMessage(sandboxResponse.getMessage());
            return judgeResult;
        }
        if (judgeConfig.getMemoryLimit() < sandboxResponse.getMemory()) {
            judgeResult.setStatus(JudgeResult.Status.MEMORY_LIMIT_EXCEED.getValue());
            judgeResult.setMessage(sandboxResponse.getMessage());
            return judgeResult;
        }
        judgeResult.setTime(sandboxResponse.getTime());
        judgeResult.setMemory(sandboxResponse.getMemory());

        JudgeCase judgeCase = judgeContext.getJudgeCase();
        if (judgeCase == null) throw new AppException(AppCode.ERR_SYSTEM);
        List<String> outputs = sandboxResponse.getOutputs();
        List<String> outputCases = judgeCase.getOutputs();
        if (outputCases.size() != outputs.size()) {
            judgeResult.setStatus(JudgeResult.Status.RUNNING_WRONG.getValue());
            judgeResult.setMessage("wrong number of outputs");
            return judgeResult;
        }

        for (int i = 0; i < outputs.size(); i++) {
            if (!Objects.equals(outputs.get(i), outputCases.get(i))) {
                judgeResult.setStatus(JudgeResult.Status.RUNNING_WRONG.getValue());
                judgeResult.setMessage("wrong answer at case " + i);
                return judgeResult;
            }
        }
        judgeResult.setStatus(JudgeResult.Status.AC.getValue());
        judgeResult.setMessage("ac");
        return judgeResult;
    }
}
