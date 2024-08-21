package com.mzc.department_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mzc.department_service.domain.Department;


@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    

}
