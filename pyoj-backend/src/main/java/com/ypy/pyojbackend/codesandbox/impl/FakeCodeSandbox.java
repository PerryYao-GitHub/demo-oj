package com.ypy.pyojbackend.codesandbox.impl;

import com.ypy.pyojbackend.codesandbox.CodeSandbox;
import com.ypy.pyojbackend.codesandbox.model.CodeSandboxRequest;
import com.ypy.pyojbackend.codesandbox.model.CodeSandboxResponse;
import com.ypy.pyojbackend.exception.AppException;
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
