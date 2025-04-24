package com.ypy.pycodesandbox;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.ypy.pycodesandbox.enums.LangEnum;
import com.ypy.pycodesandbox.model.ExecuteInfo;
import com.ypy.pycodesandbox.model.AppRequest;
import com.ypy.pycodesandbox.model.AppResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Slf4j
public abstract class JavaCodeSandboxTemplate implements CodeSandbox{

    private static final String CODE_DIR = "tmp-code";

    private static final String JAVA_CLASS_NAME = "Main.java";

    private static final long TIMEOUT = 5000L;

    private ExecuteInfo executeProcess(Process process)
    {
        ExecuteInfo executeInfo = new ExecuteInfo();
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            int exitCode = process.waitFor();
            executeInfo.setExitCode(exitCode);

            if (exitCode == 0) { // exit without error
                // attain output
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                List<String> outputStrList = new ArrayList<>();
                // read by line
                String compileOutputLine;
                while ((compileOutputLine = bufferedReader.readLine()) != null) {
                    outputStrList.add(compileOutputLine);
                }
                executeInfo.setMessage(StrUtil.join("\n", outputStrList));
            } else { // exit with error
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                List<String> outputStrList = new ArrayList<>();

                String compileOutputLine;
                while ((compileOutputLine = bufferedReader.readLine()) != null) {
                    outputStrList.add(compileOutputLine);
                }
                executeInfo.setMessage(StrUtil.join("\n", outputStrList));

                BufferedReader errorBufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                List<String> errorOutputStrList = new ArrayList<>();
                String errorCompileOutputLine;
                while ((errorCompileOutputLine = errorBufferedReader.readLine()) != null) {
                    errorOutputStrList.add(errorCompileOutputLine);
                }
                executeInfo.setMessage(StrUtil.join("\n", errorOutputStrList)); // error message
            }
            stopWatch.stop();
            executeInfo.setTime(stopWatch.getLastTaskTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return executeInfo;
    }

    private boolean validRequest(AppRequest appRequest) {
        String code = appRequest.getCode();

        Byte lang = appRequest.getLang();
        if (lang == null || lang != LangEnum.JAVA.getValue()) return false;
        return true;
    }

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
        ExecuteInfo compileExecuteInfo = compileCodeFile(codeFile);

        if (compileExecuteInfo.getExitCode() != 0) { // compile error
            if (!deleteFile(codeFile)) log.error("delete file error");
            return AppResponse.builder()
                    .message(compileExecuteInfo.getMessage())
                    .status(AppResponse.Status.ERR_COMPILE.getValue())
                    .build();
        }

        // 3. run code
        List<ExecuteInfo> runExecuteInfoList = runCodeFile(codeFile, inputList);
        System.out.println(runExecuteInfoList);

        // 4. cope with run result
        AppResponse response = getResponseFromRunExecuteInfoList(runExecuteInfoList);
        if (!deleteFile(codeFile)) log.error("delete file error");

        return response;
    }

    /**
     * 1
     * @param code
     * @return
     */
    public File saveCodeToFile(String code)
    {
        String userDir = System.getProperty("user.dir");
        String globalCodePath = userDir + File.separator + CODE_DIR;
        if (!FileUtil.exist(globalCodePath))FileUtil.mkdir(globalCodePath);

        // separate users' code file
        String codeFileParentPath = globalCodePath + File.separator + UUID.randomUUID();
        String codeFilePath = codeFileParentPath + File.separator + JAVA_CLASS_NAME;
        return FileUtil.writeString(code, codeFilePath, StandardCharsets.UTF_8);
    }

    /**
     * 2
     * @param codeFile
     * @return
     */
    public ExecuteInfo compileCodeFile(File codeFile)
    {
        String compileCmd = String.format("javac -encoding utf-8 %s", codeFile.getAbsolutePath());
        try {
            Process compileProcess = Runtime.getRuntime().exec(compileCmd);
            return executeProcess(compileProcess);
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
    public List<ExecuteInfo> runCodeFile(File codeFile, List<String> inputList)
    {
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
                ExecuteInfo executeInfo = executeProcess(runProcess);

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
    public AppResponse getResponseFromRunExecuteInfoList(List<ExecuteInfo> executeInfoList)
    {
        AppResponse appResponse = new AppResponse();
        List<String> outputList = new ArrayList<>();

        long maxTime = 0;
        long maxMemory = 0;
        for (ExecuteInfo executeInfo : executeInfoList) {
            if (executeInfo.getExitCode() != 0) {
                appResponse.setMessage(executeInfo.getMessage());
                appResponse.setStatus(AppResponse.Status.ERR_RUNTIME.getValue());
                break;
            }
            outputList.add(executeInfo.getMessage());
            if (executeInfo.getTime() != null) maxTime = Math.max(maxTime, executeInfo.getTime());
            if (executeInfo.getMemory() != null) maxMemory = Math.max(maxMemory, executeInfo.getMemory());
        }

        if (outputList.size() == executeInfoList.size()) {
            appResponse.setStatus(AppResponse.Status.OK.getValue());
            appResponse.setOutputs(outputList);
        } else appResponse.setStatus(AppResponse.Status.ERR_RUNTIME.getValue());

        appResponse.setMemory(maxMemory);
        appResponse.setTime(maxTime);
        return appResponse;
    }

    /**
     * 5
     * @param codeFile
     * @return
     */
    public boolean deleteFile(File codeFile)
    {
        if (codeFile.getParentFile() != null) {
            String userCodeParentPath = codeFile.getParentFile().getAbsolutePath();
            return FileUtil.del(userCodeParentPath);
        }
        return true;
    }
}
