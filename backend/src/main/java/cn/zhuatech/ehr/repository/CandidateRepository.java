/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.ehr.repository;
import cn.zhuatech.ehr.model.Candidate;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    @EntityGraph(attributePaths = "job")
    List<Candidate> findAllByOrderByCreatedAtDesc();
    @EntityGraph(attributePaths = "job")
    Optional<Candidate> findById(Long id);
    long countByStage(Candidate.Stage stage);
}
