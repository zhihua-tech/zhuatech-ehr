/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
class ProbationConfirmationGovernanceServiceTest {
    private final ProbationConfirmationGovernanceService service = new ProbationConfirmationGovernanceService();

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    @Test
    void confirmsFullyQualifiedEmployee() {
        var result = service.assess(request(90, 4, 0, true));
        assertThat(result.decision()).isEqualTo(ProbationConfirmationGovernanceService.Decision.CONFIRM);
    }

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    @Test
    void reviewsIncompleteGoalsAndTraining() {
        var result = service.assess(request(60, 4, 1, false));
        assertThat(result.decision()).isEqualTo(ProbationConfirmationGovernanceService.Decision.REVIEW);
        assertThat(result.actions()).hasSizeGreaterThanOrEqualTo(5);
    }

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    @Test
    void blocksConfirmationWithoutHeadcountAndAdequateRating() {
        var request = new ProbationConfirmationGovernanceService.Request("PR-101", "E-101", 5,
                90, 2, 0, ProbationConfirmationGovernanceService.Recommendation.CONFIRM,
                true, false, false, false, true, true, true, true);
        var result = service.assess(request);
        assertThat(result.decision()).isEqualTo(ProbationConfirmationGovernanceService.Decision.BLOCKED);
        assertThat(result.blockers()).hasSize(2);
    }

    private ProbationConfirmationGovernanceService.Request request(int goals, int rating,
                                                                    int absence, boolean prepared) {
        return new ProbationConfirmationGovernanceService.Request("PR-100", "E-100", 10,
                goals, rating, absence, ProbationConfirmationGovernanceService.Recommendation.CONFIRM,
                true, false, true, false, prepared, prepared, prepared, prepared);
    }
}
