# 使用Python 3.9作为基础镜像
FROM python:3.9-slim

# 设置工作目录
WORKDIR /app

# 设置环境变量
ENV PYTHONDONTWRITEBYTECODE=1
ENV PYTHONUNBUFFERED=1

# 安装系统依赖
RUN apt-get update \
    && apt-get install -y --no-install-recommends \
        default-mysql-client \
        default-libmysqlclient-dev \
        build-essential \
        pkg-config \
        curl \
        git \
        libhdf5-dev \
        netcat-openbsd \
    && rm -rf /var/lib/apt/lists/*

# 创建应用用户
RUN groupadd -r lfrs && useradd -r -g lfrs lfrs

# 复制requirements文件
COPY requirements.txt .

# 安装Python依赖
RUN pip install --no-cache-dir --upgrade pip \
    && pip install --no-cache-dir -r requirements.txt

# 复制项目文件
COPY . .

# 创建必要的目录
RUN mkdir -p /app/logs /app/media /app/staticfiles \
    && chown -R lfrs:lfrs /app

# 收集静态文件
RUN python manage.py collectstatic --noinput

# 切换到应用用户
USER lfrs

# 暴露端口
EXPOSE 8000

# 健康检查
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8000/api/health/ || exit 1

# 启动脚本
COPY --chown=lfrs:lfrs docker-entrypoint.sh /app/
RUN chmod +x /app/docker-entrypoint.sh

ENTRYPOINT ["/app/docker-entrypoint.sh"]