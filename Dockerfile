# 使用 JDK 17 的基础镜像
FROM openjdk:17

# 指定临时文件夹
VOLUME /tmp

# 将构建好的 JAR 文件复制到容器中
COPY target/ChatWaveServer-0.0.1-SNAPSHOT.jar ChatWaveServer.jar

# 设置环境变量为 pro 配置
ENV SPRING_PROFILES_ACTIVE=pro

# 设置容器启动时的命令
ENTRYPOINT ["java", "-jar", "/ChatWaveServer.jar"]

# 开放 9000 端口
EXPOSE 9000
