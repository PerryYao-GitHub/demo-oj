package com.ypy.pycodesandbox;

import cn.hutool.core.io.FileUtil;
import com.ypy.pycodesandbox.enums.LangEnum;
import com.ypy.pycodesandbox.model.ExecuteInfo;
import com.ypy.pycodesandbox.model.AppRequest;
import com.ypy.pycodesandbox.model.AppResponse;
import com.ypy.pycodesandbox.utils.ProcessUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Slf4j
public abstract class JavaCodeSandboxTemplate implements CodeSandbox{

    private static final String CODE_DIR = "tmp-code";

    private static final String JAVA_CLASS_NAME = "Main.java";

    private static final long TIMEOUT = 5000L;

    @Override
    public AppResponse exec(AppRequest request) {
        List<String> inputList = request.getInputs();
        String code = request.getCode();
        Byte lang = request.getLang();
        if (LangEnum.JAVA.getValue() != lang) {
            return AppResponse.builder()
                    .message("Wrong language")
                    .status(AppResponse.Status.ERR_OTHER.getValue())
                    .build();
        }

        // 1. save code into file
        File codeFile = saveCodeToFile(code);

        // 2. compile code file
        ExecuteInfo compileFileMessage = compileCodeFile(codeFile);

        if (compileFileMessage.getExitValue() != 0) { // compile error
            if (!deleteFile(codeFile)) log.error("delete file error");
            return AppResponse.builder()
                    .message(compileFileMessage.getMessage())
                    .status(AppResponse.Status.ERR_COMPILE.getValue())
                    .build();
        }

        // 3. run code
        List<ExecuteInfo> runCodeMessageList = runCodeFile(codeFile, inputList);

        // 4. cope with run result
        AppResponse response = getResponse(runCodeMessageList);

        // 5. clear file
        if (!deleteFile(codeFile)) log.error("delete file error");

        return response;
    }

    /**
     * 1
     * @param code
     * @return
     */
    public File saveCodeToFile(String code) {
        String userDir = System.getProperty("user.dir");
        String globalCodePathName = userDir + File.separator + CODE_DIR;
        if (!FileUtil.exist(globalCodePathName))FileUtil.mkdir(globalCodePathName);

        // separate users' code file
        String codeFileParentPath = globalCodePathName + File.separator + UUID.randomUUID();
        String codeFilePath = codeFileParentPath + File.separator + JAVA_CLASS_NAME;
        return FileUtil.writeString(code, codeFilePath, StandardCharsets.UTF_8);
    }

    /**
     * 2
     * @param codeFile
     * @return
     */
    public ExecuteInfo compileCodeFile(File codeFile) {
        String compileCmd = String.format("javac -encoding utf-8 %s", codeFile.getAbsolutePath());
        try {
            Process compileProcess = Runtime.getRuntime().exec(compileCmd);
            return ProcessUtils.runProcessAndGetMessage(compileProcess);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 3
     * @param codeFile
     * @param inputList
     * @return
     */
    public List<ExecuteInfo> runCodeFile(File codeFile, List<String> inputList) {
        String codeFileParentPath = codeFile.getParentFile().getAbsolutePath();
        List<ExecuteInfo> runCodeMessageList = new ArrayList<>();
        for (String input : inputList) {
            String runCmd = String.format("java -Xmx256m -Dfile.encoding=UTF-8 -cp %s Main %s", codeFileParentPath, input);
            try {
                Process runProcess = Runtime.getRuntime().exec(runCmd);
                new Thread(() -> {
                    try {
                        Thread.sleep(TIMEOUT);
                        runProcess.destroy();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
                ExecuteInfo executeInfo = ProcessUtils.runProcessAndGetMessage(runProcess);
                runCodeMessageList.add(executeInfo);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return runCodeMessageList;
    }

    /**
     * 4
     * @param executeInfoList
     * @return
     */
    public AppResponse getResponse(List<ExecuteInfo> executeInfoList) {
        AppResponse appResponse = new AppResponse();
        List<String> outputList = new ArrayList<>();

        long maxTime = 0;
        for (ExecuteInfo executeInfo : executeInfoList) {
            if (executeInfo.getExitValue() != 0) {
                appResponse.setMessage(executeInfo.getMessage());
                appResponse.setStatus(AppResponse.Status.ERR_RUNTIME.getValue());
                break;
            }
            outputList.add(executeInfo.getMessage());
            if (executeInfo.getTime() != null) maxTime = Math.max(maxTime, executeInfo.getTime());
        }

        if (outputList.size() == executeInfoList.size()) appResponse.setStatus(AppResponse.Status.OK.getValue());
        else appResponse.setStatus(AppResponse.Status.ERR_RUNTIME.getValue());
        appResponse.setOutputs(outputList);
        appResponse.setTime(maxTime);
        return appResponse;
    }

    /**
     * 5
     * @param codeFile
     * @return
     */
    public boolean deleteFile(File codeFile) {
        if (codeFile.getParentFile() != null) {
            String userCodeParentPath = codeFile.getParentFile().getAbsolutePath();
            return FileUtil.del(userCodeParentPath);
        }
        return true;
    }
}
