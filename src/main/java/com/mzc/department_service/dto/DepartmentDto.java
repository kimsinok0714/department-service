package com.mzc.department_service.dto;

import org.springframework.hateoas.RepresentationModel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

//public class DepartmentDto extends RepresentationModel<DepartmentDto> {  

public class DepartmentDto {
         
    private Long departmentId;
    
    // 필드가 빈 문자열, 공백만 있는 문자열, null 값을 허용하지 않는다.
    @NotBlank(message = "Name is a required fields.")
    @Size(max = 50, message =  "Name can be up to 50 characters long.")
    private String departmentName;



}
