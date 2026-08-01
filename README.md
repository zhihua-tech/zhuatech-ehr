# ZhuaTech EHR — 知华科技 EHR 社区源码版

[![License](https://img.shields.io/badge/license-Community_Source_Noncommercial-orange.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-21-ED8B00.svg)](backend/pom.xml)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0-6DB33F.svg)](backend/pom.xml)
[![Vue](https://img.shields.io/badge/Vue-3-42b883.svg)](frontend/package.json)

ZhuaTech EHR（知华 EHR）是由 **[知华科技（上海如静知华信息科技有限公司）](https://www.zhuatech.cn/)** 提供源码的电子人力资源管理系统。项目采用 Java、Spring Boot、Vue 3、移动 H5 和 MySQL 构建，覆盖员工档案、组织架构、考勤、请假、薪资与招聘等基础 EHR / HRM 能力，可用于个人学习 Java 人力资源系统、Vue EHR、员工管理和招聘管理系统的设计与实现。

> [!IMPORTANT]
> **本工程仅允许个人用于非商业性的学习、研究与技术交流，不得用于任何商业用途。** 企业内部使用、生产部署、SaaS、项目交付、咨询实施、二次开发后销售或其他直接、间接商业使用，均须事先取得上海如静知华信息科技有限公司的书面商业授权。完整条款请阅读 [LICENSE](LICENSE)。

> 本项目为“源码开放/社区源码版”，因包含非商业限制，不属于 OSI 定义的开源软件。

> 官方网站：[https://www.zhuatech.cn/](https://www.zhuatech.cn/) · 商业授权、深度开发、私有化部署与定制功能，请联系知华科技。

## 功能特性

- 员工花名册：工号、部门、岗位、入职日期、联系方式与在职状态
- 组织架构：部门数据、员工归属和组织人员统计
- 考勤管理：签到、签退、迟到判断与最近考勤记录
- 假勤审批：年假、事假、病假申请，员工进度查询，人事/管理员审批
- 薪资管理：基本工资、津贴、奖金、扣款、实发工资和发放状态
- 招聘管理：招聘职位、需求人数、候选人来源和招聘阶段流转
- 人事工作台：在职员工、待审批、在招职位和面试候选人统计
- 角色权限：管理员、人事、员工三级角色及敏感薪资数据隔离
- 移动 H5：面向手机端的员工服务、人事工作台和卡片化操作体验
- 工程能力：JWT、Flyway、MySQL、Docker Compose、Nginx 和 CI

## 技术架构

| 层级 | 技术 |
| --- | --- |
| H5 前端 | Vue 3、Vite、Vant、Pinia、Vue Router、Axios |
| Java 后端 | Java 21、Spring Boot、Spring Security、Spring Data JPA、Flyway |
| 数据库 | MySQL 8.4（测试环境可使用 H2） |
| 部署 | Docker、Docker Compose、Nginx |

后端统一使用 `cn.zhuatech.ehr` 根包名，前后端通过 REST API 解耦。详细设计见 [架构文档](docs/ARCHITECTURE.md) 和 [API 文档](docs/API.md)。

## 5 分钟启动

前置条件：Docker Desktop / Docker Engine 24+ 与 Docker Compose v2。以下方式仅供个人非商业学习环境使用；商业或生产部署前须取得书面授权。

```bash
cp .env.example .env
docker compose up --build -d
```

浏览器访问：<http://localhost:8088>

| 类型 | 账号 | 密码 |
| --- | --- | --- |
| 员工体验 | `demo` | `Demo@2026` |
| 人事体验 | `hr` | `Demo@2026` |
| 管理员 | `admin` | `ZhuaTech@2026` |

首次启动会创建示例部门、员工、考勤、请假、薪资、招聘职位和候选人数据，方便体验完整 EHR 流程。

> 演示密码和示例数据仅用于个人本地学习。部署到公网前必须修改初始化账号、数据库密码和 `JWT_SECRET`，并清除示例数据。

停止服务：

```bash
docker compose down
```

删除数据库卷会永久清除数据，仅在明确需要重置演示数据时执行：`docker compose down -v`。

## 本地开发

后端需要 JDK 21、Maven 3.9 和 MySQL 8：

```bash
cd backend
mvn spring-boot:run
```

前端需要 Node.js 24 与 npm 11：

```bash
cd frontend
npm install
npm run dev
```

默认开发地址为 <http://localhost:5173>，Vite 会将 `/api` 代理到 <http://localhost:8080>。环境变量说明见 [.env.example](.env.example)。

## 项目结构

```text
zhuatech-ehr/
├── backend/        # cn.zhuatech.ehr Java 后端
├── frontend/       # Vue 3 移动端 H5
├── deploy/         # 部署说明
├── docs/           # 架构与 REST API 文档
├── compose.yaml    # MySQL、后端与前端编排
└── README.md
```

## 路线图

- [ ] 入职、转正、调岗、离职等员工全生命周期流程
- [ ] 排班、加班、出差、调休与年假余额
- [ ] 社保、公积金、个税和工资条通知
- [ ] 绩效目标、考核周期、评分校准与人才盘点
- [ ] 培训课程、学习计划、证书与发展路径
- [ ] PC 人事管理后台、操作审计和细粒度数据权限
- [ ] 企业微信、钉钉、邮件、短信和电子签集成
- [ ] 多租户、开放 API、Webhook 与可配置审批流

欢迎按 [贡献指南](CONTRIBUTING.md) 提交 Issue 和 Pull Request。安全问题请不要公开披露，处理方式见 [安全策略](SECURITY.md)。

## 使用许可与商业授权

本项目版权归 **上海如静知华信息科技有限公司** 所有，并按照 [ZhuaTech EHR 社区源码许可协议](LICENSE)提供源码：

- 允许自然人用于个人、非商业性的学习、研究、实验和技术交流。
- 允许为上述目的在个人设备上运行和修改，但必须保留许可证、版权与 NOTICE 声明。
- **未经我方事先书面授权，不得用于任何商业用途。** 企业内部使用、生产环境部署、SaaS、托管、项目交付、商业集成、收费或免费商业产品、咨询实施以及可产生直接或间接商业利益的使用，均属于商业使用。
- 商业使用、私有化部署或基于本工程进行商业二次开发，须联系知华科技取得书面商业授权。
- “知华科技”“ZhuaTech”相关名称及标识不因源码可见而授予商标许可。

如果你需要商业授权、EHR 系统深度开发、人事流程定制、私有化部署、系统集成或技术支持，请访问 **[知华科技官网](https://www.zhuatech.cn/)** 联系上海如静知华信息科技有限公司。

### 微信咨询

扫描下方任一二维码添加微信，可咨询 ZhuaTech EHR 部署、二次开发、功能定制及企业数字化解决方案。

<p align="center">
  <img src="docs/images/zhuatech-wechat-consulting.png" width="280" alt="知华科技微信咨询二维码一｜上海如静知华信息科技有限公司" />
  &nbsp;&nbsp;
  <img src="docs/images/zhuatech-wechat-consulting-2.png" width="280" alt="知华科技微信咨询二维码二｜上海如静知华信息科技有限公司" />
</p>

<p align="center">任选一个二维码扫码添加微信，联系知华科技</p>

### 相关关键词

EHR 社区源码、Java EHR 学习项目、Spring Boot EHR、Vue EHR、H5 人力资源管理、员工管理系统、HRM 系统、电子人事系统、考勤管理、请假审批、薪资管理、招聘管理、EHR 商业授权、EHR 私有化部署。

---

Copyright © 2026 上海如静知华信息科技有限公司（知华科技）

## 人力风险不再只看离职率

新增加的部门风险评估会综合编制缺口、加班率、缺勤率和关键技能断层，输出稳定、观察、高风险或紧急等级，并生成对应的人才补位动作。接口仅对 HR 与管理员角色开放：`POST /api/workforce/risk-assessment`。

示例场景和权限边界均由 MockMvc 集成测试验证，可直接作为人才驾驶舱的数据服务使用。
