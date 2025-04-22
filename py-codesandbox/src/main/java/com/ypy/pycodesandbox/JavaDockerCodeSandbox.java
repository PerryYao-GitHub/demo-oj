package com.ypy.pycodesandbox;

//public class JavaDockerCodeSandbox extends JavaCodeSandboxTemplate {
//
//    private static final Boolean FIRST_INIT = true;
//
//    private static final String DOCKER_IMG = "openjdk:8-alpine";
//
//    /**
//     * 3 create docker, copy file into docker
//     * @param codeFile
//     * @param inputList
//     * @return
//     */
//    @Override
//    public List<ExecuteMessage> runCodeFile(File codeFile, List<String> inputList) {
//        String codeFileParentPath = codeFile.getParent();
//
//        DockerClient dockerClient = DockerClientBuilder.getInstance().build();
//
//        if (FIRST_INIT) { // pull image
//            PullImageCmd pullImageCmd = dockerClient.pullImageCmd(DOCKER_IMG);
//            PullImageResultCallback pullImageResultCallback = new PullImageResultCallback();
//
//            try {
//                pullImageCmd.exec(pullImageResultCallback).awaitCompletion();
//            } catch (InterruptedException e) {
//                throw new RuntimeException("Pull Image Error", e);
//            }
//        }
//
//
//    }
//}
