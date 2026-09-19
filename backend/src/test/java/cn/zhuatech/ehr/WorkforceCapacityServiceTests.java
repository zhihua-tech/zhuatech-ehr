/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr;

import cn.zhuatech.ehr.service.WorkforceCapacityService;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class WorkforceCapacityServiceTests {
    private final WorkforceCapacityService service = new WorkforceCapacityService();

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void detectsDepartmentCapacityGap() {
        var result = service.analyze(new WorkforceCapacityService.Request("D-OPS", new BigDecimal("1000"), new BigDecimal("760"), new BigDecimal("45"), 10, 2));
        assertThat(result.riskLevel()).isEqualTo("HIGH");
        assertThat(result.capacityGapHours()).isEqualByComparingTo("316.0");
        assertThat(result.usageNotice()).contains("不用于自动化雇佣");
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void keepsBalancedTeamLowRisk() {
        var result = service.analyze(new WorkforceCapacityService.Request("D-FIN", new BigDecimal("600"), new BigDecimal("680"), new BigDecimal("5"), 2, 0));
        assertThat(result.riskLevel()).isEqualTo("LOW");
    }
}
