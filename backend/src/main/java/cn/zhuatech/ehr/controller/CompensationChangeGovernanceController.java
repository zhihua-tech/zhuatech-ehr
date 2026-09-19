/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.controller;
import cn.zhuatech.ehr.common.ApiResponse;import cn.zhuatech.ehr.service.CompensationChangeGovernanceService;import jakarta.validation.Valid;import org.springframework.web.bind.annotation.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController @RequestMapping("/api/enterprise/ehr")public class CompensationChangeGovernanceController{
 private final CompensationChangeGovernanceService service;/**
                                                            * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                            */
public CompensationChangeGovernanceController(CompensationChangeGovernanceService service){this.service=service;}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 @PostMapping("/compensation-change")public ApiResponse<CompensationChangeGovernanceService.Assessment> assess(@Valid @RequestBody CompensationChangeGovernanceService.Request request){return ApiResponse.ok("薪酬变更评估完成",service.assess(request));}
}
