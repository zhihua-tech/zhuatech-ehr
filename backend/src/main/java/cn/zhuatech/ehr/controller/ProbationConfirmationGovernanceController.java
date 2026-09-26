/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.controller;

import cn.zhuatech.ehr.common.ApiResponse;
import cn.zhuatech.ehr.service.ProbationConfirmationGovernanceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
@RestController
@RequestMapping("/api/enterprise/ehr")
public class ProbationConfirmationGovernanceController {
    private final ProbationConfirmationGovernanceService service;

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    public ProbationConfirmationGovernanceController(ProbationConfirmationGovernanceService service) {
        this.service = service;
    }

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    @PostMapping("/probation-confirmation")
    public ApiResponse<ProbationConfirmationGovernanceService.Assessment> assess(
            @Valid @RequestBody ProbationConfirmationGovernanceService.Request request) {
        return ApiResponse.ok("试用期转正评估完成", service.assess(request));
    }
}
