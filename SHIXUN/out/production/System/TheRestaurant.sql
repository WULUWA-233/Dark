-- 创建数据库
CREATE DATABASE IF NOT EXISTS catering_db DEFAULT CHARSET utf8mb4;
USE catering_db;

-- 管理员表
CREATE TABLE IF NOT EXISTS user (
        id INT PRIMARY KEY AUTO_INCREMENT,
        username VARCHAR(50) NOT NULL UNIQUE,
password VARCHAR(100) NOT NULL
);

        -- 员工表
CREATE TABLE IF NOT EXISTS employee (
        empId INT PRIMARY KEY,
        name VARCHAR(50) NOT NULL,
gender VARCHAR(10),
position VARCHAR(50),
address VARCHAR(100),
salary DOUBLE
);

        -- 顾客表
CREATE TABLE IF NOT EXISTS customer (
        customerId INT PRIMARY KEY,
        name VARCHAR(50) NOT NULL,
phone VARCHAR(20),
gender VARCHAR(10)
);

        -- 餐台表
CREATE TABLE IF NOT EXISTS desk (
        deskId INT PRIMARY KEY,
        areaId INT,
        capacity INT,
        status VARCHAR(20)
);

        -- 菜品分类表
CREATE TABLE IF NOT EXISTS category (
        categoryId INT PRIMARY KEY,
        categoryName VARCHAR(50) NOT NULL,
description VARCHAR(200)
);

        -- 菜品表
CREATE TABLE IF NOT EXISTS dish (
        dishId INT PRIMARY KEY,
        dishName VARCHAR(50) NOT NULL,
price DOUBLE,
categoryId INT,
FOREIGN KEY (categoryId) REFERENCES category(categoryId)
        );

        -- 订单表（开台信息）
CREATE TABLE IF NOT EXISTS orders (
        orderId INT PRIMARY KEY,
        deskId INT,
        customerId INT,
        empId INT,
        startTime DATETIME,
        endTime DATETIME,
        totalAmount DOUBLE,
        FOREIGN KEY (deskId) REFERENCES desk(deskId),
FOREIGN KEY (customerId) REFERENCES customer(customerId),
FOREIGN KEY (empId) REFERENCES employee(empId)
        );

        -- 点菜表
CREATE TABLE IF NOT EXISTS order_item (
        orderItemId INT PRIMARY KEY,
        orderId INT,
        dishId INT,
        dishPrice DOUBLE,
        totalAmount DOUBLE,
        FOREIGN KEY (orderId) REFERENCES orders(orderId),
FOREIGN KEY (dishId) REFERENCES dish(dishId)
        );

INSERT INTO user (username, password) VALUES ('admin', 'admin123');