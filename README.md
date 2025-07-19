# 实验数据管理系统 (Experimental Data Management System - EDMS)

## 项目简介

实验数据管理系统是一个用于管理科研实验数据的综合性平台，提供数据存储、分析、可视化和共享等功能。

## 功能特性

- 📊 **数据管理**: 实验数据的上传、存储和组织
- 🔍 **数据检索**: 强大的搜索和过滤功能
- 📈 **数据分析**: 内置统计分析工具
- 📋 **实验记录**: 完整的实验流程记录
- 👥 **协作共享**: 团队协作和数据共享
- 🔐 **权限控制**: 细粒度的访问权限管理
- 📱 **响应式设计**: 支持多设备访问

## 技术栈

### 后端
- **框架**: Spring Boot 2.7.x
- **数据库**: MySQL 8.0
- **ORM**: Spring Data JPA
- **安全**: Spring Security + JWT
- **文档**: Swagger/OpenAPI

### 前端
- **框架**: React 18
- **状态管理**: Redux Toolkit
- **UI组件**: Ant Design
- **图表**: ECharts
- **构建工具**: Vite

## 项目结构

```
experimental-data-management-system/
├── backend/                    # 后端Spring Boot项目
│   ├── src/main/java/
│   │   └── com/edms/
│   │       ├── controller/     # 控制器层
│   │       ├── service/        # 服务层
│   │       ├── repository/     # 数据访问层
│   │       ├── entity/         # 实体类
│   │       ├── dto/           # 数据传输对象
│   │       ├── config/        # 配置类
│   │       └── utils/         # 工具类
│   ├── src/main/resources/
│   └── pom.xml
├── frontend/                   # 前端React项目
│   ├── src/
│   │   ├── components/        # 组件
│   │   ├── pages/            # 页面
│   │   ├── services/         # API服务
│   │   ├── store/            # 状态管理
│   │   └── utils/            # 工具函数
│   ├── public/
│   └── package.json
├── docs/                      # 项目文档
├── scripts/                   # 部署脚本
└── docker-compose.yml         # Docker编排文件
```

## 快速开始

### 环境要求

- Java 8+
- Node.js 16+
- MySQL 8.0+
- Git

### 安装步骤

1. 克隆项目
```bash
git clone <repository-url>
cd experimental-data-management-system
```

2. 启动后端服务
```bash
cd backend
mvn spring-boot:run
```

3. 启动前端服务
```bash
cd frontend
npm install
npm start
```

4. 访问应用
- 前端: http://localhost:3000
- 后端API: http://localhost:8080
- API文档: http://localhost:8080/swagger-ui.html

## 开发指南

### 代码规范
- 遵循阿里巴巴Java开发手册
- 使用ESLint和Prettier格式化前端代码
- 提交信息遵循Conventional Commits规范

### 分支管理
- `main`: 主分支，用于生产环境
- `develop`: 开发分支
- `feature/*`: 功能分支
- `hotfix/*`: 热修复分支

## 贡献指南

1. Fork 项目
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建 Pull Request

## 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情

## 联系方式

- 项目维护者: [Your Name]
- 邮箱: [your.email@example.com]
- 项目链接: [https://github.com/yourusername/experimental-data-management-system](https://github.com/yourusername/experimental-data-management-system)