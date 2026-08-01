/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.ehr.controller;

import cn.zhuatech.ehr.common.ApiResponse;
import cn.zhuatech.ehr.service.WorkforceRiskService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/workforce")
@PreAuthorize("hasAnyRole('ADMIN','HR')")
public class WorkforceInsightController {
    private final WorkforceRiskService service;
    public WorkforceInsightController(WorkforceRiskService service) { this.service = service; }

    @PostMapping("/risk-assessment")
    public ApiResponse<WorkforceRiskService.Result> assess(@Valid @RequestBody WorkforceRiskService.Request request) {
        return ApiResponse.ok(service.assess(request));
    }
}
