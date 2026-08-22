/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.repository;
import cn.zhuatech.ehr.model.*;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    @EntityGraph(attributePaths = "applicant")
    List<LeaveRequest> findByApplicantOrderByCreatedAtDesc(UserAccount applicant);
    @EntityGraph(attributePaths = "applicant")
    List<LeaveRequest> findByStatusOrderByCreatedAtAsc(LeaveRequest.Status status);
    @EntityGraph(attributePaths = "applicant")
    Optional<LeaveRequest> findById(Long id);
    long countByApplicantAndStatus(UserAccount applicant, LeaveRequest.Status status);
    long countByStatus(LeaveRequest.Status status);
}
