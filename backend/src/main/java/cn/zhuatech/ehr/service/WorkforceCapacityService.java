/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.service;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class WorkforceCapacityService {
    public Result analyze(Request request) {
        BigDecimal effectiveHours = request.availableHours().multiply(
            BigDecimal.ONE.subtract(BigDecimal.valueOf(request.absenceRatePercent()).divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP)));
        BigDecimal gapHours = request.requiredHours().subtract(effectiveHours).max(BigDecimal.ZERO).setScale(1, RoundingMode.HALF_UP);
        BigDecimal loadRate = request.requiredHours().divide(effectiveHours.max(new BigDecimal("0.01")), 4, RoundingMode.HALF_UP);
        int score = Math.min(50, loadRate.subtract(BigDecimal.ONE).max(BigDecimal.ZERO).multiply(BigDecimal.valueOf(100)).intValue());
        score += Math.min(20, request.overtimeHours().divide(new BigDecimal("5"), 0, RoundingMode.DOWN).intValue() * 5);
        score += Math.min(20, request.criticalRoleVacancies() * 10);
        score += Math.min(10, request.absenceRatePercent());
        String riskLevel = score >= 50 ? "HIGH" : score >= 25 ? "MEDIUM" : "LOW";

        List<String> actions = new ArrayList<>();
        if (gapHours.signum() > 0) actions.add("调整排班或补充临时产能以覆盖需求缺口");
        if (request.overtimeHours().compareTo(new BigDecimal("20")) > 0) actions.add("复核持续加班岗位并安排负荷均衡");
        if (request.criticalRoleVacancies() > 0) actions.add("优先补充关键岗位并建立内部备岗计划");
        if (request.absenceRatePercent() >= 8) actions.add("分析缺勤原因并完善关键岗位替补安排");
        if (actions.isEmpty()) actions.add("保持现有人员配置并按周滚动预测需求");
        return new Result(request.departmentCode(), effectiveHours.setScale(1, RoundingMode.HALF_UP), gapHours, loadRate, riskLevel, actions,
            "仅用于团队产能规划，不用于自动化雇佣或人员淘汰决策");
    }

    public record Request(@NotBlank String departmentCode,
                          @DecimalMin("0.01") BigDecimal requiredHours,
                          @DecimalMin("0") BigDecimal availableHours,
                          @DecimalMin("0") BigDecimal overtimeHours,
                          @Min(0) @Max(100) int absenceRatePercent,
                          @Min(0) int criticalRoleVacancies) {}
    public record Result(String departmentCode, BigDecimal effectiveHours, BigDecimal capacityGapHours,
                         BigDecimal loadRate, String riskLevel, List<String> actions, String usageNotice) {}
}
