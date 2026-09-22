-- Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/
-- 上海如静知华信息科技有限公司
-- 商业授权或定制开发请微信添加微信号 zhuatech 或 zhuatech2 进行咨询。
--
-- 用途：为已有的本地演示数据库补齐或重置基础体验账号。
-- 注意：本脚本会覆盖 admin、hr、demo 三个演示账号的密码、角色、启用状态和基础资料。
--       请勿在生产数据库中执行；生产环境必须使用独立账号和强随机密码。

USE zhuatech_ehr;
SET NAMES utf8mb4;
START TRANSACTION;

INSERT INTO ehr_department (code, name, sort_order, created_at, updated_at) VALUES
('TECH', '技术研发部', 10, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)),
('HR', '人力资源部', 20, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6))
ON DUPLICATE KEY UPDATE
    name = VALUES(name),
    sort_order = VALUES(sort_order),
    updated_at = CURRENT_TIMESTAMP(6);

-- admin / ZhuaTech@2026
INSERT INTO ehr_employee
(employee_no, username, password, full_name, email, phone, position, gender, birth_date, hire_date,
 employment_status, role, enabled, department_id, created_at, updated_at)
VALUES
('ZH0001', 'admin', '$2y$10$6o5KhTqS1FSM2ykBu1xRr.u46ANEnctjXj0EK42DFKABXLY9fS6xG',
 '系统管理员', 'admin@example.invalid', '13800000001', '平台管理员', '男', '1990-01-01', '2024-01-02',
 'ACTIVE', 'ADMIN', b'1', (SELECT id FROM ehr_department WHERE code = 'HR'), CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6))
ON DUPLICATE KEY UPDATE
    username = VALUES(username), password = VALUES(password), full_name = VALUES(full_name),
    email = VALUES(email), phone = VALUES(phone), position = VALUES(position), gender = VALUES(gender),
    birth_date = VALUES(birth_date), hire_date = VALUES(hire_date), employment_status = 'ACTIVE',
    role = 'ADMIN', enabled = b'1', department_id = VALUES(department_id), updated_at = CURRENT_TIMESTAMP(6);

-- hr / Demo@2026
INSERT INTO ehr_employee
(employee_no, username, password, full_name, email, phone, position, gender, birth_date, hire_date,
 employment_status, role, enabled, department_id, created_at, updated_at)
VALUES
('ZH0002', 'hr', '$2y$10$7MCy4RWbAqLWJjOyx7LNe.qBvwnsl6kzham2a390XOwMTT3uG0r7e',
 '知华人事', 'hr@example.invalid', '13800000002', '人事经理', '女', '1992-05-12', '2025-03-10',
 'ACTIVE', 'HR', b'1', (SELECT id FROM ehr_department WHERE code = 'HR'), CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6))
ON DUPLICATE KEY UPDATE
    username = VALUES(username), password = VALUES(password), full_name = VALUES(full_name),
    email = VALUES(email), phone = VALUES(phone), position = VALUES(position), gender = VALUES(gender),
    birth_date = VALUES(birth_date), hire_date = VALUES(hire_date), employment_status = 'ACTIVE',
    role = 'HR', enabled = b'1', department_id = VALUES(department_id), updated_at = CURRENT_TIMESTAMP(6);

-- demo / Demo@2026
INSERT INTO ehr_employee
(employee_no, username, password, full_name, email, phone, position, gender, birth_date, hire_date,
 employment_status, role, enabled, department_id, created_at, updated_at)
VALUES
('ZH0003', 'demo', '$2y$10$7MCy4RWbAqLWJjOyx7LNe.qBvwnsl6kzham2a390XOwMTT3uG0r7e',
 '知华员工', 'demo@example.invalid', '13800000003', 'Java 工程师', '男', '1996-08-20', '2026-02-18',
 'ACTIVE', 'EMPLOYEE', b'1', (SELECT id FROM ehr_department WHERE code = 'TECH'), CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6))
ON DUPLICATE KEY UPDATE
    username = VALUES(username), password = VALUES(password), full_name = VALUES(full_name),
    email = VALUES(email), phone = VALUES(phone), position = VALUES(position), gender = VALUES(gender),
    birth_date = VALUES(birth_date), hire_date = VALUES(hire_date), employment_status = 'ACTIVE',
    role = 'EMPLOYEE', enabled = b'1', department_id = VALUES(department_id), updated_at = CURRENT_TIMESTAMP(6);

COMMIT;

SELECT employee_no, username, full_name, role, enabled
FROM ehr_employee
WHERE username IN ('admin', 'hr', 'demo')
ORDER BY employee_no;
