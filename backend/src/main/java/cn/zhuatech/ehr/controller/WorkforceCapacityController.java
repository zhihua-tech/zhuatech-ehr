/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.ehr.controller;

import cn.zhuatech.ehr.common.ApiResponse;
import cn.zhuatech.ehr.service.WorkforceCapacityService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ehr/insights")
public class WorkforceCapacityController {
    private final WorkforceCapacityService service;
    public WorkforceCapacityController(WorkforceCapacityService service) { this.service = service; }

    @PostMapping("/workforce-capacity")
    public ApiResponse<WorkforceCapacityService.Result> analyze(@Valid @RequestBody WorkforceCapacityService.Request request) {
        return ApiResponse.ok(service.analyze(request));
    }
}
