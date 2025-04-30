package com.ypy.pyojbackend.codesandbox;

import com.ypy.pyojbackend.codesandbox.impl.PyCodeSandbox;
import com.ypy.pyojbackend.codesandbox.model.CodeSandboxRequest;
import com.ypy.pyojbackend.codesandbox.model.CodeSandboxResponse;
import com.ypy.pyojbackend.exception.AppException;
import com.ypy.pyojbackend.model.enums.LangEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CodeSandboxTest {

    @Autowired
    CodeSandboxFactory codeSandboxFactory;
    @Test
    void test() throws AppException {
        CodeSandbox codeSandbox = codeSandboxFactory.getCodeSandbox();
        CodeSandboxRequest request = CodeSandboxRequest.builder()
                .code("")
                .inputs(List.of("alice", "bob", "candy", "dick"))
                .lang(LangEnum.JAVA.getValue())
                .build();

        CodeSandboxResponse exec = codeSandbox.exec(request);
        System.out.println(exec);
    }

    @Autowired
    PyCodeSandbox pyCodeSandbox;
    @Test
    void testPyCodeSandbox() throws AppException {
        CodeSandboxRequest request = CodeSandboxRequest.builder()
                .code("public class Main {\n    public static void main(String[] args) {\n        System.out.println(\"Hello\");\n    }\n}")
                .inputs(List.of("1 2", "3 4", "5 6"))
                .lang(LangEnum.JAVA.getValue())
                .build();

        CodeSandboxResponse exec = pyCodeSandbox.exec(request);
        System.out.println(exec);
    }
}
