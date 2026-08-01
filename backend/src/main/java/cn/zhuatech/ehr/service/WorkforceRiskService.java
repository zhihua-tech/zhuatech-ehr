/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.ehr.service;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkforceRiskService {
    public Result assess(Request request) {
        double vacancyRatio = (double) request.openPositions() / request.headcount();
        int score = Math.min(100, (int) Math.round(request.overtimeRate() * 30
            + request.absenceRate() * 25 + Math.min(1, vacancyRatio) * 25
            + (request.criticalSkillGap() ? 25 : 0)));
        String level = score >= 70 ? "CRITICAL" : score >= 40 ? "HIGH" : score >= 20 ? "WATCH" : "STABLE";
        List<String> actions = new ArrayList<>();
        if (request.criticalSkillGap()) actions.add("启动关键岗位继任与技能补位计划");
        if (request.overtimeRate() >= .3) actions.add("复核排班和工作负荷，降低持续加班风险");
        if (vacancyRatio >= .08) actions.add("提升紧缺岗位招聘优先级");
        if (actions.isEmpty()) actions.add("保持常规人才盘点节奏");
        return new Result(request.department(), score, level, score >= 40, actions);
    }

    public record Request(@NotBlank String department, @Positive int headcount,
                          @Min(0) int openPositions,
                          @DecimalMin("0") @DecimalMax("1") double overtimeRate,
                          @DecimalMin("0") @DecimalMax("1") double absenceRate,
                          boolean criticalSkillGap) {}
    public record Result(String department, int riskScore, String level,
                         boolean hrReview, List<String> actions) {}
}
