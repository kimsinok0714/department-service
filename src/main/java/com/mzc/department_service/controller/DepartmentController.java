package com.mzc.department_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ctc.wstx.shaded.msv_core.reader.trex.ng.DataParamState;
import com.mzc.department_service.dto.DepartmentDto;

import com.mzc.department_service.exception.ApiException;
import com.mzc.department_service.service.DepartmentService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;


import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@OpenAPIDefinition(info = @Info(title = "부서 서비스 요청 API", description = "부서 서비스 요청 API 입니다.", version = "v1"))
@RestController
@RequestMapping(value = "api/v1/departments")
@Slf4j
@Validated
@RefreshScope
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;


    @Operation(summary = "부서 등록 요청", description = "부서 등록 요청을 처리한다.", tags = {"createDepartment"})  
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "SUCCESS"),
        @ApiResponse(responseCode = "500", description = "ApiException")
    })  
    @PostMapping(value = "/create")
    public EntityModel<DepartmentDto> createDepartment(@Valid @RequestBody DepartmentDto departmentDto) throws Exception {
        
        DepartmentDto savedDepartment = departmentService.saveDepartment(departmentDto);
        

        // RESTful API에서 새 리소스를 생성한 후, 그 위치를 클라이언트에게 전달하기 위해서 링크를 추가한다.      
        EntityModel<DepartmentDto> entityModel = EntityModel.of(savedDepartment);
        entityModel.add(linkTo(methodOn(DepartmentController.class).retrieveDepartmentById(savedDepartment.getDepartmentId())).withSelfRel());
    
        return entityModel;

    }

    // public ResponseEntity<EntityModel<DepartmentDto>> createDepartment(@Valid @RequestBody DepartmentDto departmentDto) throws Exception {
        
    //     DepartmentDto savedDepartment = departmentService.saveDepartment(departmentDto);
        

    //     // RESTful API에서 새 리소스를 생성한 후, 그 위치를 클라이언트에게 전달하기 위해서 링크를 추가한다.

    //     EntityModel<DepartmentDto> departmentResource = EntityModel.of(savedDepartment);
    //     departmentResource.add(linkTo(methodOn(DepartmentController.class).retrieveDepartmentById(savedDepartment.getDepartmentId())).withSelfRel());
    
    //     return ResponseEntity.ok(departmentResource);

    // }



    @Operation(summary = "부서 상세 조회 요청", description = "부서 상세 조회 요청을 처리하다.", tags = {"retrieveDepartmentById"})  
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "SUCCESS"),
        @ApiResponse(responseCode = "500", description = "ApiException")
    })  
    @GetMapping("/{departmentId}")
    public EntityModel<DepartmentDto> retrieveDepartmentById(@PathVariable(value="departmentId") Long departmentId) throws Exception {
      
        //EntityModel은 단순한 데이터 또는 객체에 추가적인 하이퍼미디어 링크를 제공하기 위해서 사용된다.

        DepartmentDto departmenDto = departmentService.findDepartmentById(departmentId);

        if (departmenDto == null) {
            throw new ApiException("There is no department associated with the provided departmentId");
        }

        EntityModel<DepartmentDto> entityModel = EntityModel.of(departmenDto);
        entityModel.add(linkTo(methodOn(DepartmentController.class).createDepartment(departmenDto)).withRel("create-department"));
        
        return entityModel;

    }


    // public ResponseEntity<EntityModel<DepartmentDto>> retrieveDepartmentById(@PathVariable(value="departmentId") Long departmentId) throws Exception {
      
    //     //EntityModel은 단순한 데이터 또는 객체에 추가적인 하이퍼미디어 링크를 제공하기 위해서 사용된다.

    //     DepartmentDto departmenDto = departmentService.findDepartmentById(departmentId);

    //     if (departmenDto == null) {
    //         throw new ApiException("There is no department associated with the provided departmentId");
    //     }

    //     EntityModel<DepartmentDto> departmentResource = EntityModel.of(departmenDto);
    //     departmentResource.add(linkTo(methodOn(DepartmentController.class).createDepartment(departmenDto)).withRel("create-department"));
        
    //     return ResponseEntity.ok(departmentResource);

    // }

}

