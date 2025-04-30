package com.ypy.pycodesandbox.sandboxtest;

import com.ypy.pycodesandbox.app.AppRequest;
import com.ypy.pycodesandbox.app.AppResponse;
import com.ypy.pycodesandbox.enums.LangEnum;
import com.ypy.pycodesandbox.javacodesandbox.JavaDockerCodeSandbox;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class JavaDockerSandboxTest {
    @Resource
    JavaDockerCodeSandbox codeSandbox;

    @Test
    void rightCodeTest() {
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

    @Test
    void wrongCompileCodeTest() {
        AppRequest appRequest = AppRequest.builder()
                .lang(LangEnum.JAVA.getValue())
                .code("import java.util.Scanner;\n" +
                        "Public Class Main {\n" +
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
AppResponse(outputs=null, message=/home/ypy/code/projects/demo-oj/py-codesandbox/tmp-code/8f431cf3-2557-4203-bcbc-20fbd91f3df6/Main.java:2: error: class, interface, enum, or record expected
Public Class Main {
^
/home/ypy/code/projects/demo-oj/py-codesandbox/tmp-code/8f431cf3-2557-4203-bcbc-20fbd91f3df6/Main.java:3: error: class, interface, enum, or record expected
    public static void main(String[] args) {
                  ^
/home/ypy/code/projects/demo-oj/py-codesandbox/tmp-code/8f431cf3-2557-4203-bcbc-20fbd91f3df6/Main.java:5: error: class, interface, enum, or record expected
        int a = sc.nextInt();
        ^
/home/ypy/code/projects/demo-oj/py-codesandbox/tmp-code/8f431cf3-2557-4203-bcbc-20fbd91f3df6/Main.java:6: error: class, interface, enum, or record expected
        int b = sc.nextInt();
        ^
/home/ypy/code/projects/demo-oj/py-codesandbox/tmp-code/8f431cf3-2557-4203-bcbc-20fbd91f3df6/Main.java:7: error: class, interface, enum, or record expected
        System.out.println(a + b);
        ^
/home/ypy/code/projects/demo-oj/py-codesandbox/tmp-code/8f431cf3-2557-4203-bcbc-20fbd91f3df6/Main.java:8: error: class, interface, enum, or record expected
    }
    ^
6 errors, status=1, memory=null, time=null)
*/
    }

    @Test
    void wrongRunTimeCodeTest() {
        AppRequest appRequest = AppRequest.builder()
                .lang(LangEnum.JAVA.getValue())
                .code("import java.util.Scanner;\n" +
                        "public class Main {\n" +
                        "    public static void main(String[] args) {\n" +
                        "        Scanner sc = new Scanner(System.in);\n" +
                        "        int a = sc.nextInt();\n" +
                        "        System.out.println(a);\n" +
                        "        int b = 1 / 0;\n" +
                        "        System.out.println(a + b);\n" +
                        "    }\n" +
                        "}")
                .inputs(List.of("1 2", "3 4", "5 6"))
                .build();
        AppResponse response = codeSandbox.exec(appRequest);
        System.out.println(response);
/*
AppResponse(outputs=null, message=Exception in thread "main" java.lang.ArithmeticException: / by zero
	at Main.main(Main.java:7), status=2, memory=null, time=null)
*/
    }
}
