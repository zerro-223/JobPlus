-- ============================================================
-- JobPlus 综合性在线招聘平台 - 数据库初始化脚本
-- 数据库版本：MySQL 8.0
-- ============================================================

-- 先删除整个数据库，清理所有旧表和旧外键约束
DROP DATABASE IF EXISTS jobplus_db;
CREATE DATABASE jobplus_db
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE jobplus_db;

-- ============================================================
-- 1. 用户表 (user)
-- ============================================================
DROP TABLE IF EXISTS user;
CREATE TABLE user (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    email       VARCHAR(100) UNIQUE NOT NULL       COMMENT '邮箱（登录凭证）',
    phone       VARCHAR(20) UNIQUE                 COMMENT '手机号',
    password    VARCHAR(255) NOT NULL              COMMENT '加密后的密码（BCrypt）',
    role        TINYINT NOT NULL DEFAULT 1         COMMENT '1=求职者 2=企业 3=管理员',
    nickname    VARCHAR(50)                        COMMENT '昵称',
    avatar_url  VARCHAR(255)                       COMMENT '头像URL',
    status      TINYINT NOT NULL DEFAULT 1         COMMENT '1=正常 0=禁用 2=待审核',
    created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_role (role),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- ============================================================
-- 2. 企业信息表 (company)
-- ============================================================
DROP TABLE IF EXISTS company;
CREATE TABLE company (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    user_id     INT NOT NULL UNIQUE                COMMENT '关联 user.id',
    name        VARCHAR(100) NOT NULL              COMMENT '企业全称',
    short_name  VARCHAR(50)                        COMMENT '企业简称',
    logo_url    VARCHAR(255)                       COMMENT 'Logo URL',
    industry    VARCHAR(50)                        COMMENT '所属行业',
    scale       VARCHAR(20)                        COMMENT '企业规模',
    description TEXT                               COMMENT '企业简介',
    website     VARCHAR(100)                       COMMENT '企业官网',
    address     VARCHAR(200)                       COMMENT '办公地址',
    contact     VARCHAR(50)                        COMMENT '联系人',
    contact_phone VARCHAR(20)                      COMMENT '联系电话',
    status      TINYINT NOT NULL DEFAULT 0         COMMENT '0=待认证 1=已认证 2=认证驳回',
    created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='企业信息表';

-- ============================================================
-- 3. 企业认证资料表 (company_certification)
-- ============================================================
DROP TABLE IF EXISTS company_certification;
CREATE TABLE company_certification (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    company_id      INT NOT NULL                   COMMENT '关联 company.id',
    license_url     VARCHAR(255) NOT NULL          COMMENT '营业执照图片路径',
    license_number  VARCHAR(100)                   COMMENT '统一社会信用代码',
    legal_person    VARCHAR(50)                    COMMENT '法定代表人',
    reject_reason   VARCHAR(500)                   COMMENT '驳回原因',
    status          TINYINT NOT NULL DEFAULT 0     COMMENT '0=待审核 1=通过 2=驳回',
    auditor_id      INT                            COMMENT '审核人ID',
    audited_at      DATETIME                       COMMENT '审核时间',
    created_at      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (company_id) REFERENCES company(id),
    FOREIGN KEY (auditor_id) REFERENCES user(id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='企业认证资料表';

-- ============================================================
-- 4. 职位分类表 (job_category)
-- ============================================================
DROP TABLE IF EXISTS job_category;
CREATE TABLE job_category (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(50) NOT NULL               COMMENT '分类名称',
    parent_id   INT DEFAULT NULL                   COMMENT '父分类ID',
    sort_order  INT DEFAULT 0                      COMMENT '排序',
    created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (parent_id) REFERENCES job_category(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='职位分类表';

-- ============================================================
-- 5. 职位信息表 (position) — position是保留关键字，加反引号
-- ============================================================
DROP TABLE IF EXISTS `position`;
CREATE TABLE `position` (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    company_id      INT NOT NULL                   COMMENT '所属企业',
    category_id     INT                            COMMENT '职位分类',
    title           VARCHAR(100) NOT NULL          COMMENT '职位标题',
    description     TEXT NOT NULL                  COMMENT '职位描述',
    requirement     TEXT                           COMMENT '任职要求',
    salary_min      DECIMAL(10,2)                  COMMENT '薪资下限(K/月)',
    salary_max      DECIMAL(10,2)                  COMMENT '薪资上限(K/月)',
    education       VARCHAR(20) DEFAULT '不限'     COMMENT '学历要求',
    experience      VARCHAR(20) DEFAULT '不限'     COMMENT '经验要求',
    workplace       VARCHAR(100) NOT NULL          COMMENT '工作地点',
    tags            VARCHAR(255)                   COMMENT '职位标签(逗号分隔)',
    status          TINYINT NOT NULL DEFAULT 0     COMMENT '0=待审核 1=招聘中 2=已下线 3=审核驳回',
    view_count      INT DEFAULT 0                  COMMENT '浏览次数',
    delivery_count  INT DEFAULT 0                  COMMENT '收到投递数',
    expire_at       DATE                           COMMENT '过期时间',
    created_at      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (company_id) REFERENCES company(id),
    FOREIGN KEY (category_id) REFERENCES job_category(id),
    INDEX idx_company (company_id),
    INDEX idx_category (category_id),
    INDEX idx_status (status),
    INDEX idx_workplace (workplace),
    INDEX idx_created (created_at),
    FULLTEXT INDEX ft_title_desc (title, description)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='职位信息表';

-- ============================================================
-- 6. 简历主表 (resume)
-- ============================================================
DROP TABLE IF EXISTS resume;
CREATE TABLE resume (
    id                INT AUTO_INCREMENT PRIMARY KEY,
    user_id           INT NOT NULL UNIQUE          COMMENT '关联 user.id',
    real_name         VARCHAR(50)                  COMMENT '真实姓名',
    gender            VARCHAR(10)                  COMMENT '性别',
    birth_date        DATE                         COMMENT '出生日期',
    phone             VARCHAR(20)                  COMMENT '联系电话',
    email             VARCHAR(100)                 COMMENT '联系邮箱',
    education         VARCHAR(20)                  COMMENT '最高学历',
    school            VARCHAR(100)                 COMMENT '毕业院校',
    major             VARCHAR(100)                 COMMENT '专业',
    graduation_year   VARCHAR(4)                   COMMENT '毕业年份',
    self_evaluation   TEXT                         COMMENT '自我评价',
    skill_tags        VARCHAR(500)                 COMMENT '技能标签',
    attachment_url    VARCHAR(255)                 COMMENT '附件简历路径',
    attachment_name   VARCHAR(200)                 COMMENT '附件原始文件名',
    is_public         TINYINT DEFAULT 1            COMMENT '1=公开 0=仅投递可见',
    created_at        DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at        DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id),
    INDEX idx_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='简历主表';

-- ============================================================
-- 7. 教育经历表 (education_experience)
-- ============================================================
DROP TABLE IF EXISTS education_experience;
CREATE TABLE education_experience (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    resume_id   INT NOT NULL                      COMMENT '关联 resume.id',
    school      VARCHAR(100) NOT NULL             COMMENT '学校名称',
    major       VARCHAR(100)                      COMMENT '专业',
    degree      VARCHAR(20)                       COMMENT '学历',
    start_date  VARCHAR(7)                        COMMENT '开始 YYYY-MM',
    end_date    VARCHAR(7)                        COMMENT '结束 YYYY-MM',
    description TEXT                              COMMENT '在校经历描述',
    sort_order  INT DEFAULT 0,
    FOREIGN KEY (resume_id) REFERENCES resume(id) ON DELETE CASCADE,
    INDEX idx_resume (resume_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='教育经历表';

-- ============================================================
-- 8. 工作经历表 (work_experience)
-- ============================================================
DROP TABLE IF EXISTS work_experience;
CREATE TABLE work_experience (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    resume_id   INT NOT NULL                      COMMENT '关联 resume.id',
    company     VARCHAR(100) NOT NULL             COMMENT '公司名称',
    position    VARCHAR(100) NOT NULL             COMMENT '职位名称(非关键字)',
    start_date  VARCHAR(7)                        COMMENT '开始 YYYY-MM',
    end_date    VARCHAR(7)                        COMMENT '结束 YYYY-MM',
    description TEXT                              COMMENT '工作描述',
    sort_order  INT DEFAULT 0,
    FOREIGN KEY (resume_id) REFERENCES resume(id) ON DELETE CASCADE,
    INDEX idx_resume (resume_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='工作经历表';

-- ============================================================
-- 9. 项目经验表 (project_experience)
-- ============================================================
DROP TABLE IF EXISTS project_experience;
CREATE TABLE project_experience (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    resume_id   INT NOT NULL                      COMMENT '关联 resume.id',
    name        VARCHAR(100) NOT NULL             COMMENT '项目名称',
    role        VARCHAR(50)                       COMMENT '担任角色',
    start_date  VARCHAR(7)                        COMMENT '开始 YYYY-MM',
    end_date    VARCHAR(7)                        COMMENT '结束 YYYY-MM',
    description TEXT                              COMMENT '项目描述',
    url         VARCHAR(255)                      COMMENT '项目链接',
    sort_order  INT DEFAULT 0,
    FOREIGN KEY (resume_id) REFERENCES resume(id) ON DELETE CASCADE,
    INDEX idx_resume (resume_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='项目经验表';

-- ============================================================
-- 10. 投递记录表 (delivery)
-- ============================================================
DROP TABLE IF EXISTS delivery;
CREATE TABLE delivery (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    user_id       INT NOT NULL                    COMMENT '求职者ID',
    position_id   INT NOT NULL                    COMMENT '职位ID',
    resume_id     INT NOT NULL                    COMMENT '简历ID',
    status        TINYINT NOT NULL DEFAULT 0      COMMENT '0=已投递 1=被查看 2=邀面试 3=不合适 4=已取消',
    company_read  BOOLEAN DEFAULT FALSE           COMMENT '企业是否查看',
    read_at       DATETIME                        COMMENT '企业查看时间',
    created_at    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (position_id) REFERENCES `position`(id),
    FOREIGN KEY (resume_id) REFERENCES resume(id),
    UNIQUE KEY uk_user_position (user_id, position_id),
    INDEX idx_user (user_id),
    INDEX idx_position (position_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='投递记录表';

-- ============================================================
-- 11. 收藏记录表 (favorite)
-- ============================================================
DROP TABLE IF EXISTS favorite;
CREATE TABLE favorite (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    user_id     INT NOT NULL                      COMMENT '求职者ID',
    position_id INT NOT NULL                      COMMENT '职位ID',
    created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (position_id) REFERENCES `position`(id),
    UNIQUE KEY uk_user_position (user_id, position_id),
    INDEX idx_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='收藏记录表';

-- ============================================================
-- 12. 面试邀请表 (interview)
-- ============================================================
DROP TABLE IF EXISTS interview;
CREATE TABLE interview (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    delivery_id   INT NOT NULL                    COMMENT '关联投递记录',
    company_id    INT NOT NULL                    COMMENT '企业ID',
    user_id       INT NOT NULL                    COMMENT '求职者ID',
    interview_time DATETIME NOT NULL              COMMENT '面试时间',
    location      VARCHAR(200)                    COMMENT '面试地点',
    contact       VARCHAR(50)                     COMMENT '联系人',
    contact_phone VARCHAR(20)                     COMMENT '联系电话',
    remark        VARCHAR(500)                    COMMENT '备注',
    status        TINYINT NOT NULL DEFAULT 0      COMMENT '0=待确认 1=已接受 2=已拒绝 3=已完成',
    created_at    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (delivery_id) REFERENCES delivery(id),
    FOREIGN KEY (company_id) REFERENCES company(id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    INDEX idx_company (company_id),
    INDEX idx_user (user_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='面试邀请表';

-- ============================================================
-- 13. 通知消息表 (notification)
-- ============================================================
DROP TABLE IF EXISTS notification;
CREATE TABLE notification (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    user_id     INT NOT NULL                      COMMENT '接收用户ID',
    type        VARCHAR(30) NOT NULL              COMMENT '通知类型',
    title       VARCHAR(200) NOT NULL             COMMENT '通知标题',
    content     TEXT                              COMMENT '通知内容',
    related_id  INT                               COMMENT '关联业务ID',
    is_read     BOOLEAN DEFAULT FALSE             COMMENT '是否已读',
    read_at     DATETIME                          COMMENT '阅读时间',
    created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id),
    INDEX idx_user_read (user_id, is_read),
    INDEX idx_type (type),
    INDEX idx_created (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='通知消息表';

-- ============================================================
-- 14. 管理员操作日志表 (admin_action_log)
-- ============================================================
DROP TABLE IF EXISTS admin_action_log;
CREATE TABLE admin_action_log (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    admin_id     INT NOT NULL                     COMMENT '管理员用户ID',
    action_type  VARCHAR(50) NOT NULL             COMMENT '操作类型',
    target_id    INT                              COMMENT '操作目标ID',
    target_type  VARCHAR(50)                      COMMENT '目标类型',
    detail       VARCHAR(500)                     COMMENT '操作详情',
    ip_address   VARCHAR(50)                      COMMENT '操作IP',
    created_at   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (admin_id) REFERENCES user(id),
    INDEX idx_admin (admin_id),
    INDEX idx_created (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='管理员操作日志表';
