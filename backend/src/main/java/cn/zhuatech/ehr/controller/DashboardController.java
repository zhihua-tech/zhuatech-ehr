/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.controller;
import cn.zhuatech.ehr.common.ApiResponse;
import cn.zhuatech.ehr.dto.EhrDto.DashboardView;
import cn.zhuatech.ehr.model.*;
import cn.zhuatech.ehr.repository.*;
import cn.zhuatech.ehr.service.CurrentUserService;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController @RequestMapping("/api/dashboard")
public class DashboardController {
    private final UserRepository users; private final LeaveRequestRepository leaves; private final JobOpeningRepository jobs; private final CandidateRepository candidates; private final AttendanceRepository attendance; private final CurrentUserService current;
    public DashboardController(UserRepository users, LeaveRequestRepository leaves, JobOpeningRepository jobs, CandidateRepository candidates, AttendanceRepository attendance, CurrentUserService current) { this.users=users; this.leaves=leaves; this.jobs=jobs; this.candidates=candidates; this.attendance=attendance; this.current=current; }
    @GetMapping public ApiResponse<DashboardView> dashboard() {
        var user=current.get(); var today=attendance.findByUserAndWorkDate(user, LocalDate.now());
        return ApiResponse.ok(new DashboardView(users.countByEmploymentStatus(UserAccount.EmploymentStatus.ACTIVE), leaves.countByStatus(LeaveRequest.Status.PENDING), jobs.countByStatus(JobOpening.Status.OPEN), candidates.countByStage(Candidate.Stage.INTERVIEW), today.map(a -> a.getCheckInTime()!=null).orElse(false), today.map(a -> a.getCheckOutTime()!=null).orElse(false)));
    }
}
