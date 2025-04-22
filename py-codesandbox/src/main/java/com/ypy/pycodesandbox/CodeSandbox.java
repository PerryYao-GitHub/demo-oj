package com.ypy.pycodesandbox;

import com.ypy.pycodesandbox.model.AppRequest;
import com.ypy.pycodesandbox.model.AppResponse;

public interface CodeSandbox {

    AppResponse exec(AppRequest request);
}
