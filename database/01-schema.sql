-- Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/
-- 上海如静知华信息科技有限公司
-- 商业授权或定制开发请微信添加微信号 zhuatech 或 zhuatech2 进行咨询。

CREATE DATABASE IF NOT EXISTS zhuatech_ehr
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_0900_ai_ci;

USE zhuatech_ehr;
SET NAMES utf8mb4;

CREATE TABLE IF NOT EXISTS ehr_department (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(32) NOT NULL UNIQUE,
    name VARCHAR(64) NOT NULL,
    sort_order INT NOT NULL DEFAULT 0,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS ehr_employee (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    employee_no VARCHAR(32) NOT NULL UNIQUE,
    username VARCHAR(32) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(50) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    position VARCHAR(50),
    gender VARCHAR(10),
    birth_date DATE,
    hire_date DATE NOT NULL,
    employment_status VARCHAR(20) NOT NULL,
    role VARCHAR(20) NOT NULL,
    enabled BIT NOT NULL DEFAULT 1,
    department_id BIGINT,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    INDEX idx_employee_status (employment_status),
    INDEX idx_employee_department (department_id),
    CONSTRAINT fk_employee_department FOREIGN KEY (department_id) REFERENCES ehr_department(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS ehr_attendance (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    employee_id BIGINT NOT NULL,
    work_date DATE NOT NULL,
    check_in_time DATETIME(6),
    check_out_time DATETIME(6),
    status VARCHAR(20) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    UNIQUE KEY uk_attendance_employee_date (employee_id, work_date),
    INDEX idx_attendance_work_date (work_date),
    CONSTRAINT fk_attendance_employee FOREIGN KEY (employee_id) REFERENCES ehr_employee(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS ehr_leave_request (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    applicant_id BIGINT NOT NULL,
    leave_type VARCHAR(20) NOT NULL,
    start_time DATETIME(6) NOT NULL,
    end_time DATETIME(6) NOT NULL,
    duration_days DECIMAL(5,1) NOT NULL,
    reason VARCHAR(500) NOT NULL,
    status VARCHAR(20) NOT NULL,
    approver_comment VARCHAR(500),
    approver_name VARCHAR(50),
    approved_at DATETIME(6),
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    INDEX idx_leave_status (status),
    INDEX idx_leave_applicant_time (applicant_id, start_time),
    CONSTRAINT fk_leave_applicant FOREIGN KEY (applicant_id) REFERENCES ehr_employee(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS ehr_payroll (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    employee_id BIGINT NOT NULL,
    payroll_month VARCHAR(7) NOT NULL,
    base_salary DECIMAL(12,2) NOT NULL,
    allowance DECIMAL(12,2) NOT NULL DEFAULT 0,
    bonus DECIMAL(12,2) NOT NULL DEFAULT 0,
    deduction DECIMAL(12,2) NOT NULL DEFAULT 0,
    net_salary DECIMAL(12,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    UNIQUE KEY uk_payroll_employee_month (employee_id, payroll_month),
    INDEX idx_payroll_month_status (payroll_month, status),
    CONSTRAINT fk_payroll_employee FOREIGN KEY (employee_id) REFERENCES ehr_employee(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS ehr_job_opening (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    department_id BIGINT NOT NULL,
    headcount INT NOT NULL,
    description VARCHAR(1000) NOT NULL,
    publish_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    INDEX idx_job_status (status),
    INDEX idx_job_department (department_id),
    CONSTRAINT fk_job_department FOREIGN KEY (department_id) REFERENCES ehr_department(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS ehr_candidate (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    job_id BIGINT NOT NULL,
    name VARCHAR(50) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(100),
    source VARCHAR(30) NOT NULL,
    stage VARCHAR(20) NOT NULL,
    remark VARCHAR(500),
    applied_date DATE NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    INDEX idx_candidate_stage (stage),
    INDEX idx_candidate_job (job_id),
    CONSTRAINT fk_candidate_job FOREIGN KEY (job_id) REFERENCES ehr_job_opening(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
