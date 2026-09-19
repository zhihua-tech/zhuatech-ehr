/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Entity @Table(name = "ehr_job_opening")
public class JobOpening extends BaseEntity {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public enum Status { DRAFT, OPEN, CLOSED }
    @Column(nullable = false, length = 100) private String title;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "department_id") private Department department;
    @Column(nullable = false) private Integer headcount;
    @Column(nullable = false, length = 1000) private String description;
    @Column(nullable = false) private LocalDate publishDate;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private Status status = Status.OPEN;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    protected JobOpening() {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public JobOpening(String title, Department department, int headcount, String description, LocalDate publishDate) { this.title=title; this.department=department; this.headcount=headcount; this.description=description; this.publishDate=publishDate; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public void close() { this.status = Status.CLOSED; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getTitle() { return title; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Department getDepartment() { return department; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Integer getHeadcount() { return headcount; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getDescription() { return description; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public LocalDate getPublishDate() { return publishDate; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Status getStatus() { return status; }
}
