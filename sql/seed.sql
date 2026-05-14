-- ============================================================
-- JobPlus 种子数据 — 用于开发测试
-- ============================================================
USE jobplus_db;
SET FOREIGN_KEY_CHECKS = 0;

-- ============================================================
-- 用户数据（密码均为 BCrypt("123456") 加密值）
-- $2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi
-- ============================================================
INSERT INTO user (email, phone, password, role, nickname, status) VALUES
('admin@jobplus.com',     '13800000000', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 3, '系统管理员', 1),
('candidate1@test.com',   '13800000001', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 1, '张三', 1),
('candidate2@test.com',   '13800000002', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 1, '李四', 1),
('candidate3@test.com',   '13800000003', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 1, '王五', 1),
('employer1@test.com',    '13900000001', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 2, '阿里云-招聘官', 1),
('employer2@test.com',    '13900000002', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 2, '华为-HR', 1),
('employer3@test.com',    '13900000003', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 2, '字节跳动-招聘', 1);

-- ============================================================
-- 企业数据
-- ============================================================
INSERT INTO company (user_id, name, short_name, industry, scale, description, website, address, contact, contact_phone, status) VALUES
(5, '阿里云计算有限公司',     '阿里云',   '互联网/IT', '1000人以上',   '阿里云是全球领先的云计算及人工智能科技公司，服务全球数百万客户。', 'https://www.aliyun.com', '杭州市余杭区文一西路969号', '张经理', '0571-88888888', 1),
(6, '华为技术有限公司',       '华为',     '通信/IT',   '1000人以上',   '华为是全球领先的ICT基础设施和智能终端提供商。', 'https://www.huawei.com', '深圳市龙岗区坂田华为基地', '李经理', '0755-88888888', 1),
(7, '北京字节跳动科技有限公司', '字节跳动', '互联网',   '1000人以上',   '字节跳动旗下拥有抖音、今日头条等产品。', 'https://www.bytedance.com', '北京市海淀区知春路48号', '王经理', '010-88888888', 1);

-- ============================================================
-- 企业认证资料
-- ============================================================
INSERT INTO company_certification (company_id, license_url, license_number, legal_person, status, auditor_id, audited_at) VALUES
(1, '/uploads/cert/aliyun_license.jpg', '91330100MA27WFB00A', '王坚',   1, 1, '2026-01-01 00:00:00'),
(2, '/uploads/cert/huawei_license.jpg', '91440300279594163A', '任正非', 1, 1, '2026-01-01 00:00:00'),
(3, '/uploads/cert/bytedance_license.jpg', '91110000802040673U', '张一鸣', 1, 1, '2026-01-01 00:00:00');

-- ============================================================
-- 职位分类
-- ============================================================
INSERT INTO job_category (name, parent_id, sort_order) VALUES
('技术',      NULL, 1),
('产品',      NULL, 2),
('设计',      NULL, 3),
('运营',      NULL, 4),
('市场',      NULL, 5),
('后端开发',  1, 1),
('前端开发',  1, 2),
('移动开发',  1, 3),
('测试',      1, 4),
('运维',      1, 5),
('数据',      1, 6),
('AI/算法',   1, 7),
('产品经理',  2, 1),
('UI设计',    3, 1),
('用户运营',  4, 1);

-- ============================================================
-- 职位数据（position 是保留关键字，加反引号）
-- ============================================================
INSERT INTO `position` (company_id, category_id, title, description, requirement, salary_min, salary_max, education, experience, workplace, tags, status, expire_at) VALUES
(1, 6, 'Java 高级开发工程师',
 '负责阿里云核心产品的后端设计与开发，参与系统架构设计，优化系统性能。',
 '1. 本科及以上学历
2. 5年以上Java开发经验
3. 精通Spring/SpringBoot/MyBatis
4. 熟悉分布式系统设计',
 25, 45, '本科', '5-10年', '杭州', '五险一金,双休,年终奖,股票期权', 1, '2026-12-31'),

(1, 7, '前端开发工程师',
 '负责阿里云控制台前端开发，使用React技术栈，参与组件库建设。',
 '1. 3年以上前端开发经验
2. 精通HTML/CSS/JavaScript
3. 熟悉React/Vue框架',
 15, 30, '本科', '3-5年', '杭州', '五险一金,双休,弹性工作', 1, '2026-12-31'),

(2, 6, 'Java 开发工程师',
 '参与华为云平台后端服务开发，负责核心模块设计及编码实现。',
 '1. 本科及以上学历
2. 3年以上Java开发经验
3. 熟悉Spring全家桶',
 18, 35, '本科', '3-5年', '深圳', '五险一金,年终奖,班车', 1, '2026-12-31'),

(2, 11, '大数据开发工程师',
 '负责华为大数据平台建设，数据仓库设计与开发，ETL流程优化。',
 '1. 3年以上大数据开发经验
2. 熟悉Hadoop/Spark/Flink
3. 精通SQL和数据建模',
 20, 40, '本科', '3-5年', '深圳', '五险一金,弹性工作,期权', 1, '2026-12-31'),

(3, 6, '后端开发工程师-抖音',
 '负责抖音业务后端开发，高并发系统设计，参与系统性能优化。',
 '1. 2年以上后端开发经验
2. 精通Java/Go/C++至少一种
3. 熟悉MySQL/Redis/Kafka',
 30, 60, '本科', '1-3年', '北京', '五险一金,免费三餐,房补', 1, '2026-12-31'),

(3, 7, '前端开发工程师-飞书',
 '负责飞书Web端功能开发，参与前端基础设施建设。',
 '1. 2年以上前端经验
2. 精通React/TypeScript
3. 了解前端性能优化',
 25, 50, '本科', '1-3年', '北京', '五险一金,免费三餐,弹性工作', 1, '2026-12-31'),

(3, 12, '算法工程师-推荐系统',
 '负责抖音推荐系统的算法研发与优化，大规模机器学习模型训练与部署。',
 '1. 硕士及以上学历
2. 精通机器学习/深度学习
3. 熟悉Python/C++
4. 有推荐系统经验优先',
 35, 70, '硕士', '1-3年', '北京', '五险一金,股票期权,顶配电脑', 1, '2026-12-31');

-- ============================================================
-- 简历数据
-- ============================================================
INSERT INTO resume (user_id, real_name, gender, birth_date, phone, email, education, school, major, graduation_year, self_evaluation, skill_tags, is_public) VALUES
(2, '张三', '男', '1998-05-15', '13800000001', 'candidate1@test.com', '本科', '浙江大学', '计算机科学与技术', '2020',
 '5年Java开发经验，参与过多个大型分布式项目。',
 'Java,Spring Boot,MyBatis,MySQL,Redis,Docker,Kubernetes', 1),

(3, '李四', '男', '2000-03-20', '13800000002', 'candidate2@test.com', '本科', '杭州电子科技大学', '软件工程', '2022',
 '有前端开发和全栈项目经验。',
 'JavaScript,TypeScript,React,Vue.js,Node.js,Python', 1),

(4, '王五', '女', '1999-11-08', '13800000003', 'candidate3@test.com', '硕士', '北京大学', '计算机技术', '2024',
 '应届硕士，研究方向为自然语言处理。',
 'Python,PyTorch,TensorFlow,NLP,Deep Learning,SQL', 1);

-- 教育经历
INSERT INTO education_experience (resume_id, school, major, degree, start_date, end_date, description, sort_order) VALUES
(1, '浙江大学', '计算机科学与技术', '本科', '2016-09', '2020-06', 'GPA 3.8/4.0，ACM校队成员。', 1),
(2, '杭州电子科技大学', '软件工程', '本科', '2018-09', '2022-06', '校优秀学生干部。', 1),
(3, '北京大学', '计算机技术', '硕士', '2021-09', '2024-06', '研究方向NLP，在ACL 2023发表论文一篇。', 1),
(3, '武汉大学', '计算机科学与技术', '本科', '2017-09', '2021-06', '校级奖学金。', 2);

-- 工作经历
INSERT INTO work_experience (resume_id, company, `position`, start_date, end_date, description, sort_order) VALUES
(1, '网易', 'Java开发工程师', '2020-07', '2023-12', '负责网易云音乐后端服务开发。', 1),
(1, '蚂蚁集团', '高级Java开发', '2024-01', '2026-05', '负责支付宝核心支付链路开发与优化。', 2),
(2, '有赞科技', '前端开发实习生', '2021-07', '2022-06', '参与有赞小程序前端开发。', 1),
(2, '拼多多', '前端开发工程师', '2022-07', '2026-05', '负责商家端Web开发，参与组件库建设。', 2);

-- 项目经验
INSERT INTO project_experience (resume_id, name, role, start_date, end_date, description, sort_order) VALUES
(1, '分布式消息系统', '核心开发', '2022-03', '2022-09', '基于RocketMQ的消息处理平台。', 1),
(2, '电商后台管理系统', '前端负责人', '2023-01', '2023-06', '使用React+Ant Design搭建的B端管理平台。', 1),
(3, '多语言文本摘要系统', '核心算法', '2022-09', '2023-12', '基于Transformer的多语言文本摘要模型，发表ACL 2023论文。', 1);

SET FOREIGN_KEY_CHECKS = 1;
