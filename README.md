# JobPlus 综合性在线招聘平台

JobPlus 是一个集 Web 网站、微信小程序和移动 App 于一体的综合性在线招聘平台，旨在通过技术手段优化招聘全流程。

## 技术栈

| 层次 | 技术 |
|------|------|
| 后端框架 | Spring 5.x + Spring MVC + MyBatis 3.5 (SSM) |
| 数据库 | MySQL 8.0 |
| 连接池 | Druid |
| 前端 | Vue.js 2.x + Element UI |
| 小程序 | 微信小程序原生 |
| 构建工具 | Maven 3.6+ |
| 版本控制 | Git + GitHub |

## 项目结构

```
JobPlus/
├── sql/                    # 数据库脚本
│   ├── init.sql           # 14张表建表
│   └── seed.sql           # 测试数据
├── src/main/
│   ├── java/com/jobplus/
│   │   ├── entity/        # 实体类 (14个)
│   │   ├── mapper/        # MyBatis Mapper接口 (14个)
│   │   ├── service/       # 业务接口+实现 (10组)
│   │   ├── controller/    # REST Controller (9个)
│   │   └── common/        # 通用模块
│   │       ├── dto/       # DTO/VO
│   │       ├── exception/ # 异常处理
│   │       ├── interceptor/ # 权限拦截器
│   │       ├── annotation/  # 自定义注解
│   │       ├── config/    # 配置类
│   │       └── utils/     # 工具类
│   └── resources/
│       ├── spring/        # Spring配置
│       └── mapper/        # MyBatis XML
├── frontend/              # Vue.js 前端
│   └── src/views/         # 15个页面
├── miniapp/               # 微信小程序
│   └── pages/             # 8个页面
└── pom.xml
```

## 快速开始

### 1. 数据库
```bash
mysql -u root -p < sql/init.sql
mysql -u root -p < sql/seed.sql
```

### 2. 后端
```bash
mvn clean package
# 将 target/JobPlus.war 部署到 Tomcat 9.x webapps/
```

### 3. 前端
```bash
cd frontend
npm install
npm run serve    # 开发模式 (http://localhost:8081)
npm run build    # 构建到 src/main/webapp/
```

### 4. 测试账号
| 角色 | 邮箱 | 密码 |
|------|------|------|
| 管理员 | admin@jobplus.com | 123456 |
| 求职者 | candidate1@test.com | 123456 |
| 企业 | employer1@test.com | 123456 |

## API 接口

前缀: `/api/v1/`

- `POST /user/register` - 注册
- `POST /user/login` - 登录
- `GET /positions` - 职位搜索 (支持分页+筛选)
- `GET /positions/{id}` - 职位详情
- `POST /deliveries` - 投递职位
- `GET /resume` - 我的简历
- `POST /favorites` - 收藏
- `GET /admin/stats` - 数据统计 (管理员)

完整接口文档见 `src/main/java/com/jobplus/controller/` 各 Controller 类。
