package com.ypy.pyojbackendjudgeservice.strategy;


import com.ypy.pyojbackendcommon.model.judge.JudgeCase;
import com.ypy.pyojbackendcommon.model.judge.JudgeConfig;
import com.ypy.pyojbackendjudgeservice.codesandbox.model.CodeSandboxResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JudgeContext {

    private CodeSandboxResponse sandboxResponse;

    private JudgeConfig judgeConfig;

    private JudgeCase judgeCase;
}
