# API 文档

## 概述

实验数据管理系统提供RESTful API，用于管理实验数据、用户认证、权限控制等功能。

## 基础信息

- **基础URL**: `http://localhost:8080/api`
- **认证方式**: JWT Bearer Token
- **请求格式**: JSON
- **响应格式**: JSON

## 认证

### 用户注册

```http
POST /auth/register
```

**请求体**:
```json
{
  "username": "string",
  "password": "string",
  "email": "string",
  "fullName": "string",
  "department": "string",
  "position": "string"
}
```

### 用户登录

```http
POST /auth/login
```

**请求体**:
```json
{
  "username": "string",
  "password": "string"
}
```

**响应**:
```json
{
  "code": 200,
  "message": "登录成功",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "user": {
      "id": 1,
      "username": "admin",
      "email": "admin@example.com",
      "fullName": "管理员",
      "roles": ["ADMIN"]
    }
  }
}
```

### 刷新Token

```http
POST /auth/refresh
```

## 用户管理

### 获取用户列表

```http
GET /users?page=0&size=10&sort=id,desc
```

**响应**:
```json
{
  "code": 200,
  "message": "成功",
  "data": {
    "content": [
      {
        "id": 1,
        "username": "admin",
        "email": "admin@example.com",
        "fullName": "管理员",
        "department": "IT部门",
        "position": "系统管理员",
        "enabled": true,
        "createdAt": "2024-01-01T00:00:00"
      }
    ],
    "totalElements": 1,
    "totalPages": 1,
    "number": 0,
    "size": 10
  }
}
```

### 获取用户详情

```http
GET /users/{id}
```

### 更新用户信息

```http
PUT /users/{id}
```

**请求体**:
```json
{
  "fullName": "string",
  "email": "string",
  "department": "string",
  "position": "string"
}
```

### 删除用户

```http
DELETE /users/{id}
```

## 实验管理

### 创建实验

```http
POST /experiments
```

**请求体**:
```json
{
  "title": "实验标题",
  "description": "实验描述",
  "category": "实验分类",
  "tags": ["标签1", "标签2"],
  "metadata": {
    "key1": "value1",
    "key2": "value2"
  }
}
```

### 获取实验列表

```http
GET /experiments?page=0&size=10&category=分类&keyword=关键词
```

### 获取实验详情

```http
GET /experiments/{id}
```

### 更新实验

```http
PUT /experiments/{id}
```

### 删除实验

```http
DELETE /experiments/{id}
```

## 数据管理

### 上传数据文件

```http
POST /data/upload
Content-Type: multipart/form-data
```

**请求参数**:
- `file`: 文件
- `experimentId`: 实验ID
- `description`: 描述

### 获取数据列表

```http
GET /data?experimentId={id}&page=0&size=10
```

### 下载数据文件

```http
GET /data/{id}/download
```

### 删除数据文件

```http
DELETE /data/{id}
```

## 统计分析

### 获取系统统计

```http
GET /statistics/overview
```

**响应**:
```json
{
  "code": 200,
  "message": "成功",
  "data": {
    "totalUsers": 100,
    "totalExperiments": 50,
    "totalDataFiles": 200,
    "storageUsed": "1.2GB"
  }
}
```

### 获取用户活动统计

```http
GET /statistics/user-activity?days=30
```

### 获取实验统计

```http
GET /statistics/experiments?category=分类
```

## 错误码

| 错误码 | 说明 |
|--------|------|
| 200 | 成功 |
| 400 | 请求参数错误 |
| 401 | 未认证 |
| 403 | 权限不足 |
| 404 | 资源不存在 |
| 409 | 资源冲突 |
| 500 | 服务器内部错误 |

## 响应格式

所有API响应都遵循统一格式：

```json
{
  "code": 200,
  "message": "成功",
  "data": {},
  "timestamp": "2024-01-01T00:00:00Z"
}
```

## 分页参数

- `page`: 页码，从0开始
- `size`: 每页大小，默认10
- `sort`: 排序字段和方向，如`id,desc`

## 认证头部

需要认证的API请求需要在头部包含：

```http
Authorization: Bearer {token}
```