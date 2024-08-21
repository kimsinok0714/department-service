package com.mzc.department_service.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "DEPARTMENT")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Department {
    @Id   
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // MySQL
    @Column(name = "dept_id")
    private Long departmentId;

    @Column(name = "dept_name")
    private String departmentName;


}
