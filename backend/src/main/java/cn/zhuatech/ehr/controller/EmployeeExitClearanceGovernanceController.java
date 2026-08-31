/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.controller;

import cn.zhuatech.ehr.common.ApiResponse;
import cn.zhuatech.ehr.service.EmployeeExitClearanceGovernanceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enterprise/ehr")
public class EmployeeExitClearanceGovernanceController {
    private final EmployeeExitClearanceGovernanceService service;
    public EmployeeExitClearanceGovernanceController(EmployeeExitClearanceGovernanceService service) { this.service = service; }

    @PostMapping("/employee-exit-clearance")
    public ApiResponse<EmployeeExitClearanceGovernanceService.Assessment> assess(
            @Valid @RequestBody EmployeeExitClearanceGovernanceService.Request request) {
        return ApiResponse.ok("员工离职清算评估完成", service.assess(request));
    }
}
