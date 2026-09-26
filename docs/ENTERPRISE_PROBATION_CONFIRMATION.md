# 试用期转正治理

`POST /api/enterprise/ehr/probation-confirmation` 用于转正、延长试用或终止建议的一致性检查。

- 检查试用期截止日、编制、绩效评分、目标完成度和未结纪律案件。
- 覆盖考勤异常、必修培训、薪酬职级对齐和员工反馈。
- 按建议类型生成标准审批路由，终止场景自动纳入法务。

返回 `CONFIRM / EXTEND / TERMINATE / REVIEW / BLOCKED`。
