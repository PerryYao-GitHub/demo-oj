package com.ypy.pyojbackend.judge.strategy;

import com.ypy.pyojbackend.app.AppCode;
import com.ypy.pyojbackend.codesandbox.model.CodeSandboxResponse;
import com.ypy.pyojbackend.exception.AppException;
import com.ypy.pyojbackend.judge.model.JudgeCase;
import com.ypy.pyojbackend.judge.model.JudgeConfig;
import com.ypy.pyojbackend.judge.model.JudgeInfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class JudgeStrategyImpl implements JudgeStrategy {
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) throws AppException {
        JudgeInfo judgeInfo = new JudgeInfo();

        CodeSandboxResponse sandboxResponse = judgeContext.getSandboxResponse();
        if (sandboxResponse == null) throw new AppException(AppCode.ERR_SYSTEM);
        if (!CodeSandboxResponse.Status.OK.getValue().equals(sandboxResponse.getStatus())) {
            judgeInfo.setStatus(JudgeInfo.Status.RUNNING_WRONG.getValue());
            judgeInfo.setMessage(sandboxResponse.getMessage());
            return judgeInfo;
        }

        JudgeConfig judgeConfig = judgeContext.getJudgeConfig(); // time and memory cost check
        if (judgeConfig == null) throw new AppException(AppCode.ERR_SYSTEM);
        if (judgeConfig.getTimeLimit() < sandboxResponse.getTime()) {
            judgeInfo.setStatus(JudgeInfo.Status.TIME_LIMIT_EXCEED.getValue());
            judgeInfo.setMessage(sandboxResponse.getMessage());
            return judgeInfo;
        }
        if (judgeConfig.getMemoryLimit() < sandboxResponse.getMemory()) {
            judgeInfo.setStatus(JudgeInfo.Status.MEMORY_LIMIT_EXCEED.getValue());
            judgeInfo.setMessage(sandboxResponse.getMessage());
            return judgeInfo;
        }
        judgeInfo.setTime(sandboxResponse.getTime());
        judgeInfo.setMemory(sandboxResponse.getMemory());

        JudgeCase judgeCase = judgeContext.getJudgeCase();
        if (judgeCase == null) throw new AppException(AppCode.ERR_SYSTEM);
        List<String> outputs = sandboxResponse.getOutputs();
        List<String> outputCases = judgeCase.getOutputs();
        if (outputCases.size() != outputs.size()) {
            judgeInfo.setStatus(JudgeInfo.Status.RUNNING_WRONG.getValue());
            judgeInfo.setMessage("wrong number of outputs");
            return judgeInfo;
        }

        for (int i = 0; i < outputs.size(); i++) {
            if (!Objects.equals(outputs.get(i), outputCases.get(i))) {
                judgeInfo.setStatus(JudgeInfo.Status.RUNNING_WRONG.getValue());
                judgeInfo.setMessage("wrong answer at case " + i);
                return judgeInfo;
            }
        }
        judgeInfo.setStatus(JudgeInfo.Status.AC.getValue());
        judgeInfo.setMessage("ac");
        return judgeInfo;
    }
}
