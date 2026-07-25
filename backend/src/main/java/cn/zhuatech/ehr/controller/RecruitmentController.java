/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.ehr.controller;
import cn.zhuatech.ehr.common.*;
import cn.zhuatech.ehr.dto.EhrDto.*;
import cn.zhuatech.ehr.model.*;
import cn.zhuatech.ehr.repository.*;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController @RequestMapping("/api/recruitment") @PreAuthorize("hasAnyRole('ADMIN','HR')")
public class RecruitmentController {
    private final JobOpeningRepository jobs; private final CandidateRepository candidates; private final DepartmentRepository departments;
    public RecruitmentController(JobOpeningRepository jobs, CandidateRepository candidates, DepartmentRepository departments) { this.jobs=jobs; this.candidates=candidates; this.departments=departments; }
    @GetMapping("/jobs") public ApiResponse<List<JobView>> jobs() { return ApiResponse.ok(jobs.findAllByOrderByCreatedAtDesc().stream().map(JobView::from).toList()); }
    @PostMapping("/jobs") public ApiResponse<JobView> createJob(@Valid @RequestBody JobCreateRequest req) { var department=departments.findById(req.departmentId()).orElseThrow(() -> new BusinessException("部门不存在")); return ApiResponse.ok("招聘职位已创建", JobView.from(jobs.save(new JobOpening(req.title(), department, req.headcount(), req.description(), LocalDate.now())))); }
    @PostMapping("/jobs/{id}/close") public ApiResponse<JobView> closeJob(@PathVariable Long id) { var job=jobs.findById(id).orElseThrow(() -> new BusinessException("招聘职位不存在")); job.close(); return ApiResponse.ok("招聘职位已关闭", JobView.from(jobs.save(job))); }
    @GetMapping("/candidates") public ApiResponse<List<CandidateView>> candidates() { return ApiResponse.ok(candidates.findAllByOrderByCreatedAtDesc().stream().map(CandidateView::from).toList()); }
    @PostMapping("/candidates") public ApiResponse<CandidateView> createCandidate(@Valid @RequestBody CandidateCreateRequest req) { var job=jobs.findById(req.jobId()).orElseThrow(() -> new BusinessException("招聘职位不存在")); return ApiResponse.ok("候选人已添加", CandidateView.from(candidates.save(new Candidate(job, req.name(), req.phone(), req.email(), req.source(), req.remark())))); }
    @PatchMapping("/candidates/{id}/stage") public ApiResponse<CandidateView> stage(@PathVariable Long id, @Valid @RequestBody CandidateStageRequest req) { var candidate=candidates.findById(id).orElseThrow(() -> new BusinessException("候选人不存在")); candidate.changeStage(Candidate.Stage.valueOf(req.stage()), req.remark()); return ApiResponse.ok("候选人阶段已更新", CandidateView.from(candidates.save(candidate))); }
}
