-- 肝纤维化研究数据管理系统 MySQL 初始化脚本

-- 设置字符集
SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;

-- 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS liver_fibrosis_db 
    CHARACTER SET utf8mb4 
    COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE liver_fibrosis_db;

-- 创建用户（如果不存在）
CREATE USER IF NOT EXISTS 'lfrs_user'@'%' IDENTIFIED BY 'lfrs_password';

-- 授予权限
GRANT ALL PRIVILEGES ON liver_fibrosis_db.* TO 'lfrs_user'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, INDEX, ALTER, CREATE TEMPORARY TABLES, LOCK TABLES ON liver_fibrosis_db.* TO 'lfrs_user'@'%';

-- 刷新权限
FLUSH PRIVILEGES;

-- 创建一些基础表结构的示例（Django会自动创建，这里仅作参考）
-- 实际的表结构将由Django migrations创建

-- 纤维化分期枚举表
CREATE TABLE IF NOT EXISTS fibrosis_stages (
    id INT AUTO_INCREMENT PRIMARY KEY,
    stage_code VARCHAR(10) NOT NULL UNIQUE COMMENT '分期代码 (F0-F4)',
    stage_name VARCHAR(50) NOT NULL COMMENT '分期名称',
    description TEXT COMMENT '分期描述',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='纤维化分期表';

-- 插入纤维化分期数据
INSERT IGNORE INTO fibrosis_stages (stage_code, stage_name, description) VALUES
('F0', '无纤维化', '肝脏组织无纤维化'),
('F1', '轻度纤维化', '门静脉周围纤维化，无间隔'),
('F2', '中度纤维化', '门静脉周围纤维化，伴有少量间隔'),
('F3', '重度纤维化', '大量间隔，但无肝硬化'),
('F4', '肝硬化', '肝硬化');

-- 治疗组类型表
CREATE TABLE IF NOT EXISTS treatment_groups (
    id INT AUTO_INCREMENT PRIMARY KEY,
    group_code VARCHAR(20) NOT NULL UNIQUE COMMENT '组别代码',
    group_name VARCHAR(100) NOT NULL COMMENT '组别名称',
    description TEXT COMMENT '组别描述',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='治疗组类型表';

-- 插入治疗组数据
INSERT IGNORE INTO treatment_groups (group_code, group_name, description) VALUES
('control', '对照组', '不接受GALECTIN-3靶向抗体治疗的对照组'),
('treatment', '治疗组', '接受GALECTIN-3靶向抗体治疗的实验组'),
('placebo', '安慰剂组', '接受安慰剂治疗的对照组');

-- 生物标志物类型表
CREATE TABLE IF NOT EXISTS biomarker_types (
    id INT AUTO_INCREMENT PRIMARY KEY,
    marker_code VARCHAR(50) NOT NULL UNIQUE COMMENT '标志物代码',
    marker_name VARCHAR(100) NOT NULL COMMENT '标志物名称',
    unit VARCHAR(20) COMMENT '单位',
    normal_range_min DECIMAL(10,4) COMMENT '正常范围最小值',
    normal_range_max DECIMAL(10,4) COMMENT '正常范围最大值',
    description TEXT COMMENT '标志物描述',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='生物标志物类型表';

-- 插入生物标志物数据
INSERT IGNORE INTO biomarker_types (marker_code, marker_name, unit, normal_range_min, normal_range_max, description) VALUES
('GALECTIN3', 'GALECTIN-3', 'ng/mL', 0.5, 3.0, 'GALECTIN-3蛋白表达水平'),
('ALT', '丙氨酸转氨酶', 'U/L', 7, 40, '肝功能指标'),
('AST', '天冬氨酸转氨酶', 'U/L', 13, 35, '肝功能指标'),
('TBIL', '总胆红素', 'μmol/L', 3.4, 20.5, '肝功能指标'),
('ALB', '白蛋白', 'g/L', 40, 55, '肝功能指标'),
('PT', '凝血酶原时间', 's', 11, 13, '凝血功能指标'),
('INR', '国际标准化比值', '', 0.8, 1.1, '凝血功能指标');

-- 创建索引以提高查询性能
CREATE INDEX IF NOT EXISTS idx_fibrosis_stages_code ON fibrosis_stages(stage_code);
CREATE INDEX IF NOT EXISTS idx_treatment_groups_code ON treatment_groups(group_code);
CREATE INDEX IF NOT EXISTS idx_biomarker_types_code ON biomarker_types(marker_code);

-- 显示创建的表
SHOW TABLES;