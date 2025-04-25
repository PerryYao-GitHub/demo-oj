package com.ypy.pycodesandbox.javacodesandbox;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.util.ArrayUtil;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.*;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.ExecStartResultCallback;
import com.ypy.pycodesandbox.app.AppRequest;
import com.ypy.pycodesandbox.app.AppResponse;
import com.ypy.pycodesandbox.model.ExecuteInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component("JavaDockerCodeSandbox")
public class JavaDockerCodeSandbox extends JavaCodeSandboxTemplate {

    private static DockerClient dockerClient = DockerClientBuilder.getInstance().build();

    private static final long TIMEOUT = 8000L;

    private static final String DOCKER_IMG = "openjdk:8-alpine";

    private static final ThreadLocal<String> containerIdHolder = new ThreadLocal<>();

    private void ensureImgExist()
    {
        List<Image> images = dockerClient.listImagesCmd().withImageNameFilter(DOCKER_IMG).exec();
        if (images == null || images.isEmpty()) {
            PullImageCmd pullImageCmd = dockerClient.pullImageCmd(DOCKER_IMG);
            PullImageResultCallback pullImageResultCallback = new PullImageResultCallback();
            try {
                pullImageCmd.exec(pullImageResultCallback).awaitCompletion();
            } catch (InterruptedException e) {
                throw new RuntimeException("Pull Image Error", e);
            }
        }
    }

    private void createAndStartContainer(String codeFileDir)
    {
        CreateContainerCmd createContainerCmd = dockerClient.createContainerCmd(DOCKER_IMG);
        HostConfig hostConfig = new HostConfig();
        hostConfig.setBinds(new Bind(codeFileDir, new Volume("/app"))); // sync local file(s)(codeFileParent) into container (/app) 容器挂载目录
        hostConfig.withMemory(100_000_000L); // allowed memory
        hostConfig.withMemorySwap(0L);
        hostConfig.withCpuCount(1L);
        hostConfig.withAutoRemove(false);

        CreateContainerResponse createContainerResponse = createContainerCmd
                .withHostConfig(hostConfig)
                .withAttachStdin(true)
                .withAttachStderr(true)
                .withAttachStdout(true)
                .withTty(true)
                .withNetworkDisabled(true)
                .exec();
        String containerId = createContainerResponse.getId();
        containerIdHolder.set(containerId);

        dockerClient.startContainerCmd(containerId).exec();
    }

    private void stopAndDestroyContainer()
    {
        String containerId = containerIdHolder.get();
        dockerClient.stopContainerCmd(containerId).exec();
        dockerClient.removeContainerCmd(containerId).exec();
        containerIdHolder.remove();
    }




    @Override
    public AppResponse exec(AppRequest request) {
        // 0. check valid request
        if (!validRequest(request)) return new AppResponse(AppResponse.Status.ERR_REQUEST, "");

        List<String> inputList = request.getInputs();
        String code = request.getCode();

        // 1. save code into file
        File codeFile = saveCodeToFile(code);

        // 1.5 init container and get containerId
        String codeFileDir = codeFile.getParentFile().getAbsolutePath();
        ensureImgExist();
        createAndStartContainer(codeFileDir);

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

    @Override
    public ExecuteInfo compileCodeFile(File codeFile)
    {
        String containerId = containerIdHolder.get();

        ExecuteInfo compileInfo = new ExecuteInfo();
        StopWatch stopWatch = new StopWatch();

        String[] compileCmd = {"javac", "-encoding", "utf-8", "/app/Main.java"};

        ExecCreateCmdResponse execCreateCmdResponse = dockerClient.execCreateCmd(containerId)
                .withCmd(compileCmd)
                .withAttachStdout(true)
                .withAttachStderr(true)
                .exec();

        String execId = execCreateCmdResponse.getId();

        final StringBuilder messageBuilder = new StringBuilder();
        final Integer[] exitCode = {0};

        ExecStartResultCallback execStartResultCallback = new ExecStartResultCallback() {
            @Override
            public void onNext(Frame frame) {
                StreamType streamType = frame.getStreamType();
                messageBuilder.append(new String(frame.getPayload()));
                if (StreamType.STDERR.equals(streamType)) exitCode[0] = 1;
                super.onNext(frame);
            }
        };

        stopWatch.start();
        try {
            dockerClient.execStartCmd(execId)
                    .exec(execStartResultCallback)
                    .awaitCompletion(TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        stopWatch.stop();

        compileInfo.setExitCode(exitCode[0]);
        compileInfo.setMessage(messageBuilder.toString());
        compileInfo.setTime(stopWatch.getLastTaskTimeMillis());

        if (exitCode[0] != 0) stopAndDestroyContainer();
        return compileInfo;
    }

    /**
     * 3 create docker, copy file into docker
     * @param codeFile
     * @param inputList
     * @return
     */
    @Override
    public List<ExecuteInfo> runCodeFile(File codeFile, List<String> inputList)
    {
        String containerId = containerIdHolder.get();
        List<ExecuteInfo> executeInfoList = new ArrayList<>();
        // get result in loop (run code in loop)
        for (String input : inputList) {
            ExecuteInfo executeInfo = new ExecuteInfo();
            StopWatch stopWatch = new StopWatch();

            // create Exec instance, but not execute it yet
            String[] inputArgsArray = input.split(" ");
            String[] cmdArray = ArrayUtil.append(new String[]{"java", "-cp", "/app", "Main"}, inputArgsArray);
            ExecCreateCmdResponse execCreateCmdResponse = dockerClient.execCreateCmd(containerId)
                    .withCmd(cmdArray)
                    .withAttachStderr(true)
                    .withAttachStdin(true)
                    .withAttachStdout(true)
                    .exec();

            // config time record
            final StringBuilder messageBuilder = new StringBuilder();
            final Integer[] exitCode = {0};
            long time = 0L;
            String execId = execCreateCmdResponse.getId();
            ExecStartResultCallback execStartResultCallback = new ExecStartResultCallback() {
                @Override
                public void onNext(Frame frame) {
                    StreamType streamType = frame.getStreamType();
                    messageBuilder.append(new String(frame.getPayload()));
                    if (StreamType.STDERR.equals(streamType)) exitCode[0] = 1; // error
                    super.onNext(frame);
                }
            };

            // config memory record (container resources monitor)
            final long[] maxMemory = {0L};
            StatsCmd statsCmd = dockerClient.statsCmd(containerId);
            statsCmd.exec(new ResultCallback<Statistics>() {
                @Override
                public void onStart(Closeable closeable) {}

                @Override
                public void onNext(Statistics statistics) {
                    maxMemory[0] = Math.max(maxMemory[0], statistics.getMemoryStats().getUsage());
                }

                @Override
                public void onError(Throwable throwable) {}

                @Override
                public void onComplete() {}

                @Override
                public void close() throws IOException {}
            });

            // execute here
            try {
                stopWatch.start();
                dockerClient.execStartCmd(execId)
                        .exec(execStartResultCallback)
                        .awaitCompletion(TIMEOUT, TimeUnit.MILLISECONDS);
                stopWatch.stop();
                time = stopWatch.getLastTaskTimeMillis();
            } catch (InterruptedException e) {
                exitCode[0] = 1;
                messageBuilder.append("Timeout Error");
            } finally {
                statsCmd.close();
            }

            executeInfo.setMessage(messageBuilder.toString());
            executeInfo.setExitCode(exitCode[0]);
            executeInfo.setTime(time);
            executeInfo.setMemory(maxMemory[0]);
            executeInfoList.add(executeInfo);
        }

        // destroy container out of loop
        stopAndDestroyContainer();
        return executeInfoList;
    }
}
