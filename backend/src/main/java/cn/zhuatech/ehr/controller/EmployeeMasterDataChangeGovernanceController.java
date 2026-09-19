/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.controller;

import cn.zhuatech.ehr.common.ApiResponse;
import cn.zhuatech.ehr.service.EmployeeMasterDataChangeGovernanceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController
@RequestMapping("/api/enterprise/ehr")
public class EmployeeMasterDataChangeGovernanceController {
    private final EmployeeMasterDataChangeGovernanceService service;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public EmployeeMasterDataChangeGovernanceController(EmployeeMasterDataChangeGovernanceService service) { this.service = service; }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/employee-master-data-change")
    public ApiResponse<EmployeeMasterDataChangeGovernanceService.Assessment> assess(
            @Valid @RequestBody EmployeeMasterDataChangeGovernanceService.Request request) {
        return ApiResponse.ok("员工主数据变更评估完成", service.assess(request));
    }
}
