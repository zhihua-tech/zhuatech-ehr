/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.controller;
import cn.zhuatech.ehr.common.ApiResponse;
import cn.zhuatech.ehr.dto.EhrDto.*;
import cn.zhuatech.ehr.repository.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController @RequestMapping("/api/organization")
public class OrganizationController {
    private final DepartmentRepository departments;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public OrganizationController(DepartmentRepository departments) { this.departments=departments; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @GetMapping("/departments") public ApiResponse<List<DepartmentView>> departments() { return ApiResponse.ok(departments.findAllByOrderBySortOrderAsc().stream().map(DepartmentView::from).toList()); }
}
