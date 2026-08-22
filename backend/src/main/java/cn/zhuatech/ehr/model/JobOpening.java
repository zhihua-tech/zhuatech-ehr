/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity @Table(name = "ehr_job_opening")
public class JobOpening extends BaseEntity {
    public enum Status { DRAFT, OPEN, CLOSED }
    @Column(nullable = false, length = 100) private String title;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "department_id") private Department department;
    @Column(nullable = false) private Integer headcount;
    @Column(nullable = false, length = 1000) private String description;
    @Column(nullable = false) private LocalDate publishDate;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private Status status = Status.OPEN;
    protected JobOpening() {}
    public JobOpening(String title, Department department, int headcount, String description, LocalDate publishDate) { this.title=title; this.department=department; this.headcount=headcount; this.description=description; this.publishDate=publishDate; }
    public void close() { this.status = Status.CLOSED; }
    public String getTitle() { return title; }
    public Department getDepartment() { return department; }
    public Integer getHeadcount() { return headcount; }
    public String getDescription() { return description; }
    public LocalDate getPublishDate() { return publishDate; }
    public Status getStatus() { return status; }
}
