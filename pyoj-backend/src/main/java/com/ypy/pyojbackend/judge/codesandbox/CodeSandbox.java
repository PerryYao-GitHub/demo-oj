package com.ypy.pyojbackend.judge.codesandbox;

import com.ypy.pyojbackend.judge.codesandbox.model.CodeSandboxRequest;
import com.ypy.pyojbackend.judge.codesandbox.model.CodeSandboxResponse;
import com.ypy.pyojbackend.exception.AppException;

public interface CodeSandbox {

    CodeSandboxResponse exec(CodeSandboxRequest request) throws AppException;
}
