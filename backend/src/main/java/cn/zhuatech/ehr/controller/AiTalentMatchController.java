/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.controller;
import cn.zhuatech.ehr.common.ApiResponse;
import cn.zhuatech.ehr.service.AiTalentMatchService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/ehr/ai")
public class AiTalentMatchController {
    private final AiTalentMatchService service;
    public AiTalentMatchController(AiTalentMatchService service) { this.service = service; }
    @PostMapping("/talent-match")
    public ApiResponse<AiTalentMatchService.Result> match(@Valid @RequestBody AiTalentMatchService.Request request) {
        return ApiResponse.ok(service.match(request));
    }
}
