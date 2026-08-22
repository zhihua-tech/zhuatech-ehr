/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.controller;
import cn.zhuatech.ehr.common.*;
import cn.zhuatech.ehr.dto.EhrDto.*;
import cn.zhuatech.ehr.model.PayrollRecord;
import cn.zhuatech.ehr.repository.*;
import cn.zhuatech.ehr.service.CurrentUserService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.YearMonth;
import java.util.List;

@RestController @RequestMapping("/api/payroll")
public class PayrollController {
    private final PayrollRepository payroll; private final UserRepository users; private final CurrentUserService current;
    public PayrollController(PayrollRepository payroll, UserRepository users, CurrentUserService current) { this.payroll=payroll; this.users=users; this.current=current; }
    @GetMapping("/mine") public ApiResponse<List<PayrollView>> mine() { return ApiResponse.ok(payroll.findByEmployeeOrderByPayrollMonthDesc(current.get()).stream().map(PayrollView::from).toList()); }
    @GetMapping @PreAuthorize("hasAnyRole('ADMIN','HR')") public ApiResponse<List<PayrollView>> list() { return ApiResponse.ok(payroll.findAllByOrderByPayrollMonthDesc().stream().map(PayrollView::from).toList()); }
    @PostMapping @PreAuthorize("hasAnyRole('ADMIN','HR')") public ApiResponse<PayrollView> create(@Valid @RequestBody PayrollCreateRequest req) {
        var employee=users.findById(req.employeeId()).orElseThrow(() -> new BusinessException("员工不存在"));
        if (payroll.existsByEmployeeAndPayrollMonth(employee, req.payrollMonth())) throw new BusinessException("该员工本月薪资已存在");
        var record=new PayrollRecord(employee, YearMonth.parse(req.payrollMonth()), req.baseSalary(), req.allowance(), req.bonus(), req.deduction());
        return ApiResponse.ok("薪资记录已创建", PayrollView.from(payroll.save(record)));
    }
    @PatchMapping("/{id}/status") @PreAuthorize("hasAnyRole('ADMIN','HR')") public ApiResponse<PayrollView> status(@PathVariable Long id, @Valid @RequestBody PayrollStatusRequest req) {
        var record=payroll.findById(id).orElseThrow(() -> new BusinessException("薪资记录不存在"));
        if ("PAID".equals(req.status())) record.markPaid(); else record.confirm();
        return ApiResponse.ok("薪资状态已更新", PayrollView.from(payroll.save(record)));
    }
}
