# 企业级员工离职清算治理

离职关闭前统一检查工作日、普通/特权访问、资产、薪资、费用、知识交接和业务/人力双重确认。

`POST /api/enterprise/ehr/employee-exit-clearance` 返回 `CLOSE / REVIEW / BLOCK` 决策。生产使用应连接 IAM、资产、薪资、费用和电子签署证据。
