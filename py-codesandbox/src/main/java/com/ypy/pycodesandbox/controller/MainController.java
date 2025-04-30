package com.ypy.pycodesandbox.controller;

import com.ypy.pycodesandbox.CodeSandbox;
import com.ypy.pycodesandbox.enums.LangEnum;
import com.ypy.pycodesandbox.javacodesandbox.JavaCodeSandboxFactory;
import com.ypy.pycodesandbox.app.AppRequest;
import com.ypy.pycodesandbox.app.AppResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class MainController {

    private static final String AUTH_REQUEST_HEADER = "py-auth";

    private static final String AUTH_REQUEST_SECRET = "py-auth-key";

    @Resource
    private JavaCodeSandboxFactory javaCodeSandboxFactory;

    @PostMapping("/exec")
    AppResponse exec(
            @RequestBody AppRequest appRequest,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String authHeader = request.getHeader(AUTH_REQUEST_HEADER);
        if (authHeader == null || !authHeader.equals(AUTH_REQUEST_SECRET)) {
            response.setStatus(403);
            return new AppResponse(AppResponse.Status.ERR_OTHER, "");
        }
        Byte langValue = appRequest.getLang();
        if (LangEnum.JAVA.getValue() == langValue) {
            CodeSandbox codeSandbox = javaCodeSandboxFactory.getCodeSandbox();
            return codeSandbox.exec(appRequest);
        }
        return new AppResponse(AppResponse.Status.ERR_OTHER, "wrong lang");
    }
}
