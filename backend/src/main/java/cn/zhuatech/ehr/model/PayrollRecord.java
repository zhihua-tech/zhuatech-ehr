/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.YearMonth;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Entity
@Table(name = "ehr_payroll", uniqueConstraints = @UniqueConstraint(columnNames = {"employee_id", "payroll_month"}))
public class PayrollRecord extends BaseEntity {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public enum Status { DRAFT, CONFIRMED, PAID }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id")
    private UserAccount employee;
    @Column(name = "payroll_month", nullable = false, length = 7)
    private String payrollMonth;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal baseSalary;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal allowance = BigDecimal.ZERO;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal bonus = BigDecimal.ZERO;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal deduction = BigDecimal.ZERO;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal netSalary;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status = Status.DRAFT;

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    protected PayrollRecord() {}

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public PayrollRecord(UserAccount employee, YearMonth month, BigDecimal baseSalary, BigDecimal allowance, BigDecimal bonus, BigDecimal deduction) {
        this.employee = employee;
        this.payrollMonth = month.toString();
        this.baseSalary = baseSalary;
        this.allowance = allowance == null ? BigDecimal.ZERO : allowance;
        this.bonus = bonus == null ? BigDecimal.ZERO : bonus;
        this.deduction = deduction == null ? BigDecimal.ZERO : deduction;
        recalculate();
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public void confirm() { this.status = Status.CONFIRMED; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public void markPaid() { this.status = Status.PAID; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private void recalculate() { this.netSalary = baseSalary.add(allowance).add(bonus).subtract(deduction); }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public UserAccount getEmployee() { return employee; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getPayrollMonth() { return payrollMonth; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public BigDecimal getBaseSalary() { return baseSalary; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public BigDecimal getAllowance() { return allowance; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public BigDecimal getBonus() { return bonus; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public BigDecimal getDeduction() { return deduction; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public BigDecimal getNetSalary() { return netSalary; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Status getStatus() { return status; }
}
