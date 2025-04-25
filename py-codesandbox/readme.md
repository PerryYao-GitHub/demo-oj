## 代码沙箱app

目前只实现了java的代码沙箱

可以在配置中使用
```yml
codesandbox:
  java:
    type: native # or docker
```
选择原生代码沙箱和docker代码沙箱

使用了模板方法, 把执行代码的流程抽象为
0. 检查代码安全性
1. 将代码存储为文件(于 /tmp-code)
2. 编译代码文件
3. 执行相应字节码
4. 整理执行结果, 获取相应
5. 清理代码文件

在docker实现中, 在1, 2中增加了拉取镜像, 初始化容器, 启动容器等步骤; 重写了编译和执行的逻辑, 让其在容器中编译执行(必须在容器中编译, 不能在宿主机编译, 以免宿主机和容器的jdk版本不同)

swagger测试样例
```json
{
  "inputs": [
    "1 2", "3 4", "5 6"
  ],
  "code": "public class Main {\n    public static void main(String[] args) {\n        System.out.println(\"Hello\");\n    }\n}",
  "lang": 0
}
```
