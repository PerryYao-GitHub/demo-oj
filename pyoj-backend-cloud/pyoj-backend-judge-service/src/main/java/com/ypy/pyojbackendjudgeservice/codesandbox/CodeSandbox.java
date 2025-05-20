package com.ypy.pyojbackendjudgeservice.codesandbox;


import com.ypy.pyojbackendcommon.exception.AppException;
import com.ypy.pyojbackendjudgeservice.codesandbox.model.CodeSandboxRequest;
import com.ypy.pyojbackendjudgeservice.codesandbox.model.CodeSandboxResponse;

public interface CodeSandbox {

    CodeSandboxResponse exec(CodeSandboxRequest request) throws AppException;
}
