/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Entity @Table(name = "ehr_employee")
public class UserAccount extends BaseEntity {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public enum Role { ADMIN, HR, EMPLOYEE }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public enum EmploymentStatus { PROBATION, ACTIVE, LEAVE, TERMINATED }
    @Column(nullable = false, unique = true, length = 32) private String employeeNo;
    @Column(nullable = false, unique = true, length = 32) private String username;
    @Column(nullable = false) private String password;
    @Column(nullable = false, length = 50) private String fullName;
    @Column(length = 100) private String email;
    @Column(length = 20) private String phone;
    @Column(length = 50) private String position;
    @Column(length = 10) private String gender;
    private LocalDate birthDate;
    @Column(nullable = false) private LocalDate hireDate;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private EmploymentStatus employmentStatus = EmploymentStatus.ACTIVE;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private Role role;
    @Column(nullable = false) private boolean enabled = true;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "department_id") private Department department;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    protected UserAccount() {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public UserAccount(String employeeNo, String username, String password, String fullName, Role role, Department department, LocalDate hireDate) {
        this.employeeNo = employeeNo; this.username = username; this.password = password; this.fullName = fullName; this.role = role; this.department = department; this.hireDate = hireDate;
    }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public void updateProfile(String email, String phone, String position, String gender, LocalDate birthDate) { this.email = email; this.phone = phone; this.position = position; this.gender = gender; this.birthDate = birthDate; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public void changeEmploymentStatus(EmploymentStatus status) { this.employmentStatus = status; this.enabled = status != EmploymentStatus.TERMINATED; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getEmployeeNo() { return employeeNo; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getUsername() { return username; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getPassword() { return password; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getFullName() { return fullName; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getEmail() { return email; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getPhone() { return phone; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getPosition() { return position; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getGender() { return gender; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public LocalDate getBirthDate() { return birthDate; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public LocalDate getHireDate() { return hireDate; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public EmploymentStatus getEmploymentStatus() { return employmentStatus; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Role getRole() { return role; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public boolean isEnabled() { return enabled; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Department getDepartment() { return department; }
}
