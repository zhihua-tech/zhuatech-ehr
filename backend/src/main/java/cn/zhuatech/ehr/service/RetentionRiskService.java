/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.service;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RetentionRiskService {
    public Result assess(Request request) {
        int score = 0;
        if (request.engagementScore() < 50) score += 30;
        else if (request.engagementScore() < 70) score += 15;
        if (request.compensationGapPercent() >= 15) score += 25;
        else if (request.compensationGapPercent() >= 8) score += 12;
        if (request.monthlyOvertimeHours() >= 40) score += 20;
        else if (request.monthlyOvertimeHours() >= 20) score += 10;
        if (request.absenceDaysLast90() >= 5) score += 15;
        if (request.monthsSincePromotion() >= 36) score += 15;
        if (request.tenureMonths() <= 6) score += 10;
        score = Math.min(100, score);
        String level = score >= 60 ? "HIGH" : score >= 30 ? "MEDIUM" : "LOW";
        List<String> actions = new ArrayList<>();
        if (request.engagementScore() < 70) actions.add("安排一对一访谈并记录关键体验问题");
        if (request.compensationGapPercent() >= 8) actions.add("执行岗位薪酬对标与调薪必要性评估");
        if (request.monthlyOvertimeHours() >= 20) actions.add("复核工作负荷并制定加班压降计划");
        if (request.monthsSincePromotion() >= 36) actions.add("补充职业发展路径和晋升评审节点");
        if ("LOW".equals(level)) actions.add("维持常规人才关怀与季度脉搏调查");
        return new Result(request.employeeNo(), score, level, actions);
    }

    public record Request(@NotBlank String employeeNo,
                          @Min(0) @Max(100) int engagementScore,
                          @Min(0) @Max(100) int compensationGapPercent,
                          @Min(0) int monthlyOvertimeHours,
                          @Min(0) int absenceDaysLast90,
                          @Min(0) int monthsSincePromotion,
                          @Min(0) int tenureMonths) {}
    public record Result(String employeeNo, int riskScore, String riskLevel,
                         List<String> retentionActions) {}
}
