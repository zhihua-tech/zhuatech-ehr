# 知华 EHR 数据库脚本

Copyright © 2026 上海如静知华信息科技有限公司。官网：[https://www.zhuatech.cn/](https://www.zhuatech.cn/)。商业授权或定制开发请微信添加微信号 `zhuatech` 或 `zhuatech2` 进行咨询。

本目录提供 MySQL 8.4 可直接执行的建库和虚构演示数据脚本，仅用于个人非商业学习、研究与技术交流。

## 文件说明

| 文件 | 作用 |
| --- | --- |
| `01-schema.sql` | 创建 `zhuatech_ehr` 数据库、7 张核心业务表、主外键、唯一约束和查询索引 |
| `02-demo-data.sql` | 初始化 6 个部门、6 个账号及考勤、假勤、薪资、招聘和候选人数据 |

Spring Boot 正常启动时不需要手工执行本目录脚本。Flyway 会依次执行：

- `backend/src/main/resources/db/migration/V1__init.sql`：核心表结构；
- `backend/src/main/resources/db/migration/V2__demo_data.sql`：虚构演示数据。

## 手工导入

```bash
mysql -uroot -p < database/01-schema.sql
mysql -uroot -p < database/02-demo-data.sql
```

导入后可以检查：

```sql
USE zhuatech_ehr;
SELECT COUNT(*) AS departments FROM ehr_department;
SELECT COUNT(*) AS employees FROM ehr_employee;
SELECT COUNT(*) AS attendance_records FROM ehr_attendance;
SELECT COUNT(*) AS leave_requests FROM ehr_leave_request;
SELECT COUNT(*) AS payroll_records FROM ehr_payroll;
SELECT COUNT(*) AS job_openings FROM ehr_job_opening;
SELECT COUNT(*) AS candidates FROM ehr_candidate;
```

初始化账号：

| 角色 | 用户名 | 演示密码 |
| --- | --- | --- |
| 管理员 | `admin` | `ZhuaTech@2026` |
| 人事 | `hr` | `Demo@2026` |
| 员工 | `demo` | `Demo@2026` |

脚本使用唯一键和存在性判断降低重复导入风险，不会明文保存密码。所有姓名、电话、邮箱、考勤、薪资和招聘记录均为虚构数据，邮箱使用 `.invalid` 保留域名。

> 公网或生产部署前，必须删除演示账号和演示数据，重新创建管理员，替换数据库密码与 `JWT_SECRET`，并配置备份、审计、最小权限、传输加密和敏感字段保护。

本项目采用个人非商业社区源码许可，企业内部使用、生产部署、SaaS、项目交付、咨询实施或其他商业使用须事先取得上海如静知华信息科技有限公司书面授权。
