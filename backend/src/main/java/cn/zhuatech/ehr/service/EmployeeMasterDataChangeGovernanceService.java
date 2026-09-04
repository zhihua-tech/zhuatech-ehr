/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeMasterDataChangeGovernanceService {
    public Assessment assess(Request request) {
        List<String> blockers = new ArrayList<>();
        List<String> actions = new ArrayList<>();
        if (!request.employmentActive()) blockers.add("员工劳动关系状态不允许直接变更");
        if (!request.changeEvidenceReady()) blockers.add("员工申请或组织决定等变更依据不完整");
        if (!request.identityFieldsVerified()) blockers.add("姓名、证件或联系方式变更未完成身份核验");
        if (!request.organizationPositionApproved()) blockers.add("部门、岗位或汇报关系变更未批准");
        if (request.compensationAffected() && !request.compensationApproved()) blockers.add("薪酬受影响但缺少授权审批");
        if (request.bankAccountAffected() && !request.bankAccountVerified()) blockers.add("工资账户变更未完成员工回拨核验");
        if (!request.taxSocialInsuranceReviewed()) blockers.add("个税、社保和公积金影响未复核");
        if (!request.effectiveDateControlled()) blockers.add("生效日与人事、考勤或薪资周期冲突");
        if (!request.privacyMinimized()) blockers.add("变更范围未遵循个人信息最小必要原则");
        if (!request.downstreamImpactReviewed()) blockers.add("考勤、薪资、权限等下游影响未复核");
        if (!request.hrApproved()) blockers.add("人力资源负责人尚未批准");
        if (!request.makerCheckerSeparated()) blockers.add("变更经办人与复核人未职责分离");
        if (!request.auditReady()) blockers.add("申请、核验、审批和生效证据链不完整");
        if (!request.employeeNoticeReady()) actions.add("准备员工变更确认与申诉入口");
        if (!request.payrollSyncReady()) actions.add("准备薪资、税务和社保系统同步回执");
        if (!request.accessReviewReady()) actions.add("触发岗位变化后的账号权限复核");
        Decision decision = !blockers.isEmpty() ? Decision.BLOCKED : !actions.isEmpty() ? Decision.REVIEW : Decision.APPLY;
        return new Assessment(request.changeRequestId(), request.employeeNo(), request.effectiveDate(),
                decision, List.copyOf(blockers), List.copyOf(actions));
    }

    public record Request(@NotBlank String changeRequestId, @NotBlank String employeeNo,
                          @NotNull LocalDate effectiveDate, boolean employmentActive,
                          boolean changeEvidenceReady, boolean identityFieldsVerified,
                          boolean organizationPositionApproved, boolean compensationAffected,
                          boolean compensationApproved, boolean bankAccountAffected,
                          boolean bankAccountVerified, boolean taxSocialInsuranceReviewed,
                          boolean effectiveDateControlled, boolean privacyMinimized,
                          boolean downstreamImpactReviewed, boolean hrApproved,
                          boolean makerCheckerSeparated, boolean auditReady,
                          boolean employeeNoticeReady, boolean payrollSyncReady,
                          boolean accessReviewReady) {}
    public record Assessment(String changeRequestId, String employeeNo, LocalDate effectiveDate,
                             Decision decision, List<String> blockers, List<String> actions) {}
    public enum Decision { APPLY, REVIEW, BLOCKED }
}
