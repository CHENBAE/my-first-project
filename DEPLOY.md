# 事业单位综合管理系统 - 部署运行说明

## 一、环境准备

### 1. 后端环境
- JDK 1.8
- Maven 3.8+
- MySQL 8.0.36

### 2. 前端环境
- Node.js 16+
- npm 7+

## 二、数据库初始化

1. 启动 MySQL 服务
2. 登录 MySQL 命令行或可视化工具（如 Navicat）
3. 执行 `init.sql` 脚本，创建数据库和初始化数据：
   ```bash
   mysql -u root -p < init.sql
   ```
   或在 MySQL 客户端中直接执行脚本内容

4. 验证数据库是否创建成功：
   - 数据库名：`admin_system`
   - 用户名：`root`
   - 密码：`123456`

## 三、后端启动

### 1. 编译打包
```bash
# 进入后端项目根目录
cd /Users/chenxi/Documents/毕设_管理系统/gs

# 编译打包
mvn clean package
```

### 2. 运行 jar 包
```bash
# 运行 jar 包
java -jar target/admin-system-1.0.0.jar
```

### 3. 验证后端服务
- 后端服务默认运行在 `http://localhost:8080`
- 可以通过访问 `http://localhost:8080/api/user/login` 验证服务是否启动成功

## 四、前端启动

### 1. 安装依赖
```bash
# 进入前端项目目录
cd /Users/chenxi/Documents/毕设_管理系统/gs/front

# 安装依赖
npm install
```

### 2. 启动开发服务器
```bash
# 启动开发服务器
npm run dev
```

### 3. 访问前端应用
- 前端应用默认运行在 `http://localhost:5173`
- 打开浏览器访问 `http://localhost:5173` 即可进入登录页面

## 五、登录测试

### 1. 管理员账号
- 用户名：`admin`
- 密码：`admin123`

### 2. 普通用户账号
- 用户名：`user`
- 密码：`user123`

## 六、功能测试

### 1. 权限测试
- 管理员登录后，可以进行所有模块的增删改查操作
- 普通用户登录后，只能查看数据，无法进行修改操作

### 2. 模块测试
测试每个模块的以下功能：
- 新增功能
- 编辑功能
- 删除功能
- 分页功能
- 搜索功能

## 七、技术栈说明

### 后端
- SpringBoot 2.7.18
- MyBatis-Plus 3.5.3.1
- MySQL 8.0.36

### 前端
- Vue 3.3.4
- Element Plus 2.4.2
- Axios 1.5.0

## 八、注意事项

1. 确保 MySQL 服务正在运行
2. 确保数据库连接信息正确（用户名：root，密码：123456）
3. 前端默认访问后端地址为 `http://localhost:8080/api`，如果后端服务地址或端口有变化，需要修改前端 `main.js` 中的 `axios.defaults.baseURL`
4. 本系统使用简单的 Token 认证，实际生产环境建议使用更安全的认证方式

## 九、项目结构

### 后端项目结构
```
gs/
├── src/
│   ├── main/
│   │   ├── java/com/example/adminsystem/
│   │   │   ├── controller/     # 控制器
│   │   │   ├── entity/          # 实体类
│   │   │   ├── mapper/          # Mapper接口
│   │   │   ├── service/         # 服务层
│   │   │   ├── common/          # 通用类
│   │   │   └── AdminSystemApplication.java  # 应用主类
│   │   └── resources/
│   │       └── application.yml  # 配置文件
├── pom.xml                      # Maven配置文件
└── init.sql                     # 数据库初始化脚本
```

### 前端项目结构
```
gs/front/
├── src/
│   ├── views/           # 页面组件
│   ├── router/          # 路由配置
│   ├── main.js          # 应用入口
│   └── App.vue          # 根组件
├── package.json         # 依赖配置
└── vite.config.js       # Vite配置
```