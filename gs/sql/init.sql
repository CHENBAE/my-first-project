-- 创建数据库
CREATE DATABASE IF NOT EXISTS admin_system CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE admin_system;

-- 用户表
CREATE TABLE IF NOT EXISTS sys_user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL COMMENT 'admin或user',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 部门表
CREATE TABLE IF NOT EXISTS sys_department (
    id INT PRIMARY KEY AUTO_INCREMENT,
    department_name VARCHAR(100) NOT NULL UNIQUE,
    leader_id INT COMMENT '负责人ID，关联sys_person表',
    sort INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 人员信息表
CREATE TABLE IF NOT EXISTS sys_person (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    gender VARCHAR(10) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    department_id INT COMMENT '部门ID，关联sys_department表',
    position VARCHAR(50),
    status VARCHAR(20) NOT NULL COMMENT '在职或离职',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 党支部表
CREATE TABLE IF NOT EXISTS sys_party_branch (
    id INT PRIMARY KEY AUTO_INCREMENT,
    branch_name VARCHAR(100) NOT NULL,
    secretary_id INT COMMENT '书记ID，关联sys_person表',
    member_count INT DEFAULT 0,
    remark VARCHAR(255),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 党员表
CREATE TABLE IF NOT EXISTS sys_party_member (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    branch_id INT COMMENT '支部ID，关联sys_party_branch表',
    join_date DATE NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 领导信息表
CREATE TABLE IF NOT EXISTS sys_leader (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    position VARCHAR(50) NOT NULL,
    responsibility VARCHAR(255),
    phone VARCHAR(20),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 会议表
CREATE TABLE IF NOT EXISTS sys_meeting (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    meeting_time DATETIME NOT NULL,
    location VARCHAR(200) NOT NULL,
    host_id INT COMMENT '主办人ID，关联sys_person表',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 年度时间节点表
CREATE TABLE IF NOT EXISTS sys_time_node (
    id INT PRIMARY KEY AUTO_INCREMENT,
   事项 VARCHAR(200) NOT NULL,
    month_num INT NOT NULL,
    day_num INT NOT NULL,
    type VARCHAR(20) NOT NULL COMMENT '法定节假日或工作节点',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 事务台账表
CREATE TABLE IF NOT EXISTS sys_affair (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    initiator_id INT COMMENT '发起人ID，关联sys_person表',
    responsible_id INT COMMENT '负责人ID，关联sys_person表',
    affair_time DATE NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 公告通知表
CREATE TABLE IF NOT EXISTS sys_announcement (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    publisher_id INT COMMENT '发布人ID，关联sys_person表',
    publish_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 初始化用户数据
DELETE FROM sys_user WHERE username IN ('admin', 'user');
INSERT INTO sys_user (username, password, role) VALUES
('admin', 'admin123', 'admin'),
('user', 'user123', 'user');

-- 初始化部门数据
INSERT INTO sys_department (department_name, sort) VALUES
('办公室', 1),
('人事处', 2),
('财务处', 3),
('业务处', 4);

-- 初始化人员数据
INSERT INTO sys_person (name, gender, phone, department_id, position, status) VALUES
('张三', '男', '13800138001', 1, '主任', '在职'),
('李四', '女', '13800138002', 2, '处长', '在职'),
('王五', '男', '13800138003', 3, '处长', '在职'),
('赵六', '女', '13800138004', 4, '处长', '在职');

-- 更新部门负责人
UPDATE sys_department SET leader_id = 1 WHERE department_name = '办公室';
UPDATE sys_department SET leader_id = 2 WHERE department_name = '人事处';
UPDATE sys_department SET leader_id = 3 WHERE department_name = '财务处';
UPDATE sys_department SET leader_id = 4 WHERE department_name = '业务处';

-- 初始化党支部数据
INSERT INTO sys_party_branch (branch_name, secretary_id, member_count, remark) VALUES
('机关党支部', 1, 10, '机关党员'),
('业务党支部', 4, 8, '业务部门党员');

-- 初始化党员数据
INSERT INTO sys_party_member (name, branch_id, join_date) VALUES
('张三', 1, '2000-01-01'),
('李四', 1, '2005-06-01'),
('王五', 1, '2010-07-01'),
('赵六', 2, '2015-08-01');

-- 初始化领导信息数据
INSERT INTO sys_leader (name, position, responsibility, phone) VALUES
('张三', '主任', '全面工作', '13800138001'),
('李四', '副主任', '人事工作', '13800138002'),
('王五', '副主任', '财务工作', '13800138003');

-- 初始化会议数据
INSERT INTO sys_meeting (title, meeting_time, location, host_id) VALUES
('周例会', '2024-01-01 09:00:00', '会议室1', 1),
('年度总结会', '2024-01-15 14:00:00', '大会议室', 1);

-- 初始化年度时间节点数据
INSERT INTO sys_time_node (事项, month_num, day_num, type) VALUES
('元旦', 1, 1, '法定节假日'),
('春节', 2, 10, '法定节假日'),
('工作部署会', 1, 5, '工作节点'),
('年度考核', 12, 20, '工作节点');

-- 初始化事务台账数据
INSERT INTO sys_affair (title, initiator_id, responsible_id, affair_time) VALUES
('项目申报', 4, 1, '2024-01-02'),
('设备采购', 3, 2, '2024-01-03');

-- 初始化公告通知数据
INSERT INTO sys_announcement (title, content, publisher_id) VALUES
('春节放假通知', '根据国家规定，春节放假时间为2024年2月10日至2月17日', 1),
('年度工作安排', '2024年工作重点包括...', 1);