# 使用OpenJDK 8作为基础镜像
FROM openjdk:8-jre-slim

# 设置工作目录
WORKDIR /app

# 创建应用用户
RUN groupadd -r edms && useradd -r -g edms edms

# 安装必要的工具
RUN apt-get update && apt-get install -y \
    curl \
    && rm -rf /var/lib/apt/lists/*

# 复制Maven构建的jar文件
COPY target/experimental-data-management-system-*.jar app.jar

# 创建日志和上传目录
RUN mkdir -p /app/logs /app/uploads && \
    chown -R edms:edms /app

# 切换到应用用户
USER edms

# 暴露端口
EXPOSE 8080

# 健康检查
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/api/actuator/health || exit 1

# 启动应用
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]