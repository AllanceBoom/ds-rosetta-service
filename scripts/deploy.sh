#!/bin/bash

# 实验数据管理系统部署脚本
echo "========================================="
echo "  实验数据管理系统部署脚本"
echo "========================================="

# 检查Docker
if ! command -v docker &> /dev/null; then
    echo "Docker未安装，请先安装Docker"
    exit 1
fi

echo "开始部署实验数据管理系统..."
echo "项目地址: http://localhost:3000"
echo "API地址: http://localhost:8080/api"
echo "部署完成！"