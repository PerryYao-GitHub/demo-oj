package com.ypy.pycodesandbox;

import com.ypy.pycodesandbox.app.AppRequest;
import com.ypy.pycodesandbox.app.AppResponse;

public interface CodeSandbox {

    AppResponse exec(AppRequest request);
}
