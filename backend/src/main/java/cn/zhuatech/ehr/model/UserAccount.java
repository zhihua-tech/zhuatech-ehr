/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity @Table(name = "ehr_employee")
public class UserAccount extends BaseEntity {
    public enum Role { ADMIN, HR, EMPLOYEE }
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
    protected UserAccount() {}
    public UserAccount(String employeeNo, String username, String password, String fullName, Role role, Department department, LocalDate hireDate) {
        this.employeeNo = employeeNo; this.username = username; this.password = password; this.fullName = fullName; this.role = role; this.department = department; this.hireDate = hireDate;
    }
    public void updateProfile(String email, String phone, String position, String gender, LocalDate birthDate) { this.email = email; this.phone = phone; this.position = position; this.gender = gender; this.birthDate = birthDate; }
    public void changeEmploymentStatus(EmploymentStatus status) { this.employmentStatus = status; this.enabled = status != EmploymentStatus.TERMINATED; }
    public String getEmployeeNo() { return employeeNo; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getPosition() { return position; }
    public String getGender() { return gender; }
    public LocalDate getBirthDate() { return birthDate; }
    public LocalDate getHireDate() { return hireDate; }
    public EmploymentStatus getEmploymentStatus() { return employmentStatus; }
    public Role getRole() { return role; }
    public boolean isEnabled() { return enabled; }
    public Department getDepartment() { return department; }
}
