package com.mzc.department_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mzc.department_service.dto.DepartmentDto;

import com.mzc.department_service.exception.ApiException;
import com.mzc.department_service.service.DepartmentService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@OpenAPIDefinition(info = @Info(title = "부서 서비스 요청 API", description = "부서 서비스 요청 API 입니다.", version = "v1"))
@RestController
@RequestMapping(value = "api/v1/departments")
@RequiredArgsConstructor
@Slf4j
@Validated
@RefreshScope
public class DepartmentController {

    private final DepartmentService departmentService;


    @Operation(summary = "부서 등록 요청", description = "부서 등록 요청을 처리하다.", tags = {"createDepartment"})  
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "SUCCESS"),
        @ApiResponse(responseCode = "500", description = "ApiException")
    })  
    @PostMapping(value = "/create")
    public ResponseEntity<EntityModel<DepartmentDto>> createDepartment(@Valid @RequestBody DepartmentDto departmentDto) throws Exception {
        
        DepartmentDto savedDepartment = departmentService.saveDepartment(departmentDto);
        

        // RESTful API에서 새 리소스를 생성한 후, 그 위치를 클라이언트에게 전달하기 위해서 링크를 추가한다.
    
        EntityModel<DepartmentDto> departmentResource = EntityModel.of(savedDepartment);
        departmentResource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveDepartmentById(savedDepartment.getDepartmentId())).withSelfRel());
    
        return ResponseEntity.ok(departmentResource);

    }



    @Operation(summary = "부서 상세 조회 요청", description = "부서 상세 조회 요청을 처리하다.", tags = {"retrieveDepartmentById"})  
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "SUCCESS"),
        @ApiResponse(responseCode = "500", description = "ApiException")
    })  
    @GetMapping("/{departmentId}")
    public ResponseEntity<EntityModel<DepartmentDto>> retrieveDepartmentById(@PathVariable Long departmentId) throws ApiException {
      
        //EntityModel은 단순한 데이터 또는 객체에 추가적인 하이퍼미디어 링크를 제공하기 위해서 사용된다.

        DepartmentDto department = departmentService.findDepartmentById(departmentId);

        if (department == null) {
            throw new ApiException("There is no department associated with the provided departmentId");
        }

        EntityModel<DepartmentDto> obj = EntityModel.of(department);
        obj.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DepartmentController.class).retrieveDepartmentById(departmentId)).withSelfRel());
        
        return ResponseEntity.ok(obj);

    }

}

