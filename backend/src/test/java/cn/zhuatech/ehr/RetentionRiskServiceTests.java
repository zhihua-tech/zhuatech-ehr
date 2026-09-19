/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr;

import cn.zhuatech.ehr.service.RetentionRiskService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class RetentionRiskServiceTests {
    private final RetentionRiskService service = new RetentionRiskService();

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void identifiesHighRetentionRisk() {
        var result = service.assess(new RetentionRiskService.Request("E-2001", 42, 18, 45, 6, 40, 30));
        assertThat(result.riskLevel()).isEqualTo("HIGH");
        assertThat(result.retentionActions()).hasSizeGreaterThanOrEqualTo(4);
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void keepsStableEmployeeLowRisk() {
        var result = service.assess(new RetentionRiskService.Request("E-2002", 84, 2, 6, 0, 12, 48));
        assertThat(result.riskLevel()).isEqualTo("LOW");
        assertThat(result.riskScore()).isZero();
    }
}
