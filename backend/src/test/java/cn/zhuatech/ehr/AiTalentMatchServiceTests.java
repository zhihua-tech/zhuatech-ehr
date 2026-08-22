/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr;
import cn.zhuatech.ehr.ai.OpenAiCompatibleGateway;
import cn.zhuatech.ehr.service.AiTalentMatchService;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
class AiTalentMatchServiceTests {
    private final AiTalentMatchService service = new AiTalentMatchService(
        new OpenAiCompatibleGateway("local", "https://api.deepseek.com", "deepseek-chat", ""));
    @Test void recommendsStrongMatch() {
        var result = service.match(new AiTalentMatchService.Request("Java 开发工程师", List.of("Java", "MySQL", "Spring"),
            List.of("java", "Spring", "MySQL", "Vue"), 3, 5, true, true,
            new BigDecimal("25000"), new BigDecimal("23000")));
        assertThat(result.decision()).isEqualTo("STRONG_MATCH");
        assertThat(result.missingSkills()).isEmpty();
    }
}
