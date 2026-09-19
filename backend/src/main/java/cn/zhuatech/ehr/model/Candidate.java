/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Entity @Table(name = "ehr_candidate")
public class Candidate extends BaseEntity {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public enum Stage { APPLIED, SCREENING, INTERVIEW, OFFER, HIRED, REJECTED }
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "job_id") private JobOpening job;
    @Column(nullable = false, length = 50) private String name;
    @Column(nullable = false, length = 20) private String phone;
    @Column(length = 100) private String email;
    @Column(nullable = false, length = 30) private String source;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private Stage stage = Stage.APPLIED;
    @Column(length = 500) private String remark;
    @Column(nullable = false) private LocalDate appliedDate;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    protected Candidate() {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Candidate(JobOpening job, String name, String phone, String email, String source, String remark) { this.job=job; this.name=name; this.phone=phone; this.email=email; this.source=source; this.remark=remark; this.appliedDate=LocalDate.now(); }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public void changeStage(Stage stage, String remark) { this.stage=stage; if (remark != null) this.remark=remark; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public JobOpening getJob() { return job; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getName() { return name; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getPhone() { return phone; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getEmail() { return email; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getSource() { return source; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Stage getStage() { return stage; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getRemark() { return remark; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public LocalDate getAppliedDate() { return appliedDate; }
}
