package com.ypy.pycodesandbox;

import com.ypy.pycodesandbox.enums.LangEnum;
import com.ypy.pycodesandbox.model.AppRequest;
import com.ypy.pycodesandbox.model.AppResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class PyCodesandboxApplicationTests {
    @Resource
    JavaNativeCodeSandbox javaNativeCodeSandbox;

    @Test
    void rightCodeTest() {
        AppRequest appRequest = AppRequest.builder()
                .lang(LangEnum.JAVA.getValue())
                .code("public class Main {\n" +
                        "    public static void main(String[] args) {\n" +
                        "        int a = Integer.parseInt(args[0]);\n" +
                        "        int b = Integer.parseInt(args[1]);\n" +
                        "        System.out.println(a + b);\n" +
                        "    }\n" +
                        "}")
                .inputs(List.of("1 2", "3 4", "5 6"))
                .build();
        AppResponse response = javaNativeCodeSandbox.exec(appRequest);
        System.out.println(response);
        /*
        success!
         */
    }

    @Test
    void wrongCodeTest() {
        AppRequest appRequest = AppRequest.builder()
                .lang(LangEnum.JAVA.getValue())
                .code("public class Main {\n" +
                        "    public static void main(String[] args) {\n" +
                        "        int a = args[0];\n" +
                        "        int b = args[1];\n" +
                        "        System.out.println(a + b);\n" +
                        "    }\n" +
                        "}")
                .inputs(List.of("1 2", "3 4", "5 6"))
                .build();
        AppResponse response = javaNativeCodeSandbox.exec(appRequest);
        System.out.println(response);
        /*
        ExecuteResponse(outputs=null, message=/home/ypy/code/projects/demo-oj/py-codesandbox/tmp-code/1d9332eb-6a8f-40c7-8bb9-92a14019181a/Main.java:3: error: incompatible types: String cannot be converted to int
        int a = args[0];
                    ^
/home/ypy/code/projects/demo-oj/py-codesandbox/tmp-code/1d9332eb-6a8f-40c7-8bb9-92a14019181a/Main.java:4: error: incompatible types: String cannot be converted to int
        int b = args[1];
                    ^
2 errors, status=1, memory=null, time=null)
         */
    }
}
