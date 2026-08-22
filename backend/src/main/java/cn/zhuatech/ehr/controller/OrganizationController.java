/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.controller;
import cn.zhuatech.ehr.common.ApiResponse;
import cn.zhuatech.ehr.dto.EhrDto.*;
import cn.zhuatech.ehr.repository.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/organization")
public class OrganizationController {
    private final DepartmentRepository departments;
    public OrganizationController(DepartmentRepository departments) { this.departments=departments; }
    @GetMapping("/departments") public ApiResponse<List<DepartmentView>> departments() { return ApiResponse.ok(departments.findAllByOrderBySortOrderAsc().stream().map(DepartmentView::from).toList()); }
}
