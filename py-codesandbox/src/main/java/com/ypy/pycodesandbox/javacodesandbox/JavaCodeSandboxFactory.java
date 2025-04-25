package com.ypy.pycodesandbox.javacodesandbox;

import com.ypy.pycodesandbox.CodeSandbox;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class JavaCodeSandboxFactory {

    @Value("${codesandbox.java.type}")
    private String type;

    @Resource(name = "JavaNativeCodeSandbox")
    private CodeSandbox nativeCodeSandbox;

    @Resource(name = "JavaDockerCodeSandbox")
    private CodeSandbox dockerCodeSandbox;

    public CodeSandbox getCodeSandbox() {
        switch (type.toLowerCase()) {
            case "native": return nativeCodeSandbox;
            case "docker": return dockerCodeSandbox;
            default: throw new RuntimeException("Invalid Java code sandbox type: " + type);
        }
    }
}
