-- -------------------------------------------------
-- 不会被SpringBoot调用，项目初始化时通过命令行创建
-- 建库脚本
-- -------------------------------------------------

-- 创建用户
create user 'springboot'@'%' identified by 'SpringBoot@123';

-- 授权用户
grant all privileges on *.* to 'springboot'@'%' with grant option;

-- 创建数据库
CREATE DATABASE IF NOT EXISTS wechatdb DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
use wechatdb;
