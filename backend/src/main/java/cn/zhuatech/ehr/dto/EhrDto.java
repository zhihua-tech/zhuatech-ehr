/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.dto;

import cn.zhuatech.ehr.model.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;

public final class EhrDto {
    private EhrDto() {}

    public record DepartmentView(Long id, String code, String name) {
        public static DepartmentView from(Department d) { return new DepartmentView(d.getId(), d.getCode(), d.getName()); }
    }

    public record EmployeeCreateRequest(
        @NotBlank @Size(max=32) String employeeNo,
        @NotBlank @Size(max=32) String username,
        @NotBlank @Size(min=8, max=72) String password,
        @NotBlank @Size(max=50) String fullName,
        @Email @Size(max=100) String email,
        @Size(max=20) String phone,
        @Size(max=50) String position,
        @Size(max=10) String gender,
        LocalDate birthDate,
        @NotNull LocalDate hireDate,
        @NotNull Long departmentId,
        @NotBlank @Pattern(regexp="ADMIN|HR|EMPLOYEE") String role) {}

    public record EmployeeStatusRequest(@NotBlank @Pattern(regexp="PROBATION|ACTIVE|LEAVE|TERMINATED") String status) {}

    public record EmployeeView(Long id, String employeeNo, String username, String fullName, String email, String phone, String position, String gender, LocalDate birthDate, LocalDate hireDate, String role, String employmentStatus, Long departmentId, String departmentName) {
        public static EmployeeView from(UserAccount u) { return new EmployeeView(u.getId(), u.getEmployeeNo(), u.getUsername(), u.getFullName(), u.getEmail(), u.getPhone(), u.getPosition(), u.getGender(), u.getBirthDate(), u.getHireDate(), u.getRole().name(), u.getEmploymentStatus().name(), u.getDepartment() == null ? null : u.getDepartment().getId(), u.getDepartment() == null ? null : u.getDepartment().getName()); }
    }

    public record AttendanceView(Long id, LocalDate workDate, LocalDateTime checkInTime, LocalDateTime checkOutTime, String status) {
        public static AttendanceView from(Attendance a) { return new AttendanceView(a.getId(), a.getWorkDate(), a.getCheckInTime(), a.getCheckOutTime(), a.getStatus()); }
    }

    public record LeaveCreateRequest(@NotBlank String leaveType, @NotNull LocalDateTime startTime, @NotNull LocalDateTime endTime, @NotNull @DecimalMin("0.5") BigDecimal durationDays, @NotBlank @Size(max=500) String reason) {}
    public record LeaveApproveRequest(boolean approved, @Size(max=500) String comment) {}
    public record LeaveView(Long id, String applicantName, String leaveType, LocalDateTime startTime, LocalDateTime endTime, BigDecimal durationDays, String reason, String status, String approverComment, String approverName, LocalDateTime createdAt) {
        public static LeaveView from(LeaveRequest l) { return new LeaveView(l.getId(), l.getApplicant().getFullName(), l.getLeaveType(), l.getStartTime(), l.getEndTime(), l.getDurationDays(), l.getReason(), l.getStatus().name(), l.getApproverComment(), l.getApproverName(), l.getCreatedAt()); }
    }

    public record PayrollCreateRequest(@NotNull Long employeeId, @NotBlank @Pattern(regexp="\\d{4}-(0[1-9]|1[0-2])") String payrollMonth, @NotNull @DecimalMin("0") BigDecimal baseSalary, @DecimalMin("0") BigDecimal allowance, @DecimalMin("0") BigDecimal bonus, @DecimalMin("0") BigDecimal deduction) {}
    public record PayrollStatusRequest(@NotBlank @Pattern(regexp="CONFIRMED|PAID") String status) {}
    public record PayrollView(Long id, Long employeeId, String employeeName, String payrollMonth, BigDecimal baseSalary, BigDecimal allowance, BigDecimal bonus, BigDecimal deduction, BigDecimal netSalary, String status) {
        public static PayrollView from(PayrollRecord p) { return new PayrollView(p.getId(), p.getEmployee().getId(), p.getEmployee().getFullName(), p.getPayrollMonth(), p.getBaseSalary(), p.getAllowance(), p.getBonus(), p.getDeduction(), p.getNetSalary(), p.getStatus().name()); }
    }

    public record JobCreateRequest(@NotBlank @Size(max=100) String title, @NotNull Long departmentId, @NotNull @Min(1) Integer headcount, @NotBlank @Size(max=1000) String description) {}
    public record JobView(Long id, String title, Long departmentId, String departmentName, Integer headcount, String description, LocalDate publishDate, String status) {
        public static JobView from(JobOpening j) { return new JobView(j.getId(), j.getTitle(), j.getDepartment().getId(), j.getDepartment().getName(), j.getHeadcount(), j.getDescription(), j.getPublishDate(), j.getStatus().name()); }
    }
    public record CandidateCreateRequest(@NotNull Long jobId, @NotBlank @Size(max=50) String name, @NotBlank @Size(max=20) String phone, @Email @Size(max=100) String email, @NotBlank @Size(max=30) String source, @Size(max=500) String remark) {}
    public record CandidateStageRequest(@NotBlank @Pattern(regexp="APPLIED|SCREENING|INTERVIEW|OFFER|HIRED|REJECTED") String stage, @Size(max=500) String remark) {}
    public record CandidateView(Long id, Long jobId, String jobTitle, String name, String phone, String email, String source, String stage, String remark, LocalDate appliedDate) {
        public static CandidateView from(Candidate c) { return new CandidateView(c.getId(), c.getJob().getId(), c.getJob().getTitle(), c.getName(), c.getPhone(), c.getEmail(), c.getSource(), c.getStage().name(), c.getRemark(), c.getAppliedDate()); }
    }

    public record DashboardView(long activeEmployees, long pendingLeaves, long openJobs, long interviewingCandidates, boolean checkedIn, boolean checkedOut) {}
}
