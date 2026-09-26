/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.service;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 员工试用期转正前校验绩效、考勤、培训、编制、绩薪与合规证据。
 *
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service
public class ProbationConfirmationGovernanceService {
    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    public Assessment assess(Request request) {
        List<String> blockers = new ArrayList<>();
        List<String> actions = new ArrayList<>();
        if (!request.employeeActive()) blockers.add("员工已离职、冻结或不在有效劳动关系内");
        if (request.daysUntilProbationEnd() < 0 && !request.overdueExceptionApproved()) blockers.add("试用期已过期且未获得逾期处理批准");
        if (!request.roleHeadcountApproved()) blockers.add("转正岗位缺少有效编制");
        if (request.disciplinaryCaseOpen()) blockers.add("员工存在未结案的纪律或合规调查");
        if (request.recommendation() == Recommendation.CONFIRM && request.managerRating() < 3) {
            blockers.add("绩效评分不支持直接转正");
        }
        if (request.recommendation() == Recommendation.CONFIRM && request.completedGoalPercent() < 80) {
            actions.add("补充未完成试用期目标的改进计划");
        }
        if (request.unexcusedAbsenceDays() > 0) actions.add("复核无故缺勤与考勤例外记录");
        if (!request.mandatoryTrainingCompleted()) actions.add("完成信息安全、职业健康等必修课程");
        if (!request.compensationAligned()) actions.add("确认转正后薪酬、职级与生效日期");
        if (!request.employeeFeedbackRecorded()) actions.add("记录员工述职与反馈");
        if (!request.auditEvidenceAttached()) actions.add("归档目标、评价、审批和通知证据");
        Decision decision;
        if (!blockers.isEmpty()) decision = Decision.BLOCKED;
        else if (!actions.isEmpty()) decision = Decision.REVIEW;
        else decision = switch (request.recommendation()) {
            case CONFIRM -> Decision.CONFIRM;
            case EXTEND -> Decision.EXTEND;
            case TERMINATE -> Decision.TERMINATE;
        };
        String route = request.recommendation() == Recommendation.TERMINATE
                ? "直属主管→HRBP→用工负责人→法务"
                : "直属主管→HRBP→部门负责人";
        return new Assessment(request.reviewNo(), request.employeeNo(), decision, route,
                List.copyOf(blockers), List.copyOf(actions));
    }

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    public record Request(@NotBlank String reviewNo, @NotBlank String employeeNo,
                          @Min(-365) @Max(365) int daysUntilProbationEnd,
                          @Min(0) @Max(100) int completedGoalPercent,
                          @Min(1) @Max(5) int managerRating, @Min(0) int unexcusedAbsenceDays,
                          @NotNull Recommendation recommendation, boolean employeeActive,
                          boolean overdueExceptionApproved, boolean roleHeadcountApproved,
                          boolean disciplinaryCaseOpen, boolean mandatoryTrainingCompleted,
                          boolean compensationAligned, boolean employeeFeedbackRecorded,
                          boolean auditEvidenceAttached) {}

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    public record Assessment(String reviewNo, String employeeNo, Decision decision,
                             String approvalRoute, List<String> blockers, List<String> actions) {}

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    public enum Recommendation { CONFIRM, EXTEND, TERMINATE }

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    public enum Decision { CONFIRM, EXTEND, TERMINATE, REVIEW, BLOCKED }
}
