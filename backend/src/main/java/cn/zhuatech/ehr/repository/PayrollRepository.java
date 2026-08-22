/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.repository;
import cn.zhuatech.ehr.model.*;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface PayrollRepository extends JpaRepository<PayrollRecord, Long> {
    @EntityGraph(attributePaths = "employee")
    List<PayrollRecord> findByEmployeeOrderByPayrollMonthDesc(UserAccount employee);
    @EntityGraph(attributePaths = "employee")
    List<PayrollRecord> findAllByOrderByPayrollMonthDesc();
    @EntityGraph(attributePaths = "employee")
    Optional<PayrollRecord> findById(Long id);
    boolean existsByEmployeeAndPayrollMonth(UserAccount employee, String payrollMonth);
}
