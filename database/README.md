# 知华 EHR 数据库脚本

Copyright © 2026 上海如静知华信息科技有限公司。官网：[https://www.zhuatech.cn/](https://www.zhuatech.cn/)。商业授权或定制开发请微信添加微信号 `zhuatech` 或 `zhuatech2` 进行咨询。

本目录提供 MySQL 8.4 可直接执行的建库和虚构演示数据脚本，仅用于个人非商业学习、研究与技术交流。

## 文件说明

| 文件 | 作用 |
| --- | --- |
| `01-schema.sql` | 创建 `zhuatech_ehr` 数据库、7 张核心业务表、主外键、唯一约束和查询索引 |
| `02-demo-data.sql` | 初始化 6 个部门、6 个账号及考勤、假勤、薪资、招聘和候选人数据 |
| `03-repair-demo-accounts.sql` | 为已有演示库补齐或重置 `admin`、`hr`、`demo` 三个基础账号 |

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

## 已有数据库的账号修复

`02-demo-data.sql` 已包含基础账号。若数据库卷来自旧版本、账号被修改，或只导入过建表脚本，可在项目根目录执行：

```bash
docker compose exec -T mysql sh -c 'mysql -uroot -p"$MYSQL_ROOT_PASSWORD" "$MYSQL_DATABASE"' \
  < database/03-repair-demo-accounts.sql
```

该脚本会补齐部门，并将 `admin`、`hr`、`demo` 恢复为上表中的演示密码、角色和启用状态。它只面向本地演示环境，不应在生产数据库执行。

可使用以下命令检查账号是否存在：

```bash
docker compose exec -T mysql sh -c \
  'mysql -uroot -p"$MYSQL_ROOT_PASSWORD" "$MYSQL_DATABASE" -e \
  "SELECT employee_no,username,role,enabled FROM ehr_employee WHERE username IN (\"admin\",\"hr\",\"demo\");"'
```

## 登录返回 403

如果通过 `http://127.0.0.1:8088` 访问旧版本时登录返回 403，原因通常不是账号缺失，而是旧版默认只允许 `http://localhost:8088`。更新代码后重新构建即可，无须删除数据库卷：

```bash
docker compose up --build -d
```

当前版本会兼容旧 `.env`，并始终允许默认开发端口上的 `localhost` 和 `127.0.0.1`。更新后请使用 `docker compose up --build -d --force-recreate`，确保后端和前端容器均使用新代码。如使用其他域名、IP 或端口，请在 `.env` 的 `CORS_ORIGINS` 中填写完整来源，多个来源以英文逗号分隔。

脚本使用唯一键和存在性判断降低重复导入风险，不会明文保存密码。所有姓名、电话、邮箱、考勤、薪资和招聘记录均为虚构数据，邮箱使用 `.invalid` 保留域名。

> 公网或生产部署前，必须删除演示账号和演示数据，重新创建管理员，替换数据库密码与 `JWT_SECRET`，并配置备份、审计、最小权限、传输加密和敏感字段保护。

本项目采用个人非商业社区源码许可，企业内部使用、生产部署、SaaS、项目交付、咨询实施或其他商业使用须事先取得上海如静知华信息科技有限公司书面授权。
