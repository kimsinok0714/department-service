package com.mzc.department_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ResponseDto {

    private String code;
    private String message;
    

}
