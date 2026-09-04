/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.controller;

import cn.zhuatech.ehr.common.ApiResponse;
import cn.zhuatech.ehr.service.EmployeeMasterDataChangeGovernanceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enterprise/ehr")
public class EmployeeMasterDataChangeGovernanceController {
    private final EmployeeMasterDataChangeGovernanceService service;
    public EmployeeMasterDataChangeGovernanceController(EmployeeMasterDataChangeGovernanceService service) { this.service = service; }

    @PostMapping("/employee-master-data-change")
    public ApiResponse<EmployeeMasterDataChangeGovernanceService.Assessment> assess(
            @Valid @RequestBody EmployeeMasterDataChangeGovernanceService.Request request) {
        return ApiResponse.ok("员工主数据变更评估完成", service.assess(request));
    }
}
