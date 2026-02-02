-- =============================================
-- 基金实时估值监控系统 - 数据库初始化脚本
-- =============================================

-- 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS fund_estimate DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE fund_estimate;

-- =============================================
-- 1. 用户表
-- =============================================
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username` VARCHAR(50) NOT NULL COMMENT '用户名',
    `password` VARCHAR(100) NOT NULL COMMENT '密码（加密存储）',
    `nickname` VARCHAR(50) DEFAULT NULL COMMENT '昵称',
    `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- =============================================
-- 2. 资产账户表（一个用户可以有多个账户，如：支付宝、招行等）
-- =============================================
DROP TABLE IF EXISTS `t_account`;
CREATE TABLE `t_account` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '账户ID',
    `user_id` BIGINT NOT NULL COMMENT '所属用户ID',
    `account_name` VARCHAR(100) NOT NULL COMMENT '账户名称（如：支付宝、招行）',
    `description` VARCHAR(255) DEFAULT NULL COMMENT '账户描述',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资产账户表';

-- =============================================
-- 3. 持仓表（一个账户可以有多个基金持仓）
-- =============================================
DROP TABLE IF EXISTS `t_holding`;
CREATE TABLE `t_holding` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '持仓ID',
    `account_id` BIGINT NOT NULL COMMENT '所属账户ID',
    `fund_code` VARCHAR(10) NOT NULL COMMENT '基金代码',
    `fund_name` VARCHAR(100) DEFAULT NULL COMMENT '基金名称',
    `principal` DECIMAL(12,2) NOT NULL DEFAULT 0.00 COMMENT '持有本金（市值）',
    `initial_profit` DECIMAL(12,2) NOT NULL DEFAULT 0.00 COMMENT '累计收益（昨日）',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_account_id` (`account_id`),
    KEY `idx_fund_code` (`fund_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='基金持仓表';

-- =============================================
-- 4. 自选基金表（用户的自选列表）
-- =============================================
DROP TABLE IF EXISTS `t_watchlist`;
CREATE TABLE `t_watchlist` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '自选ID',
    `user_id` BIGINT NOT NULL COMMENT '所属用户ID',
    `fund_code` VARCHAR(10) NOT NULL COMMENT '基金代码',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_fund` (`user_id`, `fund_code`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='自选基金表';

-- =============================================
-- 插入默认测试用户
-- =============================================
INSERT INTO `t_user` (`username`, `password`, `nickname`) VALUES 
('admin', '123456', '管理员');
