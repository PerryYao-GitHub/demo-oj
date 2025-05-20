package com.ypy.pyojbackendjudgeservice.codesandbox.impl;


import com.ypy.pyojbackendcommon.exception.AppException;
import com.ypy.pyojbackendjudgeservice.codesandbox.CodeSandbox;
import com.ypy.pyojbackendjudgeservice.codesandbox.model.CodeSandboxRequest;
import com.ypy.pyojbackendjudgeservice.codesandbox.model.CodeSandboxResponse;
import org.springframework.stereotype.Component;

@Component("FakeCodeSandbox")
public class FakeCodeSandbox implements CodeSandbox {
    @Override
    public CodeSandboxResponse exec(CodeSandboxRequest request) throws AppException {
        CodeSandboxResponse response = CodeSandboxResponse.builder()
                .outputs(request.getInputs())
                .message("ok")
                .status(CodeSandboxResponse.Status.OK.getValue())
                .build();
        return response;
    }
}
