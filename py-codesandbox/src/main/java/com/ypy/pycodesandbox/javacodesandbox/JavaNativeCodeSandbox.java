package com.ypy.pycodesandbox.javacodesandbox;

import com.ypy.pycodesandbox.app.AppRequest;
import com.ypy.pycodesandbox.app.AppResponse;
import org.springframework.stereotype.Component;

@Component("JavaNativeCodeSandbox")
public class JavaNativeCodeSandbox extends JavaCodeSandboxTemplate {

    @Override
    public AppResponse exec(AppRequest request) { return super.exec(request); }
}
