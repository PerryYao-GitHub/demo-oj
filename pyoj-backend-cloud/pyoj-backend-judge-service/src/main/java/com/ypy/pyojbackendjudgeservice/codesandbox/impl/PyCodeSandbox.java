package com.ypy.pyojbackendjudgeservice.codesandbox.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.ypy.pyojbackendcommon.app.AppCode;
import com.ypy.pyojbackendcommon.exception.AppException;
import com.ypy.pyojbackendjudgeservice.codesandbox.CodeSandbox;
import com.ypy.pyojbackendjudgeservice.codesandbox.model.CodeSandboxRequest;
import com.ypy.pyojbackendjudgeservice.codesandbox.model.CodeSandboxResponse;
import org.springframework.stereotype.Component;

@Component("PyCodeSandbox")
public class PyCodeSandbox implements CodeSandbox {
    private static final String AUTH_REQUEST_HEADER = "py-auth";

    private static final String AUTH_REQUEST_SECRET = "py-auth-key";

    private static final String URL = "http://localhost:8964/api/exec";

    @Override
    public CodeSandboxResponse exec(CodeSandboxRequest request) throws AppException {
        String json = JSONUtil.toJsonStr(request);
        String responseStr = HttpUtil.createPost(URL)
                .header(AUTH_REQUEST_HEADER, AUTH_REQUEST_SECRET)
                .body(json)
                .execute()
                .body();
        if (StrUtil.isBlank(responseStr)) throw new AppException(AppCode.ERR_SYSTEM);
        return JSONUtil.toBean(responseStr, CodeSandboxResponse.class);
    }
}
