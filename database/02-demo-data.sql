-- Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/
-- 上海如静知华信息科技有限公司：本脚本仅用于个人非商业学习环境的虚构演示数据。
-- 商业授权或定制开发请微信添加微信号 zhuatech 或 zhuatech2 进行咨询。

-- 演示密码：admin 使用 ZhuaTech@2026，其余账号使用 Demo@2026。
-- BCrypt 仅保存不可逆哈希；生产部署前必须删除演示账号并创建独立管理员。

USE zhuatech_ehr;
SET NAMES utf8mb4;
START TRANSACTION;

INSERT IGNORE INTO ehr_department (code, name, sort_order, created_at, updated_at) VALUES
('MGMT', '经营管理部', 5, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)),
('TECH', '技术研发部', 10, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)),
('HR', '人力资源部', 20, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)),
('SALES', '市场商务部', 30, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)),
('FINANCE', '财务管理部', 40, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)),
('DELIVERY', '项目交付部', 50, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6));

INSERT IGNORE INTO ehr_employee
(employee_no, username, password, full_name, email, phone, position, gender, birth_date, hire_date,
 employment_status, role, enabled, department_id, created_at, updated_at)
VALUES
('ZH0001', 'admin', '$2y$10$6o5KhTqS1FSM2ykBu1xRr.u46ANEnctjXj0EK42DFKABXLY9fS6xG',
 '系统管理员', 'admin@example.invalid', '13800000001', '平台管理员', '男', '1990-01-01', '2024-01-02',
 'ACTIVE', 'ADMIN', b'1', (SELECT id FROM ehr_department WHERE code = 'HR'), CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)),
('ZH0002', 'hr', '$2y$10$7MCy4RWbAqLWJjOyx7LNe.qBvwnsl6kzham2a390XOwMTT3uG0r7e',
 '知华人事', 'hr@example.invalid', '13800000002', '人事经理', '女', '1992-05-12', '2025-03-10',
 'ACTIVE', 'HR', b'1', (SELECT id FROM ehr_department WHERE code = 'HR'), CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)),
('ZH0003', 'demo', '$2y$10$7MCy4RWbAqLWJjOyx7LNe.qBvwnsl6kzham2a390XOwMTT3uG0r7e',
 '知华员工', 'demo@example.invalid', '13800000003', 'Java 工程师', '男', '1996-08-20', '2026-02-18',
 'ACTIVE', 'EMPLOYEE', b'1', (SELECT id FROM ehr_department WHERE code = 'TECH'), CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)),
('ZH0004', 'zhangsan', '$2y$10$7MCy4RWbAqLWJjOyx7LNe.qBvwnsl6kzham2a390XOwMTT3uG0r7e',
 '张珊', 'zhangsan@example.invalid', '13800000004', '商务经理', '女', '1993-11-06', '2025-07-08',
 'ACTIVE', 'EMPLOYEE', b'1', (SELECT id FROM ehr_department WHERE code = 'SALES'), CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)),
('ZH0005', 'chenwei', '$2y$10$7MCy4RWbAqLWJjOyx7LNe.qBvwnsl6kzham2a390XOwMTT3uG0r7e',
 '陈伟', 'chenwei@example.invalid', '13800000005', '薪酬专员', '男', '1994-04-16', '2025-09-01',
 'ACTIVE', 'EMPLOYEE', b'1', (SELECT id FROM ehr_department WHERE code = 'FINANCE'), CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)),
('ZH0006', 'liuqiang', '$2y$10$7MCy4RWbAqLWJjOyx7LNe.qBvwnsl6kzham2a390XOwMTT3uG0r7e',
 '刘强', 'liuqiang@example.invalid', '13800000006', '实施顾问', '男', '1995-09-18', '2026-01-12',
 'PROBATION', 'EMPLOYEE', b'1', (SELECT id FROM ehr_department WHERE code = 'DELIVERY'), CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6));

INSERT IGNORE INTO ehr_attendance
(employee_id, work_date, check_in_time, check_out_time, status, created_at, updated_at)
VALUES
((SELECT id FROM ehr_employee WHERE employee_no = 'ZH0003'), CURRENT_DATE,
 TIMESTAMP(CURRENT_DATE, '08:56:00'), NULL, 'NORMAL', CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)),
((SELECT id FROM ehr_employee WHERE employee_no = 'ZH0004'), CURRENT_DATE,
 TIMESTAMP(CURRENT_DATE, '09:18:00'), NULL, 'LATE', CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)),
((SELECT id FROM ehr_employee WHERE employee_no = 'ZH0005'), CURRENT_DATE,
 TIMESTAMP(CURRENT_DATE, '08:48:00'), NULL, 'NORMAL', CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)),
((SELECT id FROM ehr_employee WHERE employee_no = 'ZH0006'), DATE_SUB(CURRENT_DATE, INTERVAL 1 DAY),
 TIMESTAMP(DATE_SUB(CURRENT_DATE, INTERVAL 1 DAY), '08:50:00'),
 TIMESTAMP(DATE_SUB(CURRENT_DATE, INTERVAL 1 DAY), '18:05:00'), 'NORMAL', CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6));

INSERT INTO ehr_leave_request
(applicant_id, leave_type, start_time, end_time, duration_days, reason, status,
 approver_comment, approver_name, approved_at, created_at, updated_at)
SELECT e.id, 'ANNUAL', DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 5 DAY), DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 6 DAY),
       1.0, '个人事务（虚构演示数据）', 'PENDING', NULL, NULL, NULL, CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)
FROM ehr_employee e
WHERE e.employee_no = 'ZH0003'
  AND NOT EXISTS (
      SELECT 1 FROM ehr_leave_request l
      WHERE l.applicant_id = e.id AND l.status = 'PENDING' AND l.reason = '个人事务（虚构演示数据）'
  );

INSERT INTO ehr_leave_request
(applicant_id, leave_type, start_time, end_time, duration_days, reason, status,
 approver_comment, approver_name, approved_at, created_at, updated_at)
SELECT e.id, 'SICK', DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 10 DAY), DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 9 DAY),
       1.0, '病假流程演示（虚构演示数据）', 'APPROVED', '材料齐全，同意请假', '知华人事',
       DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 12 DAY), CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)
FROM ehr_employee e
WHERE e.employee_no = 'ZH0004'
  AND NOT EXISTS (
      SELECT 1 FROM ehr_leave_request l
      WHERE l.applicant_id = e.id AND l.status = 'APPROVED' AND l.reason = '病假流程演示（虚构演示数据）'
  );

INSERT IGNORE INTO ehr_payroll
(employee_id, payroll_month, base_salary, allowance, bonus, deduction, net_salary, status, created_at, updated_at)
VALUES
((SELECT id FROM ehr_employee WHERE employee_no = 'ZH0003'), DATE_FORMAT(DATE_SUB(CURRENT_DATE, INTERVAL 1 MONTH), '%Y-%m'),
 12000.00, 800.00, 1000.00, 650.00, 13150.00, 'PAID', CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)),
((SELECT id FROM ehr_employee WHERE employee_no = 'ZH0004'), DATE_FORMAT(DATE_SUB(CURRENT_DATE, INTERVAL 1 MONTH), '%Y-%m'),
 13500.00, 1200.00, 1800.00, 780.00, 15720.00, 'PAID', CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)),
((SELECT id FROM ehr_employee WHERE employee_no = 'ZH0005'), DATE_FORMAT(DATE_SUB(CURRENT_DATE, INTERVAL 1 MONTH), '%Y-%m'),
 11000.00, 600.00, 500.00, 530.00, 11570.00, 'CONFIRMED', CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)),
((SELECT id FROM ehr_employee WHERE employee_no = 'ZH0006'), DATE_FORMAT(CURRENT_DATE, '%Y-%m'),
 10500.00, 900.00, 0.00, 460.00, 10940.00, 'DRAFT', CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6));

INSERT INTO ehr_job_opening
(title, department_id, headcount, description, publish_date, status, created_at, updated_at)
SELECT 'Java 开发工程师', d.id, 2,
       '负责企业信息化产品 Java 后端研发，熟悉 Spring Boot、MySQL 与自动化测试。',
       DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY), 'OPEN', CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)
FROM ehr_department d
WHERE d.code = 'TECH'
  AND NOT EXISTS (SELECT 1 FROM ehr_job_opening j WHERE j.title = 'Java 开发工程师' AND j.department_id = d.id AND j.status = 'OPEN');

INSERT INTO ehr_job_opening
(title, department_id, headcount, description, publish_date, status, created_at, updated_at)
SELECT '项目实施顾问', d.id, 3,
       '负责客户需求调研、系统配置、数据初始化、用户培训与上线支持。',
       DATE_SUB(CURRENT_DATE, INTERVAL 4 DAY), 'OPEN', CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)
FROM ehr_department d
WHERE d.code = 'DELIVERY'
  AND NOT EXISTS (SELECT 1 FROM ehr_job_opening j WHERE j.title = '项目实施顾问' AND j.department_id = d.id AND j.status = 'OPEN');

INSERT INTO ehr_job_opening
(title, department_id, headcount, description, publish_date, status, created_at, updated_at)
SELECT '解决方案顾问', d.id, 1,
       '负责中小企业信息化与 AI 转型方案设计、售前交流和项目协同。',
       DATE_SUB(CURRENT_DATE, INTERVAL 2 DAY), 'OPEN', CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)
FROM ehr_department d
WHERE d.code = 'SALES'
  AND NOT EXISTS (SELECT 1 FROM ehr_job_opening j WHERE j.title = '解决方案顾问' AND j.department_id = d.id AND j.status = 'OPEN');

INSERT INTO ehr_candidate
(job_id, name, phone, email, source, stage, remark, applied_date, created_at, updated_at)
SELECT j.id, '李明', '13700000001', 'candidate01@example.invalid', '官网投递', 'INTERVIEW',
       '已完成简历初筛，等待技术面试（虚构演示数据）', DATE_SUB(CURRENT_DATE, INTERVAL 5 DAY), CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)
FROM ehr_job_opening j
WHERE j.title = 'Java 开发工程师' AND j.status = 'OPEN'
  AND NOT EXISTS (SELECT 1 FROM ehr_candidate c WHERE c.job_id = j.id AND c.phone = '13700000001');

INSERT INTO ehr_candidate
(job_id, name, phone, email, source, stage, remark, applied_date, created_at, updated_at)
SELECT j.id, '周敏', '13700000002', 'candidate02@example.invalid', '员工推荐', 'SCREENING',
       '待业务负责人复核（虚构演示数据）', DATE_SUB(CURRENT_DATE, INTERVAL 3 DAY), CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)
FROM ehr_job_opening j
WHERE j.title = 'Java 开发工程师' AND j.status = 'OPEN'
  AND NOT EXISTS (SELECT 1 FROM ehr_candidate c WHERE c.job_id = j.id AND c.phone = '13700000002');

INSERT INTO ehr_candidate
(job_id, name, phone, email, source, stage, remark, applied_date, created_at, updated_at)
SELECT j.id, '王琳', '13700000003', 'candidate03@example.invalid', '招聘平台', 'OFFER',
       '面试通过，待确认入职日期（虚构演示数据）', DATE_SUB(CURRENT_DATE, INTERVAL 8 DAY), CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)
FROM ehr_job_opening j
WHERE j.title = '项目实施顾问' AND j.status = 'OPEN'
  AND NOT EXISTS (SELECT 1 FROM ehr_candidate c WHERE c.job_id = j.id AND c.phone = '13700000003');

INSERT INTO ehr_candidate
(job_id, name, phone, email, source, stage, remark, applied_date, created_at, updated_at)
SELECT j.id, '赵凯', '13700000004', 'candidate04@example.invalid', '官网投递', 'APPLIED',
       '等待初筛（虚构演示数据）', DATE_SUB(CURRENT_DATE, INTERVAL 1 DAY), CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)
FROM ehr_job_opening j
WHERE j.title = '项目实施顾问' AND j.status = 'OPEN'
  AND NOT EXISTS (SELECT 1 FROM ehr_candidate c WHERE c.job_id = j.id AND c.phone = '13700000004');

INSERT INTO ehr_candidate
(job_id, name, phone, email, source, stage, remark, applied_date, created_at, updated_at)
SELECT j.id, '孙悦', '13700000005', 'candidate05@example.invalid', '行业社群', 'INTERVIEW',
       '预约方案演示面试（虚构演示数据）', DATE_SUB(CURRENT_DATE, INTERVAL 4 DAY), CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6)
FROM ehr_job_opening j
WHERE j.title = '解决方案顾问' AND j.status = 'OPEN'
  AND NOT EXISTS (SELECT 1 FROM ehr_candidate c WHERE c.job_id = j.id AND c.phone = '13700000005');

COMMIT;
