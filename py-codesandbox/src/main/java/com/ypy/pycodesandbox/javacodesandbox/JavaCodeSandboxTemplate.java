package com.ypy.pycodesandbox.javacodesandbox;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.ypy.pycodesandbox.CodeSandbox;
import com.ypy.pycodesandbox.enums.LangEnum;
import com.ypy.pycodesandbox.model.ExecuteInfo;
import com.ypy.pycodesandbox.app.AppRequest;
import com.ypy.pycodesandbox.app.AppResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Slf4j
public abstract class JavaCodeSandboxTemplate implements CodeSandbox {

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

    @Override
    public AppResponse exec(AppRequest request) {
        // 0. check valid request
        if (!validRequest(request)) return new AppResponse(AppResponse.Status.ERR_REQUEST, "");

        List<String> inputList = request.getInputs();
        String code = request.getCode();

        // 1. save code into file
        File codeFile = saveCodeToFile(code);

        // 2. compile code file
        ExecuteInfo compileExecuteInfo = compileCodeFile(codeFile);

        if (compileExecuteInfo.getExitCode() != 0) { // compile error
            if (!deleteFile(codeFile)) log.error("delete file error");
            return new AppResponse(AppResponse.Status.ERR_COMPILE, compileExecuteInfo.getMessage());
        }

        // 3. run code
        List<ExecuteInfo> runExecuteInfoList = runCodeFile(codeFile, inputList);

        // 4. cope with run result
        AppResponse response = getResponseFromRunExecuteInfoList(runExecuteInfoList);
        if (!deleteFile(codeFile)) log.error("delete file error");

        return response;
    }

    /**
     * 0
     * @param appRequest
     * @return
     */
    public boolean validRequest(AppRequest appRequest)
    {
        String code = appRequest.getCode();
        // todo: safe code
        Byte lang = appRequest.getLang();
        return lang != null && lang == LangEnum.JAVA.getValue();
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
            String runCmd = String.format("java -Xmx256m -Dfile.encoding=UTF-8 -cp %s Main", codeFileParentPath);
            try {
                Process runProcess = Runtime.getRuntime().exec(runCmd);

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(runProcess.getOutputStream()));
                writer.write(input);
                writer.newLine();
                writer.flush();
                writer.close();

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
        List<String> outputList = new ArrayList<>();

        long maxTime = 0;
        long maxMemory = 0;
        for (ExecuteInfo executeInfo : executeInfoList) {
            if (executeInfo.getExitCode() != 0) return new AppResponse(AppResponse.Status.ERR_RUNTIME, executeInfo.getMessage());

            outputList.add(executeInfo.getMessage());
            if (executeInfo.getTime() != null) maxTime = Math.max(maxTime, executeInfo.getTime());
            if (executeInfo.getMemory() != null) maxMemory = Math.max(maxMemory, executeInfo.getMemory());
        }
        outputList = outputList.stream().map(String::trim).collect(Collectors.toList());
        return new AppResponse(outputList, maxTime, maxMemory);
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
