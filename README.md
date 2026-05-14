# JobPlus 综合性在线招聘平台

> **浙江师范大学 - 《软件工程基础》课程实践项目**
>
> JobPlus 是一个面向求职者、招聘企业和平台管理员的综合性在线招聘平台，覆盖 Web 网站端与微信小程序端，支持职位搜索、简历管理、投递追踪、企业认证、面试邀请、管理后台等完整招聘流程。

---

## 目录

1. [环境要求](#1-环境要求)
2. [快速启动（完整步骤）](#2-快速启动完整步骤)
3. [项目结构](#3-项目结构)
4. [功能模块与使用指南](#4-功能模块与使用指南)
5. [测试账号与场景](#5-测试账号与场景)
6. [API 接口文档](#6-api-接口文档)
7. [部署到生产环境](#7-部署到生产环境)
8. [常见问题](#8-常见问题)
9. [开发与排错经验](#9-开发与排错经验)

---

## 1. 环境要求

### 后端

| 软件 | 版本要求 | 作用 |
|------|----------|------|
| JDK | 1.8 或 11 | Java 运行环境 |
| Maven | 3.6+ | 项目构建与依赖管理 |
| MySQL | 8.0 | 关系型数据库 |
| Tomcat | 9.x | Web 应用服务器 |
| Git | 最新版 | 版本控制 |

### 前端

| 软件 | 版本要求 | 作用 |
|------|----------|------|
| Node.js | 14+ | JavaScript 运行环境 |
| npm | 6+ | 前端包管理 |

### 推荐工具

- **IntelliJ IDEA** — Java 开发（推荐 Ultimate 版，自带 Tomcat 集成）
- **Navicat / MySQL Workbench** — 数据库可视化
- **Postman / Apifox** — API 调试测试

---

## 2. 快速启动（完整步骤）

### 第 1 步：克隆项目

```bash
git clone https://github.com/zerro-223/JobPlus.git
cd JobPlus
```

### 第 2 步：创建数据库

```bash
# 方式一：交互式
mysql -u root -p
mysql> source sql/init.sql;
mysql> source sql/seed.sql;
mysql> exit;

# 方式二：一行执行
mysql -u root -p < sql/init.sql
mysql -u root -p < sql/seed.sql
```

> ⚠️ **数据库配置：** 编辑 `src/main/resources/db.properties`，将 `jdbc.username` 和 `jdbc.password` 改为你自己的 MySQL 账号密码。

### 第 3 步：启动后端（IDEA 开发模式，推荐）

1. 用 **IntelliJ IDEA** 打开 JobPlus 项目根目录
2. 配置 Tomcat Server：
   - `Run → Edit Configurations → + → Tomcat Server → Local`
   - **Server 标签页：** 配置 Tomcat 路径
   - **Deployment 标签页：** `+ → Artifact → JobPlus:war exploded`
   - **Application context：** 设为 `/JobPlus`
3. 点击右上角 Debug 🐞 按钮启动
4. 验证：浏览器访问 `http://localhost:8080/JobPlus/api/v1/positions?page=1&size=5`，应返回 JSON 数据

### 第 4 步：启动前端

```bash
# 注意：一定要先 cd 到 frontend 目录！
cd frontend

# 安装依赖（首次或 node_modules 被删除后需要）
npm install

# 启动开发服务器（默认端口 8081）
npm run serve

# 打开浏览器访问
# http://localhost:8081/
```

### 第 5 步：全栈联调验证

1. 确保 MySQL 运行中（3306 端口）
2. 确保 Tomcat 已启动（8080 端口）
3. 确保前端开发服务器已启动（8081 端口）
4. 浏览器访问 `http://localhost:8081/`
5. 用测试账号 `admin@jobplus.com` / `123456` 登录
6. 测试完整流程：浏览职位 → 投递 → 查看投递记录 → 管理后台

### ⚠️ 关于 IDEA 中运行终端命令

**如果你的项目路径包含中文（如 `C:\Users\39019\Desktop\软件工程实训\JobPlus`），在 IDEA 的内置终端中运行命令时，可能会遇到以下问题：**

- 命令行的字符被交错解析（从后往前）
- SQL 脚本中的注释变成乱码
- Maven 构建抛异常

**解决办法：**

1. **尽量在 Windows 的 cmd 或 PowerShell 中运行命令**，而不是 IDEA 内置终端
2. 如果必须在 IDEA 终端操作，在 `File → Settings → Tools → Terminal` 中将 `Shell path` 设为 `cmd.exe` 而不是 PowerShell，并确认字符编码为 UTF-8
3. 数据库初始化建议直接用 MySQL 命令行工具或 Navicat 执行 SQL 文件
4. Maven 构建可以右键 `pom.xml → Run Maven → clean package`，不依赖终端

---

## 3. 项目结构

```
JobPlus/
├── sql/                                # 数据库脚本
│   ├── init.sql                       # 建表脚本（14 张表）
│   └── seed.sql                       # 测试数据（7个用户 + 3家企业 + 7个职位 + 简历）
│
├── src/main/
│   ├── java/com/jobplus/
│   │   ├── entity/                    # 实体类（14 个，对应数据库表）
│   │   │   ├── User.java             # 用户表（含角色：求职者/企业/管理员）
│   │   │   ├── Position.java         # 职位信息表
│   │   │   ├── Company.java          # 企业信息表
│   │   │   ├── Resume.java           # 简历主表
│   │   │   ├── Delivery.java         # 投递记录表
│   │   │   ├── Favorite.java         # 收藏记录表
│   │   │   ├── Interview.java        # 面试邀请表
│   │   │   ├── Notification.java     # 通知消息表
│   │   │   └── ...                   # 其他实体
│   │   ├── mapper/                   # MyBatis Mapper 接口（@Select/@Insert 注解）
│   │   ├── service/                  # 业务接口
│   │   │   └── impl/                 # 业务实现类
│   │   ├── controller/               # RESTful API 控制器（9 个）
│   │   │   ├── UserController.java        # 注册/登录/用户信息
│   │   │   ├── PositionController.java    # 职位搜索/详情/发布
│   │   │   ├── CompanyController.java     # 企业信息/认证/收到的简历
│   │   │   ├── ResumeController.java      # 简历编辑/附件上传
│   │   │   ├── DeliveryController.java    # 投递/状态追踪
│   │   │   ├── FavoriteController.java    # 收藏/取消收藏
│   │   │   ├── InterviewController.java   # 面试邀请
│   │   │   ├── NotificationController.java# 站内通知
│   │   │   └── AdminController.java       # 管理员审核/统计
│   │   └── common/                   # 通用模块
│   │       ├── dto/                  # DTO/VO 数据传输对象
│   │       ├── exception/            # 全局异常处理
│   │       ├── interceptor/          # 角色权限拦截器（@RequireRole）
│   │       ├── annotation/           # 自定义注解
│   │       ├── config/               # 参数解析器配置
│   │       └── utils/                # 工具类（BCrypt 加密、Session 工具）
│   │
│   └── resources/
│       ├── spring/
│       │   ├── applicationContext.xml # Spring 核心配置
│       │   └── spring-mvc.xml        # Spring MVC 配置
│       ├── mapper/                   # MyBatis 动态 SQL XML
│       │   ├── PositionMapper.xml    # 职位搜索动态 SQL
│       │   └── DeliveryMapper.xml    # 投递记录 JOIN 查询
│       ├── mybatis-config.xml        # MyBatis 全局配置
│       ├── db.properties             # 数据库连接配置
│       └── logback.xml               # 日志配置
│
├── frontend/                          # Vue.js 前端项目
│   ├── src/
│   │   ├── main.js                   # 入口文件
│   │   ├── App.vue                   # 根组件
│   │   ├── router/index.js           # 路由定义（13 个路由）
│   │   ├── utils/api.js              # Axios 封装
│   │   └── views/                    # 页面组件（13 个）
│   ├── vue.config.js                 # 开发代理配置 + Cookie 路径重写
│   └── package.json                  # 前端依赖
│
├── pom.xml                            # Maven 项目配置
└── README.md                          # 本文件
```

---

## 4. 功能模块与使用指南

### 4.1 前端页面一览

| 页面 | 路由 | 访问权限 | 功能描述 |
|------|------|----------|----------|
| 首页 | `/` | 所有人 | 搜索栏 + 热门分类 + 推荐职位 + 知名企业展示 |
| 职位列表 | `/jobs` | 所有人 | 多条件筛选（关键词/城市/薪资/学历/分类）+ 分页 |
| 职位详情 | `/jobs/:id` | 所有人 | 职位描述/要求 + 公司信息 + 投递 + 收藏 |
| 登录 | `/login` | 未登录 | 邮箱密码登录 |
| 注册 | `/register` | 未登录 | 邮箱+密码+角色选择注册 |
| 个人中心 | `/profile` | 已登录 | 修改昵称/手机号 + 修改密码 |
| 简历管理 | `/resume` | 求职者 | 基本信息 + 教育/工作/项目分段编辑 + 附件上传 |
| 投递记录 | `/deliveries` | 求职者 | 投递历史列表 + 状态追踪 |
| 收藏夹 | `/favorites` | 求职者 | 收藏的职位列表 + 取消收藏 |
| 消息通知 | `/notifications` | 已登录 | 通知列表 + 标为已读 + 全部已读 |
| 公司主页 | `/company/:id` | 所有人 | 公司信息 + 在招职位列表 |
| 企业后台 | `/employer` | 企业用户 | 企业信息/认证/职位管理/收到的简历/面试 |
| 管理后台 | `/admin` | 管理员 | 数据统计/企业审核/职位审核/用户管理 |

### 4.2 各角色操作流程

#### 🟢 求职者

```
注册（选择"求职者"角色）→ 登录 → 完善简历 → 浏览职位 → 投递 → 查看投递状态
```

1. **注册账号** — 点击导航栏「注册」，填写邮箱、密码，选择「求职者」
2. **完善简历** — 登录后进入「我的简历」，填写基本信息、教育/工作/项目经历、技能标签
3. **搜索职位** — 在首页搜索框输入关键词（如"Java"），或在职位列表页使用多条件筛选
4. **投递职位** — 点击职位进入详情页，点击「立即投递」
5. **查看进度** — 在「投递记录」查看状态变化：已投递 → 被查看 → 邀面试/不合适
6. **收藏职位** — 在职位详情页点击「收藏」，在「收藏夹」统一管理

#### 🔵 企业招聘方

```
注册（选择"企业"角色）→ 登录 → 填写企业信息 → 提交认证 → 等待管理员审核
→ 审核通过后发布职位 → 查看收到的简历 → 邀面试
```

1. **注册企业账号** — 选择「企业招聘方」
2. **完善企业信息** — 进入「企业后台」→「企业信息」，填写企业全称、行业、规模、简介等
3. **提交认证** — 切换到「企业认证」页面，提交营业执照信息
4. **等待管理员审核** — 审核通过后企业状态变为「已认证」
5. **发布职位** — 在「职位管理」点击「发布新职位」，提交后状态为「待审核」
6. **等待管理员审核职位** — 审核通过后职位变为「招聘中」
7. **查看投递** — 在「收到的简历」查看候选人的投递记录
8. **邀面试** — 点击「邀面试」发送面试邀请

#### 🔴 管理员

```
登录预置管理员账号 → 审核企业认证 → 审核职位 → 管理用户 → 查看数据统计
```

1. **登录** — 使用管理员账号 `admin@jobplus.com` / `123456` 登录
2. **企业审核** — 进入「管理后台」→「企业审核」，通过或驳回待审核企业
3. **职位审核** — 切换到「职位审核」，审核待发布职位
4. **用户管理** — 禁用违规用户
5. **数据统计** — 查看注册用户数、企业数、职位数、投递数等关键指标

---

## 5. 测试账号与场景

### 预置账号

| 角色 | 邮箱 | 密码 | 说明 |
|------|------|------|------|
| 👑 管理员 | admin@jobplus.com | 123456 | 审核企业、审核职位、管理用户、查看统计 |
| 👤 求职者-张三 | candidate1@test.com | 123456 | 已填写完整简历（浙大+网易+蚂蚁） |
| 👤 求职者-李四 | candidate2@test.com | 123456 | 已填写简历（杭电+有赞+拼多多） |
| 👤 求职者-王五 | candidate3@test.com | 123456 | 已填写简历（北大硕士+NLP） |
| 🏢 企业-阿里云 | employer1@test.com | 123456 | 已认证，可发布职位 |
| 🏢 企业-华为 | employer2@test.com | 123456 | 已认证，可发布职位 |
| 🏢 企业-字节跳动 | employer3@test.com | 123456 | 已认证，可发布职位 |

### 预置职位数据（7 个）

| 职位 | 企业 | 薪资(K) | 地点 | 状态 |
|------|------|---------|------|------|
| Java 高级开发工程师 | 阿里云 | 25-45 | 杭州 | ✅ 招聘中 |
| 前端开发工程师 | 阿里云 | 15-30 | 杭州 | ✅ 招聘中 |
| Java 开发工程师 | 华为 | 18-35 | 深圳 | ✅ 招聘中 |
| 大数据开发工程师 | 华为 | 20-40 | 深圳 | ✅ 招聘中 |
| 后端开发工程师-抖音 | 字节跳动 | 30-60 | 北京 | ✅ 招聘中 |
| 前端开发工程师-飞书 | 字节跳动 | 25-50 | 北京 | ✅ 招聘中 |
| 算法工程师-推荐系统 | 字节跳动 | 35-70 | 北京 | ✅ 招聘中 |

### 端到端测试场景

#### 场景一：求职者投递流程
1. 用 `candidate1@test.com` / `123456` 登录
2. 进入「我的简历」确认简历信息完整
3. 首页搜索「Java」→ 找到阿里云「Java 高级开发工程师」
4. 点击「立即投递」→ 确认成功
5. 进入「投递记录」查看状态
6. 收藏一个职位 → 进入「收藏夹」验证

#### 场景二：企业发布职位
1. 用 `employer1@test.com` / `123456` 登录
2. 进入「企业后台」→「职位管理」→「发布新职位」
3. 填写后提交 → 状态「待审核」
4. 切换管理员账号审核通过后，回到企业账号验证状态

#### 场景三：管理员审核
1. 用 `admin@jobplus.com` / `123456` 登录
2. 进入「管理后台」→「职位审核」→ 找到待审核职位 → 点击「通过」
3. 验证该职位状态变为「招聘中」

---

## 6. API 接口文档

> **基础路径：** `/api/v1`
> **统一响应格式：** `{ "code": 200, "message": "success", "data": { ... } }`
> **认证方式：** Session（登录后自动维护，无需传 Token）

### 6.1 用户模块

| 方法 | 路径 | 说明 | 权限 |
|------|------|------|------|
| POST | `/api/v1/user/register` | 注册新用户 | 未登录 |
| POST | `/api/v1/user/login` | 用户登录（邮箱+密码） | 未登录 |
| GET | `/api/v1/user/profile` | 获取当前用户信息 | 已登录 |
| PUT | `/api/v1/user/profile` | 更新个人信息 | 已登录 |
| PUT | `/api/v1/user/password?oldPwd=&newPwd=` | 修改密码 | 已登录 |
| POST | `/api/v1/user/logout` | 退出登录 | 已登录 |

**注册请求体示例：**
```json
{
  "email": "newuser@test.com",
  "phone": "13800138000",
  "password": "123456",
  "role": 1,
  "nickname": "新用户"
}
```

### 6.2 职位模块

| 方法 | 路径 | 说明 | 权限 |
|------|------|------|------|
| GET | `/api/v1/positions?page=&size=&keyword=&city=&salaryMin=&education=&categoryId=` | 职位搜索（分页+筛选） | 所有人 |
| GET | `/api/v1/positions/{id}` | 职位详情 | 所有人 |
| POST | `/api/v1/positions` | 发布职位 | 企业 |
| PUT | `/api/v1/positions/{id}` | 编辑职位 | 企业 |
| PUT | `/api/v1/positions/{id}/status?status=2` | 下线职位 | 企业 |
| GET | `/api/v1/positions/categories` | 获取职位分类树 | 所有人 |

### 6.3 企业模块

| 方法 | 路径 | 说明 | 权限 |
|------|------|------|------|
| GET | `/api/v1/company/profile` | 获取企业信息 | 企业 |
| PUT | `/api/v1/company/profile` | 编辑企业信息 | 企业 |
| POST | `/api/v1/company/certify` | 提交认证资料 | 企业 |
| GET | `/api/v1/company/{id}` | 企业公开信息 | 所有人 |
| GET | `/api/v1/company/{id}/positions` | 企业在招职位 | 所有人 |
| GET | `/api/v1/company/deliveries` | 收到的简历列表 | 企业 |

### 6.4 简历模块

| 方法 | 路径 | 说明 | 权限 |
|------|------|------|------|
| GET | `/api/v1/resume` | 获取我的简历（含教育/工作/项目经历） | 已登录 |
| PUT | `/api/v1/resume` | 保存简历基本信息 | 已登录 |
| POST | `/api/v1/resume/attachment` | 上传附件简历 | 已登录 |
| PUT | `/api/v1/resume/education` | 保存教育经历列表 | 已登录 |
| PUT | `/api/v1/resume/work` | 保存工作经历列表 | 已登录 |
| PUT | `/api/v1/resume/project` | 保存项目经验列表 | 已登录 |

### 6.5 投递模块

| 方法 | 路径 | 说明 | 权限 |
|------|------|------|------|
| POST | `/api/v1/deliveries?positionId=&resumeId=` | 投递职位 | 求职者 |
| GET | `/api/v1/deliveries` | 我的投递记录 | 求职者 |
| PUT | `/api/v1/deliveries/{id}/status?status=` | 更新投递状态（企业端） | 企业 |

**投递状态：** `0=已投递` `1=被查看` `2=邀面试` `3=不合适` `4=已取消`

### 6.6 收藏/面试/通知/管理后台

| 方法 | 路径 | 说明 | 权限 |
|------|------|------|------|
| POST | `/api/v1/favorites?positionId=` | 收藏职位 | 已登录 |
| DELETE | `/api/v1/favorites/{positionId}` | 取消收藏 | 已登录 |
| GET | `/api/v1/favorites` | 收藏列表 | 已登录 |
| POST | `/api/v1/interviews` | 发送面试邀请 | 企业 |
| GET | `/api/v1/interviews` | 面试列表 | 已登录 |
| PUT | `/api/v1/interviews/{id}/status` | 接受/拒绝面试 | 求职者 |
| GET | `/api/v1/notifications` | 通知列表 | 已登录 |
| PUT | `/api/v1/notifications/{id}/read` | 标记已读 | 已登录 |
| PUT | `/api/v1/notifications/read-all` | 全部已读 | 已登录 |
| GET | `/api/v1/admin/stats` | 数据统计 | 管理员 |
| GET | `/api/v1/admin/companies/pending` | 待审核企业 | 管理员 |
| PUT | `/api/v1/admin/companies/{id}/audit` | 审核企业 | 管理员 |
| GET | `/api/v1/admin/positions/pending` | 待审核职位 | 管理员 |
| PUT | `/api/v1/admin/positions/{id}/audit` | 审核职位 | 管理员 |
| GET | `/api/v1/admin/users` | 用户列表 | 管理员 |
| PUT | `/api/v1/admin/users/{id}/status` | 禁用/启用用户 | 管理员 |

### 6.7 Postman 测试请求

```
// 登录
POST http://localhost:8080/JobPlus/api/v1/user/login
Content-Type: application/json
{ "email": "candidate1@test.com", "password": "123456" }

// 搜索职位
GET http://localhost:8080/JobPlus/api/v1/positions?keyword=Java&page=1&size=10

// 投递职位
POST http://localhost:8080/JobPlus/api/v1/deliveries?positionId=1&resumeId=1
```

---

## 7. 部署到生产环境

### 方案一：Tomcat 单机部署（推荐课程演示）

```bash
# 1. Maven 构建
mvn clean package -DskipTests

# 2. 前端构建（产物会复制到 src/main/webapp/）
cd frontend && npm install && npm run build

# 3. 部署到 Tomcat
cp target/JobPlus.war /path/to/tomcat/webapps/

# 4. 启动 Tomcat
cd /path/to/tomcat/bin && ./startup.sh

# 5. 访问 http://<服务器IP>:8080/JobPlus/
```

### 方案二：云服务器部署（可选）

```bash
# 推荐配置：阿里云/腾讯云 2C4G 轻量应用服务器
# 安装 JDK 11 + Tomcat 9 + MySQL 8.0
# 修改 db.properties 中的数据库地址为云数据库地址
# 安全组开放 8080 端口
```

---

## 8. 常见问题

### Q: 为什么访问 localhost:8081 后，API 调用一直 401？
**根本原因：** Tomcat 返回的 `JSESSIONID` cookie 路径为 `/JobPlus`，但前端在 `localhost:8081`，浏览器因路径不匹配丢弃了 cookie。

**解决方案（已内置于前端）：** `vue.config.js` 中设置了 `cookiePathRewrite: {'/JobPlus': '/'}`，将 cookie 路径重写为根路径。如果仍遇此问题，请：
1. 在浏览器按 F12 → Application → Storage → Cookies → 清空
2. 重新登录

### Q: 启动 Tomcat 后访问报 404？
- 确认 WAR 包已正确部署到 `webapps/` 目录
- 检查 Tomcat 启动日志：`tail -f logs/catalina.out`
- 确认访问路径：`http://localhost:8080/JobPlus/xxx`

### Q: 前端页面调用 API 返回空数据？
- 确认 MySQL 运行中，`init.sql` 和 `seed.sql` 已执行
- 确认 `db.properties` 中的数据库密码正确
- 查看 Tomcat 日志排查后端错误

### Q: IDEA 内置终端中命令出现乱码或从后往前执行？
**原因：** 项目路径包含中文字符（如 `软件工程实训`），IDEA 终端编码处理有问题。

**解决：**
1. 在 `File → Settings → Tools → Terminal` 中，将 `Shell path` 设为 `cmd.exe`
2. 或在 Windows 的 cmd/PowerShell 中手动运行命令（推荐）
3. IDEA 中右键 `pom.xml` → Run Maven → clean/package，不依赖终端
4. 数据库操作直接在 Navicat/MySQL Workbench 中执行 SQL 文件

### Q: 企业无法发布职位？
- 确认为「企业」角色登录
- 确认企业信息已填写
- 确认企业认证已通过审核

### Q: 前端 npm install 报错？
- Node.js 版本 >= 14
- 删除 `node_modules` 后重新安装：`npm install`

### Q: 如何重置数据库？
```bash
mysql -u root -p < sql/init.sql
mysql -u root -p < sql/seed.sql
```

### Q: npm run serve 后遇到 "Missing script: serve" 或者空转？
原因可能是你在 **项目根目录** 而不是 `frontend/` 子目录执行了命令。

**修复：**
```bash
# 先进入前端目录（重要！）
cd C:\Users\39019\Desktop\软件工程实训\JobPlus\frontend
npm install
npm run serve
```

---

## 9. 开发与排错经验

### 数据库相关
- `position` 是 MySQL 保留关键字，建表和查询时必须用反引号 `` `position` `` 转义
- `characterEncoding` 在 JDBC URL 中必须用 `UTF-8`（而不是 `utf8mb4`），因为 JDBC 驱动需要 Java 字符集名称
- 建表前先 `DROP DATABASE IF EXISTS` 可以避免外键约束残留导致建表失败
- `work_experience` 表也有 `position` 列名（表示"职位名称"），同样需反引号转义

### 权限拦截器
- `AuthInterceptor` 只拦截标注了 `@RequireRole` 注解的 Controller 方法
- `@RequireRole(0)` = 任何已登录用户可访问
- `@RequireRole(1)` = 仅求职者
- `@RequireRole(2)` = 仅企业
- `@RequireRole(3)` = 仅管理员

### 前端 Session 保持
- 使用 `Vue.observable({user})` 做响应式全局状态（`$global`）
- 模板中通过 `$global.user` 读取用户信息，登录态变化会自动更新所有组件
- Axios `withCredentials: true` 允许跨域携带 Cookie
- 开发代理中 `cookiePathRewrite` 解决路径不匹配问题

### Maven 编译
- DispatcherServlet 初始化日志为 INFO 级别，但一旦出现 ERROR 说明配置有问题
- 常见的编译问题：Maven 依赖下载缓慢可以换阿里云镜像仓库
- 如果 MyBatis 报"Failed to parse mapping resource"，说明 Mapper XML 和注解有重复定义的同名 SQL

---

## 技术栈

| 层次 | 技术 | 版本 |
|------|------|------|
| 后端框架 | Spring + Spring MVC + MyBatis (SSM) | Spring 5.3.31 / MyBatis 3.5.15 |
| 数据库 | MySQL | 8.0 |
| 连接池 | Druid | 1.2.20 |
| 密码加密 | Spring Security Crypto (BCrypt) | 5.8.8 |
| 前端 | Vue.js 2 + Element UI | Vue 2.7.14 |
| 构建 | Maven | 3.6+ |
| 部署 | Apache Tomcat | 9.x |
| 版本控制 | Git + GitHub | — |

---

> 项目地址：https://github.com/zerro-223/JobPlus
>
> 浙江师范大学 - 《软件工程基础》课程实践项目
