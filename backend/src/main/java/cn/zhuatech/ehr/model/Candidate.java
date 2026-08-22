/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity @Table(name = "ehr_candidate")
public class Candidate extends BaseEntity {
    public enum Stage { APPLIED, SCREENING, INTERVIEW, OFFER, HIRED, REJECTED }
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "job_id") private JobOpening job;
    @Column(nullable = false, length = 50) private String name;
    @Column(nullable = false, length = 20) private String phone;
    @Column(length = 100) private String email;
    @Column(nullable = false, length = 30) private String source;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private Stage stage = Stage.APPLIED;
    @Column(length = 500) private String remark;
    @Column(nullable = false) private LocalDate appliedDate;
    protected Candidate() {}
    public Candidate(JobOpening job, String name, String phone, String email, String source, String remark) { this.job=job; this.name=name; this.phone=phone; this.email=email; this.source=source; this.remark=remark; this.appliedDate=LocalDate.now(); }
    public void changeStage(Stage stage, String remark) { this.stage=stage; if (remark != null) this.remark=remark; }
    public JobOpening getJob() { return job; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getSource() { return source; }
    public Stage getStage() { return stage; }
    public String getRemark() { return remark; }
    public LocalDate getAppliedDate() { return appliedDate; }
}
