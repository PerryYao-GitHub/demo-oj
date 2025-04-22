package com.ypy.pycodesandbox;

import com.ypy.pycodesandbox.model.AppRequest;
import com.ypy.pycodesandbox.model.AppResponse;
import org.springframework.stereotype.Component;

@Component
public class JavaNativeCodeSandbox extends JavaCodeSandboxTemplate {

    @Override
    public AppResponse exec(AppRequest request) { return super.exec(request); }
}
