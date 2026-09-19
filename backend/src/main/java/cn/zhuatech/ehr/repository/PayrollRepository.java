/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.repository;
import cn.zhuatech.ehr.model.*;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
public interface PayrollRepository extends JpaRepository<PayrollRecord, Long> {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @EntityGraph(attributePaths = "employee")
    List<PayrollRecord> findByEmployeeOrderByPayrollMonthDesc(UserAccount employee);
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @EntityGraph(attributePaths = "employee")
    List<PayrollRecord> findAllByOrderByPayrollMonthDesc();
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @EntityGraph(attributePaths = "employee")
    Optional<PayrollRecord> findById(Long id);
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    boolean existsByEmployeeAndPayrollMonth(UserAccount employee, String payrollMonth);
}
