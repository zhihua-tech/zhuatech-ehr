/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.controller;
import cn.zhuatech.ehr.common.ApiResponse;
import cn.zhuatech.ehr.service.AiTalentMatchService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController @RequestMapping("/api/ehr/ai")
public class AiTalentMatchController {
    private final AiTalentMatchService service;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public AiTalentMatchController(AiTalentMatchService service) { this.service = service; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/talent-match")
    public ApiResponse<AiTalentMatchService.Result> match(@Valid @RequestBody AiTalentMatchService.Request request) {
        return ApiResponse.ok(service.match(request));
    }
}
