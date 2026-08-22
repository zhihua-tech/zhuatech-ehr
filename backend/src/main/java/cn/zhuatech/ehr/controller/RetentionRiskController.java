/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.controller;

import cn.zhuatech.ehr.common.ApiResponse;
import cn.zhuatech.ehr.service.RetentionRiskService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ehr/insights")
public class RetentionRiskController {
    private final RetentionRiskService service;
    public RetentionRiskController(RetentionRiskService service) { this.service = service; }

    @PostMapping("/retention-risk")
    public ApiResponse<RetentionRiskService.Result> assess(@Valid @RequestBody RetentionRiskService.Request request) {
        return ApiResponse.ok(service.assess(request));
    }
}
