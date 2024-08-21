package com.mzc.department_service.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mzc.department_service.domain.Department;
import com.mzc.department_service.dto.DepartmentDto;
import com.mzc.department_service.repository.DepartmentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;


    public DepartmentDto findDepartmentById(Long departmentId) {
        
        Optional<Department> optional = departmentRepository.findById(departmentId);

        if (optional.isPresent()) {

            Department department = optional.get();

            return DepartmentDto.builder()
                .departmentId(department.getDepartmentId())
                .departmentName(department.getDepartmentName())
                .build();                
        }
            
        return null;
        
    }


    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {

        Department department = Department.builder()               
            .departmentName(departmentDto.getDepartmentName())
            .build();    

        Department savedDepartment = departmentRepository.save(department);

        log.info("savedDepartment : {}", savedDepartment);

        departmentDto.setDepartmentId(savedDepartment.getDepartmentId());
        
        return departmentDto;        

    }

}
