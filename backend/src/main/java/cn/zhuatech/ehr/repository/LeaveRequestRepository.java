/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.repository;
import cn.zhuatech.ehr.model.*;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @EntityGraph(attributePaths = "applicant")
    List<LeaveRequest> findByApplicantOrderByCreatedAtDesc(UserAccount applicant);
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @EntityGraph(attributePaths = "applicant")
    List<LeaveRequest> findByStatusOrderByCreatedAtAsc(LeaveRequest.Status status);
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @EntityGraph(attributePaths = "applicant")
    Optional<LeaveRequest> findById(Long id);
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    long countByApplicantAndStatus(UserAccount applicant, LeaveRequest.Status status);
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    long countByStatus(LeaveRequest.Status status);
}
