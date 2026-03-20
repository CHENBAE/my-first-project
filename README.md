# 事业单位综合管理系统

## 项目介绍

事业单位综合管理系统是一个基于前后端分离架构的管理系统，用于管理事业单位的人员、部门、党支部、党员、领导、会议、时间节点、事务台账和公告通知等信息。

## 技术栈

### 后端
- Spring Boot 2.7.18
- MyBatis-Plus 3.5.3.1
- MySQL 8.0.30

### 前端
- Vue 3.5.30
- Element Plus 2.4.2
- Axios 1.5.0
- Vue Router 5.0.4
- Vite 8.0.1

## 功能模块

1. **人员信息管理**：人员的增删改查、分页和搜索
2. **部门管理**：部门的增删改查、分页和搜索
3. **党支部管理**：党支部的增删改查、分页和搜索
4. **党员管理**：党员的增删改查、分页和搜索
5. **领导信息管理**：领导的增删改查、分页和搜索
6. **会议管理**：会议的增删改查、分页和搜索
7. **时间节点管理**：时间节点的增删改查、分页和搜索
8. **事务台账管理**：事务台账的增删改查、分页和搜索
9. **公告通知管理**：公告通知的增删改查、分页和搜索

## 权限控制

- **管理员**：账号 `admin`，密码 `admin123`，拥有所有模块的增删改查权限
- **普通用户**：账号 `user`，密码 `user123`，只能查看数据，无法进行修改操作

## 部署运行

### 1. 环境准备

- **后端环境**：JDK 1.8 或 17，Maven 3.8+
- **前端环境**：Node.js 16+，npm 7+
- **数据库**：MySQL 8.0+

### 2. 数据库初始化

执行 `sql/init.sql` 脚本创建数据库和初始化数据（建议使用 MySQL 8.0 版本）：

```bash
mysql -u root -p < sql/init.sql
```

### 3. 后端启动

```bash
# 编译打包
mvn clean package

# 运行 jar 包
java -jar target/admin-system-1.0.0.jar

# 或者直接运行（开发环境）
mvn spring-boot:run

# 启动类位置：src/main/java/com/example/adminsystem/AdminSystemApplication.java
```

### 4. 前端启动

```bash
# 进入前端目录
cd front

# 安装依赖
npm install

# 启动开发服务器
npm run dev

# 构建生产版本
npm run build
```

## 项目结构

```
admin-system/
├── front/            # 前端项目
│   ├── src/          # 前端源代码
│   ├── public/       # 静态资源
│   └── dist/         # 构建产物
├── src/              # 后端源代码
│   ├── main/java/    # Java代码
│   └── main/resources/ # 配置文件
├── sql/              # 数据库脚本
│   └── init.sql      # 数据库初始化脚本
├── target/           # 后端构建产物
├── pom.xml           # Maven配置文件
└── README.md         # 项目说明文档
```

## 接口规范

- **风格**：RESTful风格，接口统一前缀 `/api`
- **返回格式**：`{code: 状态码, msg: 提示信息, data: 业务数据}`
- **状态码**：200（成功）、400（参数错误）、401（未登录/权限不足）、500（服务器错误）
- **数据交互**：前后端JSON格式，时间字段统一为 `yyyy-MM-dd HH:mm:ss`

## 注意事项

1. 确保MySQL服务正在运行
2. 配置数据库连接信息：
   - 修改 `src/main/resources/application.yml` 文件中的数据库连接信息
   - 配置示例：
     ```yaml
     spring:
       datasource:
         url: jdbc:mysql://localhost:3306/admin_system?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
         username: your_username
         password: your_password
     ```
3. 前端默认访问后端地址为 `http://localhost:8080/api`，如果后端服务地址或端口有变化，需要修改前端 `main.js` 中的 `axios.defaults.baseURL`
4. 本系统使用简单的登录认证，实际生产环境建议使用更安全的认证方式
5. 系统已经预置了一些测试数据，可以直接登录使用：
   - 管理员账号：admin，密码：admin123
   - 普通用户账号：user，密码：user123