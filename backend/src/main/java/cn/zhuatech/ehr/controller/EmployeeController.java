/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.ehr.controller;
import cn.zhuatech.ehr.common.*;
import cn.zhuatech.ehr.dto.EhrDto.*;
import cn.zhuatech.ehr.model.UserAccount;
import cn.zhuatech.ehr.repository.*;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/employees")
public class EmployeeController {
    private final UserRepository users; private final DepartmentRepository departments; private final PasswordEncoder encoder;
    public EmployeeController(UserRepository users, DepartmentRepository departments, PasswordEncoder encoder) { this.users=users; this.departments=departments; this.encoder=encoder; }
    @GetMapping public ApiResponse<List<EmployeeView>> list() { return ApiResponse.ok(users.findAllByOrderByEmployeeNoAsc().stream().map(EmployeeView::from).toList()); }
    @GetMapping("/{id}") public ApiResponse<EmployeeView> detail(@PathVariable Long id) { return ApiResponse.ok(EmployeeView.from(users.findById(id).orElseThrow(() -> new BusinessException("员工不存在")))); }
    @PostMapping @PreAuthorize("hasAnyRole('ADMIN','HR')") public ApiResponse<EmployeeView> create(@Valid @RequestBody EmployeeCreateRequest req) {
        if (users.existsByEmployeeNo(req.employeeNo())) throw new BusinessException("员工编号已存在");
        if (users.existsByUsername(req.username())) throw new BusinessException("登录账号已存在");
        var department=departments.findById(req.departmentId()).orElseThrow(() -> new BusinessException("部门不存在"));
        var user=new UserAccount(req.employeeNo(), req.username(), encoder.encode(req.password()), req.fullName(), UserAccount.Role.valueOf(req.role()), department, req.hireDate());
        user.updateProfile(req.email(), req.phone(), req.position(), req.gender(), req.birthDate());
        return ApiResponse.ok("员工档案已创建", EmployeeView.from(users.save(user)));
    }
    @PatchMapping("/{id}/status") @PreAuthorize("hasAnyRole('ADMIN','HR')") public ApiResponse<EmployeeView> status(@PathVariable Long id, @Valid @RequestBody EmployeeStatusRequest req) {
        var user=users.findById(id).orElseThrow(() -> new BusinessException("员工不存在")); user.changeEmploymentStatus(UserAccount.EmploymentStatus.valueOf(req.status()));
        return ApiResponse.ok("员工状态已更新", EmployeeView.from(users.save(user)));
    }
}
