/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.ehr.config;

import cn.zhuatech.ehr.model.*;
import cn.zhuatech.ehr.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.*;

@Component
public class DataInitializer implements CommandLineRunner {
    private final DepartmentRepository departments; private final UserRepository users; private final AttendanceRepository attendance; private final LeaveRequestRepository leaves; private final PayrollRepository payroll; private final JobOpeningRepository jobs; private final CandidateRepository candidates; private final PasswordEncoder encoder;
    public DataInitializer(DepartmentRepository departments, UserRepository users, AttendanceRepository attendance, LeaveRequestRepository leaves, PayrollRepository payroll, JobOpeningRepository jobs, CandidateRepository candidates, PasswordEncoder encoder) { this.departments=departments; this.users=users; this.attendance=attendance; this.leaves=leaves; this.payroll=payroll; this.jobs=jobs; this.candidates=candidates; this.encoder=encoder; }

    @Override @Transactional public void run(String... args) {
        if (users.count() > 0) return;
        Department tech=departments.save(new Department("TECH", "技术研发部", 10));
        Department hr=departments.save(new Department("HR", "人力资源部", 20));
        Department sales=departments.save(new Department("SALES", "市场商务部", 30));

        UserAccount admin=new UserAccount("ZH0001", "admin", encoder.encode("ZhuaTech@2026"), "系统管理员", UserAccount.Role.ADMIN, hr, LocalDate.of(2024, 1, 2));
        admin.updateProfile("contact@zhuatech.cn", "021-00000000", "平台管理员", "男", LocalDate.of(1990, 1, 1)); users.save(admin);
        UserAccount hrUser=new UserAccount("ZH0002", "hr", encoder.encode("Demo@2026"), "知华人事", UserAccount.Role.HR, hr, LocalDate.of(2025, 3, 10));
        hrUser.updateProfile("hr@zhuatech.cn", "13800000001", "人事经理", "女", LocalDate.of(1992, 5, 12)); users.save(hrUser);
        UserAccount demo=new UserAccount("ZH0003", "demo", encoder.encode("Demo@2026"), "知华员工", UserAccount.Role.EMPLOYEE, tech, LocalDate.of(2026, 2, 18));
        demo.updateProfile("demo@zhuatech.cn", "13800000000", "Java 工程师", "男", LocalDate.of(1996, 8, 20)); users.save(demo);
        UserAccount salesUser=new UserAccount("ZH0004", "zhangsan", encoder.encode("Demo@2026"), "张珊", UserAccount.Role.EMPLOYEE, sales, LocalDate.of(2025, 7, 8));
        salesUser.updateProfile("zhangsan@zhuatech.cn", "13900000000", "商务经理", "女", LocalDate.of(1993, 11, 6)); users.save(salesUser);

        Attendance today=new Attendance(demo, LocalDate.now()); today.checkIn(); attendance.save(today);
        leaves.save(new LeaveRequest(demo, "ANNUAL", LocalDateTime.now().plusDays(5), LocalDateTime.now().plusDays(6), new BigDecimal("1.0"), "个人事务"));
        payroll.save(new PayrollRecord(demo, YearMonth.now().minusMonths(1), new BigDecimal("12000"), new BigDecimal("800"), new BigDecimal("1000"), new BigDecimal("650")));
        JobOpening job=jobs.save(new JobOpening("Java 开发工程师", tech, 2, "负责企业信息化产品的 Java 后端研发，熟悉 Spring Boot 与 MySQL。", LocalDate.now()));
        Candidate candidate=new Candidate(job, "李明", "13700000000", "liming@example.com", "官网投递", "已完成简历初筛");
        candidate.changeStage(Candidate.Stage.INTERVIEW, "已通过初筛，等待技术面试"); candidates.save(candidate);
    }
}
