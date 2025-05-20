package com.ypy.pyojbackend.judge.codesandbox;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CodeSandboxFactory {

    @Value("${codesandbox.type}")
    private String type;

    @Resource(name = "PyCodeSandbox")
    CodeSandbox pyCodeSandbox;

    @Resource(name = "FakeCodeSandbox")
    CodeSandbox fakeCodeSandbox;

    public CodeSandbox getCodeSandbox() {
        switch (type) {
            case "py":
                return pyCodeSandbox;
            case "fake":
                return fakeCodeSandbox;

            default: throw new RuntimeException("wrong codesandbox type in application file");
        }
    }
}
