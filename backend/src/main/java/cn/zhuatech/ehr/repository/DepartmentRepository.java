/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.ehr.repository;
import cn.zhuatech.ehr.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface DepartmentRepository extends JpaRepository<Department, Long> { List<Department> findAllByOrderBySortOrderAsc(); }
