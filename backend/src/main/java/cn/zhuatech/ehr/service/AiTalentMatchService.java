/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.service;

import cn.zhuatech.ehr.ai.OpenAiCompatibleGateway;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
public class AiTalentMatchService {
    private final OpenAiCompatibleGateway gateway;
    public AiTalentMatchService(OpenAiCompatibleGateway gateway) { this.gateway = gateway; }

    public Result match(Request request) {
        Set<String> candidateSkills = new HashSet<>(request.candidateSkills().stream()
            .map(value -> value.trim().toLowerCase(Locale.ROOT)).toList());
        List<String> missing = request.requiredSkills().stream()
            .filter(skill -> !candidateSkills.contains(skill.trim().toLowerCase(Locale.ROOT))).toList();
        int matched = request.requiredSkills().size() - missing.size();
        int skillScore = BigDecimal.valueOf(matched * 70L)
            .divide(BigDecimal.valueOf(request.requiredSkills().size()), 0, RoundingMode.HALF_UP).intValue();
        int experienceScore = request.requiredExperienceYears() == 0 ? 20
            : Math.min(20, request.candidateExperienceYears() * 20 / request.requiredExperienceYears());
        int certificationScore = !Boolean.TRUE.equals(request.certificationRequired())
            || Boolean.TRUE.equals(request.certificationPresent()) ? 10 : 0;
        int score = skillScore + experienceScore + certificationScore;
        boolean salaryFit = request.expectedSalary().compareTo(request.salaryBudget()) <= 0;
        if (!salaryFit) score = Math.max(0, score - 10);
        String decision = score >= 80 && salaryFit ? "STRONG_MATCH" : score >= 60 ? "INTERVIEW" : "GAP_REVIEW";

        String context = "岗位=%s，匹配分=%d，缺失技能=%s，经验=%d/%d年，薪资匹配=%s"
            .formatted(request.jobTitle(), score, missing, request.candidateExperienceYears(), request.requiredExperienceYears(), salaryFit);
        var enhanced = gateway.complete("你是招聘人岗匹配助手，请只基于岗位要求给出结构化面试问题，避免性别、年龄等歧视因素。", context);
        var metadata = gateway.metadata();
        String local = missing.isEmpty() ? "技能覆盖完整，重点验证实际项目深度。" : "面试重点验证缺失技能：" + missing;
        return new Result(score, decision, missing, salaryFit, enhanced.orElse(local),
            enhanced.isPresent() ? "EXTERNAL_MODEL" : "LOCAL_RULES", metadata.provider(), metadata.model());
    }

    public record Request(@NotBlank String jobTitle, @NotEmpty List<@NotBlank String> requiredSkills,
                          @NotNull List<@NotBlank String> candidateSkills, @Min(0) int requiredExperienceYears,
                          @Min(0) int candidateExperienceYears, @NotNull Boolean certificationRequired,
                          @NotNull Boolean certificationPresent, @DecimalMin("0") BigDecimal salaryBudget,
                          @DecimalMin("0") BigDecimal expectedSalary) {}
    public record Result(int matchScore, String decision, List<String> missingSkills, boolean salaryFit,
                         String interviewAdvice, String aiMode, String provider, String model) {}
}
