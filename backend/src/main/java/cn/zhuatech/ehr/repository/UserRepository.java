/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. */
package cn.zhuatech.ehr.repository;
import cn.zhuatech.ehr.model.UserAccount;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface UserRepository extends JpaRepository<UserAccount, Long> {
    @EntityGraph(attributePaths = "department")
    Optional<UserAccount> findByUsername(String username);
    @EntityGraph(attributePaths = "department")
    Optional<UserAccount> findById(Long id);
    @EntityGraph(attributePaths = "department")
    List<UserAccount> findAllByOrderByEmployeeNoAsc();
    boolean existsByEmployeeNo(String employeeNo);
    boolean existsByUsername(String username);
    long countByEmploymentStatus(UserAccount.EmploymentStatus status);
}
