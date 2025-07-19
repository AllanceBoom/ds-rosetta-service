# 肝纤维化研究数据管理系统

基于Python和Django的肝纤维化研究数据管理系统，专门用于GALECTIN-3靶向抗体治疗的数据分析。

## 特点
- 🐍 Python后端 (Django + DRF)
- ⚛️ React前端
- 📊 pandas + numpy数据分析
- 🧬 GALECTIN-3专项分析
- 📈 机器学习预测
- 🔬 医学数据处理
- 🗄️ MySQL数据库

## 技术栈
- **Backend**: Django, pandas, numpy, scikit-learn
- **Frontend**: React, TypeScript, Ant Design
- **Database**: MySQL 8.0
- **Cache**: Redis
- **Analysis**: Jupyter Notebooks
- **Deploy**: Docker + Docker Compose

## 环境要求
- Python 3.9+
- Node.js 16+
- MySQL 8.0+
- Redis 6+

## 快速开始

### 方式一：Docker部署（推荐）
```bash
# 克隆项目
git clone <repository-url>
cd liver-fibrosis-research-system

# 启动所有服务
docker-compose up -d

# 访问应用
# 前端: http://localhost:3000
# 后端API: http://localhost:8000
# 管理后台: http://localhost:8000/admin
# Jupyter: http://localhost:8888 (token: liver-fibrosis-research)
```

### 方式二：本地开发
```bash
# 1. 安装MySQL并创建数据库
mysql -u root -p
CREATE DATABASE liver_fibrosis_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# 2. 安装后端依赖
cd backend
pip install -r requirements.txt

# 3. 配置环境变量（可选）
export DB_NAME=liver_fibrosis_db
export DB_USER=root
export DB_PASSWORD=your_password
export DB_HOST=localhost
export DB_PORT=3306

# 4. 运行数据库迁移
python manage.py migrate

# 5. 创建超级用户
python manage.py createsuperuser

# 6. 启动服务
python manage.py runserver

# 7. 安装前端依赖（另一个终端）
cd frontend
npm install
npm start
```

## 核心功能

### 🧬 GALECTIN-3分析
- 表达水平定量分析
- 治疗前后对比
- 与纤维化程度相关性
- 时间序列变化趋势
- 剂量-效应分析

### 📊 统计分析
- t检验、Mann-Whitney U检验
- 相关性分析（Pearson、Spearman）
- 线性回归和多元回归
- 生存分析（Kaplan-Meier）
- 方差分析（ANOVA）

### 🤖 机器学习
- 随机森林回归
- 特征重要性分析
- 预测模型构建
- 交叉验证
- 模型评估

### 📈 数据可视化
- matplotlib、seaborn静态图表
- plotly交互式图表
- 医学专用图表类型
- 实时数据监控

## 数据库结构

系统使用MySQL 8.0，主要包含以下核心表：
- **用户管理**: Django默认用户表
- **实验数据**: 实验设计、样本信息
- **生物标志物**: GALECTIN-3等标志物数据
- **纤维化分期**: F0-F4分期数据
- **治疗组信息**: 对照组、治疗组、安慰剂组
- **分析结果**: 统计分析和ML模型结果

## API文档

启动服务后访问：
- Swagger UI: http://localhost:8000/api/docs/
- ReDoc: http://localhost:8000/api/redoc/

## 开发指南

### 代码规范
- Python: PEP 8, Black格式化
- JavaScript/TypeScript: ESLint + Prettier
- 提交信息: Conventional Commits

### 测试
```bash
# 后端测试
cd backend
python manage.py test

# 前端测试
cd frontend
npm test
```

### 数据分析
使用Jupyter Notebooks进行数据分析：
```bash
# 启动Jupyter
jupyter notebook notebooks/

# 或使用Docker
docker-compose exec jupyter jupyter notebook --ip=0.0.0.0 --port=8888
```

## 部署

### 生产环境部署
1. 配置环境变量
2. 设置SSL证书
3. 配置域名和反向代理
4. 启用监控和日志

```bash
# 生产环境启动
docker-compose -f docker-compose.yml -f docker-compose.prod.yml up -d
```

## 贡献指南

1. Fork项目
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建Pull Request

## 许可证

本项目采用MIT许可证 - 查看 [LICENSE](LICENSE) 文件了解详情

## 支持

- 📧 技术支持: support@liver-fibrosis.com
- 📖 文档: 查看 `docs/` 目录
- 🐛 问题反馈: 创建GitHub Issue