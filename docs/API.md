# ZhuaTech EHR REST API

Copyright © 2026 上海如静知华信息科技有限公司。

基础路径为 `/api`。除登录外，请传入 `Authorization: Bearer <token>`。统一响应结构：

```json
{"success": true, "message": "操作成功", "data": {}, "timestamp": "2026-07-25T00:00:00Z"}
```

| 方法 | 路径 | 功能 | 权限 |
| --- | --- | --- | --- |
| POST | `/auth/login` | 登录并获取 JWT | 公开 |
| GET | `/auth/me` | 当前员工 | 登录 |
| GET | `/dashboard` | 人事首页统计 | 登录 |
| GET / POST | `/employees` | 员工花名册 / 新建员工 | 登录 / 人事、管理员 |
| GET | `/employees/{id}` | 员工档案详情 | 登录 |
| PATCH | `/employees/{id}/status` | 更新在职状态 | 人事、管理员 |
| GET | `/organization/departments` | 部门列表 | 登录 |
| GET | `/attendance/today` | 今日考勤 | 登录 |
| GET | `/attendance` | 最近 31 条考勤 | 登录 |
| POST | `/attendance/check-in` | 签到 | 登录 |
| POST | `/attendance/check-out` | 签退 | 登录 |
| GET / POST | `/leaves` | 我的请假 / 提交请假 | 登录 |
| GET | `/leaves/pending` | 待审批请假 | 人事、管理员 |
| POST | `/leaves/{id}/approve` | 同意或拒绝 | 人事、管理员 |
| GET | `/payroll/mine` | 我的薪资 | 登录 |
| GET / POST | `/payroll` | 全员薪资 / 新建薪资 | 人事、管理员 |
| PATCH | `/payroll/{id}/status` | 确认或标记已发放 | 人事、管理员 |
| GET / POST | `/recruitment/jobs` | 职位列表 / 新建职位 | 人事、管理员 |
| POST | `/recruitment/jobs/{id}/close` | 关闭职位 | 人事、管理员 |
| GET / POST | `/recruitment/candidates` | 候选人列表 / 新建候选人 | 人事、管理员 |
| PATCH | `/recruitment/candidates/{id}/stage` | 更新招聘阶段 | 人事、管理员 |

时间使用 ISO 8601 本地时间；薪资月份使用 `YYYY-MM`。接口扩展需保持向后兼容，破坏性变更应发布新的主版本。

## 部门用工风险

`POST /api/workforce/risk-assessment`：仅 HR 与管理员可用，返回部门风险分、等级和人才干预建议。
