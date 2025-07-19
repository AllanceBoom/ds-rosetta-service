# 肝纤维化研究数据管理系统
# Liver Fibrosis Research Data Management System

## 项目简介

肝纤维化研究数据管理系统是专门为肝纤维化研究设计的综合性数据管理平台，特别针对GALECTIN-3靶向抗体治疗的实验数据分析和管理。系统集成了强大的数据分析功能，支持pandas和numpy进行深度统计分析。

## 研究背景

- **研究领域**: 肝纤维化治疗
- **治疗方法**: 靶向GALECTIN-3的抗体治疗
- **研究目标**: 评估抗体治疗对肝纤维化的疗效
- **数据类型**: 实验数据、生物标志物、影像学数据、临床指标

## 功能特性

### 🔬 实验数据管理
- 实验设计管理
- 样本信息追踪
- 实验结果记录
- 数据版本控制

### 📊 数据分析功能
- **统计分析**: 基于pandas的数据处理
- **数值计算**: numpy支持的高性能计算
- **可视化**: matplotlib, seaborn, plotly图表
- **机器学习**: scikit-learn支持的预测模型

### 🧬 生物标志物分析
- GALECTIN-3表达水平分析
- 纤维化标志物监测
- 炎症因子检测结果
- 肝功能指标评估

### 📈 疗效评估
- 治疗前后对比分析
- 剂量-效应关系
- 时间序列分析
- 生存分析

### 🔐 数据安全
- 用户权限管理
- 数据加密存储
- 审计日志
- 合规性支持

## 技术栈

### 后端 (Python)
- **框架**: Django 4.2 / FastAPI
- **数据库**: PostgreSQL
- **数据分析**: pandas, numpy, scipy
- **可视化**: matplotlib, seaborn, plotly
- **机器学习**: scikit-learn, tensorflow
- **统计分析**: statsmodels

### 前端
- **框架**: React 18 + TypeScript
- **UI组件**: Ant Design
- **图表**: ECharts, D3.js
- **状态管理**: Redux Toolkit

### 数据存储
- **关系数据库**: PostgreSQL
- **文件存储**: MinIO / AWS S3
- **缓存**: Redis
- **搜索**: Elasticsearch

## 项目结构

```
liver-fibrosis-research-system/
├── backend/                    # Python后端
│   ├── apps/                   # Django应用
│   │   ├── authentication/    # 用户认证
│   │   ├── experiments/        # 实验管理
│   │   ├── samples/           # 样本管理
│   │   ├── biomarkers/        # 生物标志物
│   │   ├── analysis/          # 数据分析
│   │   └── reports/           # 报告生成
│   ├── core/                  # 核心配置
│   ├── data_analysis/         # 数据分析模块
│   │   ├── galectin3/        # GALECTIN-3分析
│   │   ├── fibrosis/         # 纤维化分析
│   │   ├── statistics/       # 统计分析
│   │   └── visualization/    # 可视化
│   ├── ml_models/            # 机器学习模型
│   └── requirements.txt
├── frontend/                  # React前端
│   ├── src/
│   │   ├── components/       # 组件
│   │   ├── pages/           # 页面
│   │   ├── services/        # API服务
│   │   ├── utils/           # 工具函数
│   │   └── types/           # 类型定义
│   └── package.json
├── data/                     # 数据目录
│   ├── raw/                 # 原始数据
│   ├── processed/           # 处理后数据
│   └── exports/             # 导出数据
├── notebooks/               # Jupyter笔记本
│   ├── exploratory/        # 探索性分析
│   ├── galectin3_analysis/ # GALECTIN-3分析
│   └── reports/            # 分析报告
├── docs/                    # 文档
├── docker/                  # Docker配置
└── scripts/                 # 脚本文件
```

## 核心分析模块

### GALECTIN-3分析
- 表达水平定量分析
- 时间序列变化趋势
- 与治疗效果的相关性分析
- 预后预测模型

### 肝纤维化评估
- 纤维化分期评估
- 纤维化标志物分析
- 影像学数据处理
- 病理学评分

### 统计分析
- 描述性统计
- 假设检验
- 方差分析
- 回归分析
- 生存分析

## 快速开始

### 环境要求
- Python 3.9+
- Node.js 16+
- PostgreSQL 13+
- Redis 6+

### 安装步骤

1. 克隆项目
```bash
git clone <repository-url>
cd liver-fibrosis-research-system
```

2. 设置Python环境
```bash
cd backend
python -m venv venv
source venv/bin/activate  # Windows: venv\Scripts\activate
pip install -r requirements.txt
```

3. 配置数据库
```bash
python manage.py migrate
python manage.py createsuperuser
```

4. 启动后端服务
```bash
python manage.py runserver
```

5. 安装前端依赖
```bash
cd ../frontend
npm install
npm start
```

### 访问地址
- 前端应用: http://localhost:3000
- 后端API: http://localhost:8000
- 管理后台: http://localhost:8000/admin
- API文档: http://localhost:8000/docs

## 数据分析示例

### GALECTIN-3表达分析
```python
import pandas as pd
import numpy as np
from data_analysis.galectin3 import Galectin3Analyzer

# 加载实验数据
analyzer = Galectin3Analyzer()
data = analyzer.load_experiment_data(experiment_id=123)

# 分析GALECTIN-3表达水平
expression_levels = analyzer.analyze_expression(data)
correlation = analyzer.correlate_with_fibrosis(expression_levels)

# 生成可视化图表
analyzer.plot_expression_trends()
analyzer.plot_dose_response()
```

### 疗效评估分析
```python
from data_analysis.statistics import EffectivenessAnalyzer

analyzer = EffectivenessAnalyzer()

# 治疗前后对比
before_after = analyzer.compare_before_after_treatment()

# 剂量-效应分析
dose_response = analyzer.analyze_dose_response()

# 生存分析
survival_analysis = analyzer.survival_analysis()
```

## 开发指南

### 代码规范
- 遵循PEP 8 Python代码规范
- 使用Black代码格式化
- 使用flake8代码检查
- 前端遵循ESLint规范

### 测试
```bash
# 后端测试
python manage.py test

# 前端测试
npm test
```

### 数据分析最佳实践
1. 数据预处理标准化
2. 统计检验的假设验证
3. 多重比较校正
4. 结果可重现性验证

## 部署

### Docker部署
```bash
docker-compose up -d
```

### 生产环境配置
- 数据库优化
- 缓存配置
- 负载均衡
- 监控告警

## 许可证

本项目采用 MIT 许可证

## 贡献指南

1. Fork 项目
2. 创建功能分支
3. 提交更改
4. 创建 Pull Request

## 联系方式

- 项目维护者: 肝纤维化研究团队
- 邮箱: research@liver-fibrosis.com
- 技术支持: support@liver-fibrosis.com