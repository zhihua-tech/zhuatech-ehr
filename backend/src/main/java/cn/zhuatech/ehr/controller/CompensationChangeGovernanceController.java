/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.controller;
import cn.zhuatech.ehr.common.ApiResponse;import cn.zhuatech.ehr.service.CompensationChangeGovernanceService;import jakarta.validation.Valid;import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/enterprise/ehr")public class CompensationChangeGovernanceController{
 private final CompensationChangeGovernanceService service;public CompensationChangeGovernanceController(CompensationChangeGovernanceService service){this.service=service;}
 @PostMapping("/compensation-change")public ApiResponse<CompensationChangeGovernanceService.Assessment> assess(@Valid @RequestBody CompensationChangeGovernanceService.Request request){return ApiResponse.ok("薪酬变更评估完成",service.assess(request));}
}
