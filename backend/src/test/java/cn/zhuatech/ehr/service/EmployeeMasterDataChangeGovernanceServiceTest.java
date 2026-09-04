/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.service;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmployeeMasterDataChangeGovernanceServiceTest {
    private final EmployeeMasterDataChangeGovernanceService service = new EmployeeMasterDataChangeGovernanceService();

    @Test void appliesControlledEmployeeChange() {
        var result = service.assess(request(true, true, true));
        assertEquals(EmployeeMasterDataChangeGovernanceService.Decision.APPLY, result.decision());
        assertTrue(result.blockers().isEmpty());
        assertTrue(result.actions().isEmpty());
    }

    @Test void reviewsChangeWithOperationalActions() {
        var result = service.assess(request(false, false, false));
        assertEquals(EmployeeMasterDataChangeGovernanceService.Decision.REVIEW, result.decision());
        assertEquals(3, result.actions().size());
    }

    @Test void blocksUncontrolledSensitiveChange() {
        var result = service.assess(new EmployeeMasterDataChangeGovernanceService.Request("CHG-003", "EMP-003",
                LocalDate.of(2026, 9, 10), false, false, false, false, true, false, true, false,
                false, false, false, false, false, false, false, true, true, true));
        assertEquals(EmployeeMasterDataChangeGovernanceService.Decision.BLOCKED, result.decision());
        assertEquals(13, result.blockers().size());
    }

    @Test void ignoresUnaffectedCompensationAndBankControls() {
        var request = request(true, true, true);
        var result = service.assess(new EmployeeMasterDataChangeGovernanceService.Request(request.changeRequestId(),
                request.employeeNo(), request.effectiveDate(), true, true, true, true, false, false,
                false, false, true, true, true, true, true, true, true, true, true, true));
        assertEquals(EmployeeMasterDataChangeGovernanceService.Decision.APPLY, result.decision());
    }

    private EmployeeMasterDataChangeGovernanceService.Request request(boolean notice, boolean payroll, boolean access) {
        return new EmployeeMasterDataChangeGovernanceService.Request("CHG-001", "EMP-001",
                LocalDate.of(2026, 9, 10), true, true, true, true, true, true, true, true,
                true, true, true, true, true, true, true, notice, payroll, access);
    }
}
