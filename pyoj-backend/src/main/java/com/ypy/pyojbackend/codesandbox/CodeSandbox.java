package com.ypy.pyojbackend.codesandbox;

import com.ypy.pyojbackend.codesandbox.model.CodeSandboxRequest;
import com.ypy.pyojbackend.codesandbox.model.CodeSandboxResponse;
import com.ypy.pyojbackend.exception.AppException;

public interface CodeSandbox {

    CodeSandboxResponse exec(CodeSandboxRequest request) throws AppException;
}
