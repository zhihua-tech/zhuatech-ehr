/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.service;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class EmployeeExitClearanceGovernanceServiceTest {
    private final EmployeeExitClearanceGovernanceService service = new EmployeeExitClearanceGovernanceService();
    @Test void closesCompletedExit() {
        var result = service.assess(new EmployeeExitClearanceGovernanceService.Request(
                "EMP-001", true, true, true, true, true, true, true, true, true, true));
        assertThat(result.decision()).isEqualTo(EmployeeExitClearanceGovernanceService.Decision.CLOSE);
        assertThat(result.blockers()).isEmpty();
    }
    @Test void blocksUncontrolledPrivilegedExit() {
        var result = service.assess(new EmployeeExitClearanceGovernanceService.Request(
                "EMP-002", false, false, true, false, false, false, false, false, false, false));
        assertThat(result.decision()).isEqualTo(EmployeeExitClearanceGovernanceService.Decision.BLOCK);
        assertThat(result.blockers()).hasSize(4);
        assertThat(result.actions()).hasSize(4);
    }
}
