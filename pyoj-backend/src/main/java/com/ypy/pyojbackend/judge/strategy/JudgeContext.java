package com.ypy.pyojbackend.judge.strategy;

import com.ypy.pyojbackend.codesandbox.model.CodeSandboxResponse;
import com.ypy.pyojbackend.judge.model.JudgeCase;
import com.ypy.pyojbackend.judge.model.JudgeConfig;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JudgeContext {

    private CodeSandboxResponse sandboxResponse;

    private JudgeConfig judgeConfig;

    private JudgeCase judgeCase;
}
