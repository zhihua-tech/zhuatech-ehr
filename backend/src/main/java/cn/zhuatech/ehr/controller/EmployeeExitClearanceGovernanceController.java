/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.controller;

import cn.zhuatech.ehr.common.ApiResponse;
import cn.zhuatech.ehr.service.EmployeeExitClearanceGovernanceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController
@RequestMapping("/api/enterprise/ehr")
public class EmployeeExitClearanceGovernanceController {
    private final EmployeeExitClearanceGovernanceService service;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public EmployeeExitClearanceGovernanceController(EmployeeExitClearanceGovernanceService service) { this.service = service; }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/employee-exit-clearance")
    public ApiResponse<EmployeeExitClearanceGovernanceService.Assessment> assess(
            @Valid @RequestBody EmployeeExitClearanceGovernanceService.Request request) {
        return ApiResponse.ok("员工离职清算评估完成", service.assess(request));
    }
}
