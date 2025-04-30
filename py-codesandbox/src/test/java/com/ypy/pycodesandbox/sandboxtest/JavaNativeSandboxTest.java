package com.ypy.pycodesandbox.sandboxtest;

import com.ypy.pycodesandbox.javacodesandbox.JavaNativeCodeSandbox;
import com.ypy.pycodesandbox.enums.LangEnum;
import com.ypy.pycodesandbox.app.AppRequest;
import com.ypy.pycodesandbox.app.AppResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;


@SpringBootTest
public class JavaNativeSandboxTest {
    @Resource
    JavaNativeCodeSandbox codeSandbox;

    @Test
    void rightCodeTestOld() {
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
        AppResponse response = codeSandbox.exec(appRequest);
        System.out.println(response);
/*
AppResponse(outputs=[3, 7, 11], message=ok, status=0, memory=0, time=26)
*/
    }

    @Test
    void wrongCodeTestOld() {
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
        AppResponse response = codeSandbox.exec(appRequest);
        System.out.println(response);
/*
AppResponse(outputs=null, message=/home/ypy/code/projects/demo-oj/py-codesandbox/tmp-code/021f8317-9b16-40b6-a06a-bb117f3a1535/Main.java:3: error: incompatible types: String cannot be converted to int
        int a = args[0];
                    ^
/home/ypy/code/projects/demo-oj/py-codesandbox/tmp-code/021f8317-9b16-40b6-a06a-bb117f3a1535/Main.java:4: error: incompatible types: String cannot be converted to int
        int b = args[1];
                    ^
2 errors, status=1, memory=null, time=null)
*/
    }

    @Test
    void rightCodeTestNew() {
        AppRequest appRequest = AppRequest.builder()
                .lang(LangEnum.JAVA.getValue())
                .code("import java.util.Scanner;\n" +
                        "public class Main {\n" +
                        "    public static void main(String[] args) {\n" +
                        "        Scanner sc = new Scanner(System.in);\n" +
                        "        int a = sc.nextInt();\n" +
                        "        int b = sc.nextInt();\n" +
                        "        System.out.println(a + b);\n" +
                        "    }\n" +
                        "}")
                .inputs(List.of("1 2", "3 4", "5 6"))
                .build();
        AppResponse response = codeSandbox.exec(appRequest);
        System.out.println(response);
/*
AppResponse(outputs=[3, 7, 11], message=ok, status=0, memory=0, time=53)
*/
    }
}
