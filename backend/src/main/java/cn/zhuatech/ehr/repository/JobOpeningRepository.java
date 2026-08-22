/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.repository;
import cn.zhuatech.ehr.model.JobOpening;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface JobOpeningRepository extends JpaRepository<JobOpening, Long> {
    @EntityGraph(attributePaths = "department")
    List<JobOpening> findAllByOrderByCreatedAtDesc();
    @EntityGraph(attributePaths = "department")
    Optional<JobOpening> findById(Long id);
    long countByStatus(JobOpening.Status status);
}
