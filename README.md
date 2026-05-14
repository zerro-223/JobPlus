# JobPlus 综合性在线招聘平台

> **浙江师范大学 - 《软件工程基础》课程实践项目**
>
> JobPlus 是一个面向求职者、招聘企业和平台管理员的综合性在线招聘平台，覆盖 Web 网站端，支持职位搜索、简历管理、投递追踪、企业认证、面试邀请、管理后台等完整招聘流程。

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

- **IntelliJ IDEA / Eclipse** — Java 开发
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
# 登录 MySQL（需 root 权限）
mysql -u root -p

# 执行建表脚本（14 张表）
mysql> source sql/init.sql;

# 插入测试数据（3 个求职者、3 家企业、7 个职位、测试简历等）
mysql> source sql/seed.sql;

# 退出
mysql> exit;
```

**也可用一行执行：**

```bash
mysql -u root -p < sql/init.sql
mysql -u root -p < sql/seed.sql
```

> **初始数据库配置：** 用户名 `root`，密码 `root`，数据库名 `jobplus_db`。
> 如需修改，编辑 `src/main/resources/db.properties`。

### 第 3 步：启动后端

```bash
# 方式一：Maven 打包后部署到 Tomcat（推荐用于生产）

# 1. 构建 WAR 包
mvn clean package

# 2. 将 WAR 包复制到 Tomcat webapps 目录
cp target/JobPlus.war /path/to/tomcat/webapps/

# 3. 启动 Tomcat
cd /path/to/tomcat/bin
./startup.sh        # Linux/Mac
startup.bat         # Windows

# 4. 验证后端是否启动成功
# 访问: http://localhost:8080/JobPlus/
# 终端日志: tail -f /path/to/tomcat/logs/catalina.out
```

```bash
# 方式二：使用 IDEA 开发模式（推荐用于开发）

# 1. 用 IntelliJ IDEA 打开 JobPlus 项目
# 2. 配置 Tomcat Server：
#    Run → Edit Configurations → + → Tomcat Server → Local
#    Deployment: 选择 JobPlus:war exploded
#    Application context: /JobPlus
# 3. 点击 Debug 按钮启动
# 4. 访问 http://localhost:8080/JobPlus/
```

> **验证后端：** 浏览器访问 `http://localhost:8080/JobPlus/api/v1/positions?page=1&size=5`，应返回 JSON 格式的职位列表数据。

### 第 4 步：启动前端

```bash
# 1. 进入前端目录
cd frontend

# 2. 安装依赖
npm install

# 3. 启动开发服务器（默认端口 8081）
npm run serve

# 4. 打开浏览器访问
# http://localhost:8081/
```

> **开发模式下的代理配置：** 前端开发服务器（:8081）会自动将 `/api` 请求代理到后端 Tomcat（:8080），无需额外配置，详见 `frontend/vue.config.js`。

### 第 5 步：验证全栈联调

1. 确保 MySQL 正在运行（3306 端口）
2. 确保 Tomcat 已启动（8080 端口）
3. 确保前端开发服务器已启动（8081 端口）
4. 打开浏览器访问 `http://localhost:8081/`
5. 使用测试账号登录（见下方第 5 节）
6. 浏览职位 → 投递 → 查看投递记录，验证完整流程

---

## 3. 项目结构

```
JobPlus/
├── sql/                                # 数据库脚本
│   ├── init.sql                       # 建表脚本（14 张表：user, company, position,
│   │                                   #   resume, delivery, favorite, interview,
│   │                                   #   notification, job_category, ...）
│   └── seed.sql                       # 测试数据（3个求职者 + 3家企业 + 7个职位 + 简历）
│
├── src/main/
│   ├── java/com/jobplus/
│   │   ├── entity/                    # 实体类（14 个，对应数据库表）
│   │   ├── mapper/                    # MyBatis Mapper 接口（含 @Select/@Insert 注解）
│   │   ├── service/                   # 业务接口
│   │   │   └── impl/                  # 业务实现类
│   │   ├── controller/                # RESTful API 控制器（9 个）
│   │   │   ├── UserController.java         # 注册/登录/用户信息
│   │   │   ├── PositionController.java     # 职位搜索/详情/发布
│   │   │   ├── CompanyController.java      # 企业信息/认证/收到的简历
│   │   │   ├── ResumeController.java       # 简历编辑/附件上传
│   │   │   ├── DeliveryController.java     # 投递职位/状态追踪
│   │   │   ├── FavoriteController.java     # 收藏/取消收藏
│   │   │   ├── InterviewController.java    # 面试邀请
│   │   │   ├── NotificationController.java # 站内通知
│   │   │   └── AdminController.java        # 管理员审核/统计
│   │   └── common/                     # 通用模块
│   │       ├── dto/                    # DTO/VO 数据传输对象
│   │       ├── exception/             # 全局异常处理
│   │       ├── interceptor/           # 登录拦截器
│   │       ├── annotation/            # 自定义注解（@RequireRole, @AuthUser）
│   │       └── utils/                 # 工具类（BCrypt 加密、Session 工具）
│   │
│   └── resources/
│       ├── spring/
│       │   ├── applicationContext.xml  # Spring 核心配置（数据源、事务、Mapper扫描）
│       │   └── spring-mvc.xml         # Spring MVC 配置（拦截器、参数解析器、JSON）
│       ├── mapper/                    # MyBatis 动态 SQL XML
│       │   ├── PositionMapper.xml     # 职位搜索动态 SQL（多条件筛选+分页）
│       │   └── DeliveryMapper.xml     # 投递记录关联查询
│       ├── mybatis-config.xml         # MyBatis 全局配置（驼峰映射、日志）
│       ├── db.properties              # 数据库连接配置
│       └── logback.xml                # 日志配置
│
├── frontend/                           # Vue.js 前端项目
│   ├── src/
│   │   ├── main.js                    # 入口（Element UI + Axios + Router）
│   │   ├── App.vue                    # 根组件
│   │   ├── router/index.js            # 路由定义（15 个路由）
│   │   ├── utils/api.js               # Axios 封装（拦截器、Session）
│   │   └── views/                     # 页面组件（详见下方 4.1 节）
│   ├── vue.config.js                  # 开发代理配置
│   └── package.json                   # 前端依赖
│
├── pom.xml                             # Maven 项目配置
└── README.md                           # 本文件
```

---

## 4. 功能模块与使用指南

### 4.1 前端页面一览

| 页面 | 路由 | 访问权限 | 功能描述 |
|------|------|----------|----------|
| 首页 | `/` | 所有人 | 搜索栏 + 热门分类 + 推荐职位 + 知名企业展示 |
| 职位列表 | `/jobs` | 所有人 | 多条件筛选（关键词/城市/薪资/学历/分类）+ 分页 |
| 职位详情 | `/jobs/:id` | 所有人 | 职位描述/要求 + 公司信息 + 投递按钮 + 收藏 |
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

**详细步骤：**

1. **注册账号** — 点击导航栏「注册」，填写邮箱、密码，选择角色为「求职者」
2. **完善简历** — 登录后进入「我的简历」，填写基本信息、教育经历、工作经历、项目经验、技能标签
3. **搜索职位** — 在首页搜索框输入关键词（如"Java"），或在职位列表页使用多条件筛选
4. **投递职位** — 点击职位进入详情页，点击「立即投递」按钮
5. **查看进度** — 在「投递记录」页面查看状态变化：已投递 → 被查看 → 邀面试/不合适
6. **收藏职位** — 在职位详情页点击「收藏」按钮，在「收藏夹」统一管理

#### 🔵 企业招聘方

```
注册（选择"企业"角色）→ 登录 → 填写企业信息 → 提交认证 → 等待管理员审核
→ 审核通过后发布职位 → 查看收到的简历 → 邀面试
```

**详细步骤：**

1. **注册企业账号** — 选择角色为「企业招聘方」
2. **完善企业信息** — 进入「企业后台」→「企业信息」，填写企业全称、行业、规模、简介等
3. **提交认证** — 切换到「企业认证」页面，填写营业执照信息并提交
4. **等待审核** — 管理员审核通过后，企业状态变为「已认证」
5. **发布职位** — 在「职位管理」点击「发布新职位」，填写职位信息并提交
6. **等待审核** — 管理员审核职位通过后，职位状态变为「招聘中」
7. **查看投递** — 在「收到的简历」查看候选人的投递记录
8. **邀面试** — 点击「邀面试」发送面试邀请，填写面试时间地点

#### 🔴 管理员

```
登录预置管理员账号 → 审核企业认证 → 审核职位 → 管理用户 → 查看数据统计
```

**详细步骤：**

1. **登录** — 使用管理员账号 `admin@jobplus.com` / `123456` 登录
2. **企业审核** — 进入「管理后台」→「企业审核」，查看待审核企业，点击通过或驳回
3. **职位审核** — 切换到「职位审核」，审核待发布职位
4. **用户管理** — 在「用户管理」页面禁用违规用户
5. **数据统计** — 查看注册用户数、企业数、职位数、投递数等关键指标

### 4.3 核心业务流程图

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│   求职者     │     │    企业      │     │   管理员     │
├─────────────┤     ├─────────────┤     ├─────────────┤
│ 注册/登录    │     │ 注册/登录    │     │ 登录        │
│ 填写简历     │     │ 填写企业信息  │     │             │
│ 搜索职位     │     │ 提交认证资料  │────▶│ 审核企业认证  │
│ 投递职位     │     │ 审核通过后   │     │ 审核职位     │
│ 查看状态     │◀────│ 发布职位     │     │ 用户管理     │
│ 收藏职位     │     │ 查看收到的简历│     │ 数据统计     │
│             │     │ 发送面试邀请  │     │             │
└─────────────┘     └─────────────┘     └─────────────┘
```

---

## 5. 测试账号与场景

### 预置账号

| 角色 | 邮箱 | 密码 | 说明 |
|------|------|------|------|
| 👑 管理员 | admin@jobplus.com | 123456 | 审核企业、审核职位、管理用户、查看统计 |
| 👤 求职者-张三 | candidate1@test.com | 123456 | 已填写完整简历（浙大本科+网易+蚂蚁经验） |
| 👤 求职者-李四 | candidate2@test.com | 123456 | 已填写简历（杭电+有赞+拼多多前端经验） |
| 👤 求职者-王五 | candidate3@test.com | 123456 | 已填写简历（北大硕士+NLP研究方向） |
| 🏢 企业-阿里云 | employer1@test.com | 123456 | 已认证（可发布职位） |
| 🏢 企业-华为 | employer2@test.com | 123456 | 已认证（可发布职位） |
| 🏢 企业-字节跳动 | employer3@test.com | 123456 | 已认证（可发布职位） |

### 预置职位数据

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

#### 场景一：求职者完整求职流程
1. 用 `candidate1@test.com` / `123456` 登录
2. 进入「我的简历」确认简历信息完整
3. 首页搜索「Java」→ 找到阿里云「Java 高级开发工程师」
4. 点击进入详情页 → 点击「立即投递」→ 确认投递成功
5. 进入「投递记录」查看状态（显示"已投递"）
6. 收藏一个感兴趣的职位 → 进入「收藏夹」验证

#### 场景二：企业发布职位并查看投递
1. 用 `employer1@test.com` / `123456` 登录
2. 进入「企业后台」→「企业信息」查看企业资料
3. 进入「职位管理」→ 点击「发布新职位」
4. 填写：标题"测试工程师"、薪资 15-25K、地点"杭州"等
5. 提交后 → 职位状态为「待审核」
6. （切换到管理员账号审核通过后，再切回企业账号）
7. 进入「收到的简历」查看投递该企业职位的候选人列表

#### 场景三：管理员审核流程
1. 用 `admin@jobplus.com` / `123456` 登录
2. 进入「管理后台」→「数据统计」查看概览数据
3. 进入「企业审核」→ 查看是否有待审核企业
4. 进入「职位审核」→ 找到刚才企业发布的待审核职位 → 点击「通过」
5. 验证：该职位状态变为「招聘中」，企业对应用户收到通知

---

## 6. API 接口文档

> **基础路径：** `/api/v1`
> **统一响应格式：** `{ "code": 200, "message": "success", "data": { ... } }`
> **认证方式：** Session（登录后自动维护，无需手动传 Token）

### 6.1 用户模块

| 方法 | 路径 | 说明 | 权限 |
|------|------|------|------|
| POST | `/api/v1/user/register` | 注册新用户 | 未登录 |
| POST | `/api/v1/user/login` | 用户登录（邮箱+密码） | 未登录 |
| GET | `/api/v1/user/profile` | 获取当前用户信息 | 已登录 |
| PUT | `/api/v1/user/profile` | 更新个人信息（昵称、手机号） | 已登录 |
| PUT | `/api/v1/user/password?oldPwd=&newPwd=` | 修改密码 | 已登录 |
| POST | `/api/v1/user/logout` | 退出登录 | 已登录 |

#### POST /api/v1/user/register 示例

**请求体：**
```json
{
  "email": "newuser@test.com",
  "phone": "13800138000",
  "password": "123456",
  "role": 1,
  "nickname": "新用户"
}
```
**返回值：**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 8,
    "email": "newuser@test.com",
    "phone": "13800138000",
    "role": 1,
    "nickname": "新用户",
    "status": 1
  }
}
```

### 6.2 职位模块

| 方法 | 路径 | 说明 | 权限 |
|------|------|------|------|
| GET | `/api/v1/positions?page=1&size=20&keyword=&city=&salaryMin=&education=&categoryId=` | 职位搜索（分页+筛选） | 所有人 |
| GET | `/api/v1/positions/{id}` | 职位详情（自动增加浏览量） | 所有人 |
| POST | `/api/v1/positions` | 发布职位 | 企业 |
| PUT | `/api/v1/positions/{id}` | 编辑职位 | 企业（仅本人） |
| PUT | `/api/v1/positions/{id}/status?status=2` | 下线职位（status=2） | 企业（仅本人） |
| GET | `/api/v1/positions/categories` | 获取职位分类树 | 所有人 |

#### GET /api/v1/positions?keyword=Java&city=杭州&page=1&size=5 返回值示例

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "list": [
      {
        "id": 1,
        "title": "Java 高级开发工程师",
        "companyName": "阿里云计算有限公司",
        "companyLogo": null,
        "salaryMin": 25.00,
        "salaryMax": 45.00,
        "education": "本科",
        "experience": "5-10年",
        "workplace": "杭州",
        "tags": "五险一金,双休,年终奖,股票期权",
        "status": 1,
        "viewCount": 0,
        "deliveryCount": 0,
        "createdAt": "2026-05-14 18:42:00"
      }
    ],
    "page": 1,
    "size": 5,
    "total": 1,
    "totalPages": 1
  }
}
```

### 6.3 企业模块

| 方法 | 路径 | 说明 | 权限 |
|------|------|------|------|
| GET | `/api/v1/company/profile` | 获取企业信息 | 企业 |
| PUT | `/api/v1/company/profile` | 编辑企业信息 | 企业 |
| POST | `/api/v1/company/certify` | 提交认证资料 | 企业 |
| GET | `/api/v1/company/{id}` | 获取企业公开信息 | 所有人 |
| GET | `/api/v1/company/{id}/positions` | 获取企业在招职位 | 所有人 |
| GET | `/api/v1/company/deliveries` | 收到的简历列表 | 企业 |

### 6.4 简历模块

| 方法 | 路径 | 说明 | 权限 |
|------|------|------|------|
| GET | `/api/v1/resume` | 获取我的简历（含教育/工作/项目经历） | 已登录 |
| PUT | `/api/v1/resume` | 保存简历基本信息 | 已登录 |
| POST | `/api/v1/resume/attachment` | 上传附件简历（MultipartFile） | 已登录 |
| PUT | `/api/v1/resume/education` | 保存教育经历列表 | 已登录 |
| PUT | `/api/v1/resume/work` | 保存工作经历列表 | 已登录 |
| PUT | `/api/v1/resume/project` | 保存项目经验列表 | 已登录 |

### 6.5 投递模块

| 方法 | 路径 | 说明 | 权限 |
|------|------|------|------|
| POST | `/api/v1/deliveries?positionId=&resumeId=` | 投递职位 | 求职者 |
| GET | `/api/v1/deliveries` | 我的投递记录 | 求职者 |
| GET | `/api/v1/deliveries/{id}` | 投递详情 | 已登录 |
| PUT | `/api/v1/deliveries/{id}/status?status=` | 更新投递状态（企业端） | 企业 |

**投递状态说明：** `0=已投递` `1=被查看` `2=邀面试` `3=不合适` `4=已取消`

### 6.6 收藏模块

| 方法 | 路径 | 说明 | 权限 |
|------|------|------|------|
| POST | `/api/v1/favorites?positionId=` | 收藏职位 | 已登录 |
| DELETE | `/api/v1/favorites/{positionId}` | 取消收藏 | 已登录 |
| GET | `/api/v1/favorites` | 收藏列表 | 已登录 |

### 6.7 面试模块

| 方法 | 路径 | 说明 | 权限 |
|------|------|------|------|
| POST | `/api/v1/interviews` | 发送面试邀请 | 企业 |
| GET | `/api/v1/interviews` | 我的面试列表 | 已登录 |
| PUT | `/api/v1/interviews/{id}/status?status=` | 接受/拒绝面试 | 求职者 |

### 6.8 通知模块

| 方法 | 路径 | 说明 | 权限 |
|------|------|------|------|
| GET | `/api/v1/notifications` | 通知列表 | 已登录 |
| PUT | `/api/v1/notifications/{id}/read` | 标记已读 | 已登录 |
| PUT | `/api/v1/notifications/read-all` | 全部已读 | 已登录 |
| GET | `/api/v1/notifications/unread-count` | 未读通知数 | 已登录 |

### 6.9 管理后台

| 方法 | 路径 | 说明 | 权限 |
|------|------|------|------|
| GET | `/api/v1/admin/stats` | 数据统计概览 | 管理员 |
| GET | `/api/v1/admin/companies/pending` | 待审核企业列表 | 管理员 |
| PUT | `/api/v1/admin/companies/{id}/audit?approved=&rejectReason=` | 审核企业认证 | 管理员 |
| GET | `/api/v1/admin/positions/pending` | 待审核职位列表 | 管理员 |
| PUT | `/api/v1/admin/positions/{id}/audit?approved=&rejectReason=` | 审核职位 | 管理员 |
| GET | `/api/v1/admin/users` | 用户列表 | 管理员 |
| PUT | `/api/v1/admin/users/{id}/status?status=` | 禁用/启用用户（0=禁用 1=启用） | 管理员 |

### 6.10 模拟测试请求（Postman）

```json
// 1. 注册（求职者）
POST http://localhost:8080/JobPlus/api/v1/user/register
Content-Type: application/json
{
  "email": "test@test.com",
  "password": "123456",
  "role": 1,
  "nickname": "测试用户"
}

// 2. 登录
POST http://localhost:8080/JobPlus/api/v1/user/login
Content-Type: application/json
{
  "email": "candidate1@test.com",
  "password": "123456"
}

// 3. 搜索职位
GET http://localhost:8080/JobPlus/api/v1/positions?keyword=Java&page=1&size=10

// 4. 投递职位（需要已登录 Session）
POST http://localhost:8080/JobPlus/api/v1/deliveries?positionId=1&resumeId=1
```

---

## 7. 部署到生产环境

### 方案一：Tomcat 单机部署（推荐用于课程演示）

```bash
# 1. 构建
mvn clean package -DskipTests

# 2. 前端构建
cd frontend && npm install && npm run build

# 3. 部署到 Tomcat
cp target/JobPlus.war /path/to/tomcat/webapps/

# 4. 启动 Tomcat
/path/to/tomcat/bin/startup.sh

# 5. 访问
# http://<服务器IP>:8080/JobPlus/
```

> **注意：** 前端构建产物默认输出到 `src/main/webapp/`，与 WAR 包一起部署，因此无需单独配置 Nginx。

### 方案二：云服务器部署（可选）

```bash
# 推荐配置：阿里云/腾讯云轻量应用服务器，2C4G
# 安装 JDK 11 + Tomcat 9 + MySQL 8.0

# 修改 db.properties，将 localhost 改为云数据库地址
# 如需外网访问，在安全组开放 8080 端口
```

---

## 8. 常见问题

### Q: 启动 Tomcat 后访问报 404？
- 确认 WAR 包已正确复制到 `webapps/` 目录
- 确认 Tomcat 启动日志无报错：`tail -f logs/catalina.out`
- 确认访问路径正确：`http://localhost:8080/JobPlus/`

### Q: 前端页面调用 API 返回空数据？
- 确认 MySQL 服务正在运行，`init.sql` 和 `seed.sql` 已执行
- 确认 `db.properties` 中的数据库账号密码正确
- 查看 Tomcat 日志排查后端错误

### Q: 登录报错 "该邮箱已被注册"？
- 测试账号已预置在 `seed.sql` 中，直接用测试账号登录即可
- 如需新账号，使用未注册的邮箱注册

### Q: 企业无法发布职位？
- 确认为「企业」角色登录（不是求职者）
- 确认企业信息已填写（企业后台 → 企业信息）
- 确认企业认证已通过审核（状态为「已认证」）

### Q: 前端 npm install 报错？
- 确认 Node.js 版本 >= 14
- 尝试删除 `node_modules` 后重新安装：`rm -rf node_modules && npm install`

### Q: 如何重置数据库？
```bash
mysql -u root -p jobplus_db < sql/init.sql
mysql -u root -p jobplus_db < sql/seed.sql
```

---

## 技术栈

| 层次 | 技术 | 版本 |
|------|------|------|
| 后端框架 | Spring + Spring MVC + MyBatis (SSM) | Spring 5.3.31 / MyBatis 3.5.15 |
| 数据库 | MySQL | 8.0 |
| 连接池 | Druid | 1.2.20 |
| 密码加密 | Spring Security Crypto (BCrypt) | 5.8.8 |
| 前端 | Vue.js 2 + Element UI | Vue 2.7.14 / Element 2.15.14 |
| 构建 | Maven | 3.6+ |
| 部署 | Apache Tomcat | 9.x |
| 版本控制 | Git + GitHub | — |

---

> 项目地址：https://github.com/zerro-223/JobPlus
> 
> 如有问题，请在 GitHub Issues 中提出。
