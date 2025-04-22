package com.ypy.pyojbackend.codesandbox;

import com.ypy.pyojbackend.codesandbox.model.ExecuteRequest;
import com.ypy.pyojbackend.codesandbox.model.ExecuteResponse;

public interface CodeSandbox {

    ExecuteResponse exec(ExecuteRequest request);
}
