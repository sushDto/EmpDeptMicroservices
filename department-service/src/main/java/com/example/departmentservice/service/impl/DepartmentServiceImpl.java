package com.example.departmentservice.service.impl;

import com.example.departmentservice.dto.DepartmentDto;
import com.example.departmentservice.entity.Department;
import com.example.departmentservice.repository.DepartmentRepository;
import com.example.departmentservice.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {


    private DepartmentRepository departmentRepository;
    @Override
    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {

        //convert departentDto to jpa entity
        Department department=new Department(
                departmentDto.getId(),
                departmentDto.getDepartmentName(),
                departmentDto.getDepartmentDescription(),
                departmentDto.getDepartmentCode()

        );

        Department savedDepartment=departmentRepository.save(department);

        DepartmentDto savedDeptDto=new DepartmentDto(
                savedDepartment.getId(),
                savedDepartment.getDepartmentName(),
                savedDepartment.getDepartmentDescription(),
                savedDepartment.getDepartmentCode()

                );


        return savedDeptDto;
    }

    @Override
    public DepartmentDto getDepartmentByCode(String deptCode) {
        Department dept=departmentRepository.findByDepartmentCode(deptCode);

        DepartmentDto deptDto=new DepartmentDto(
                dept.getId(),
                dept.getDepartmentName(),
                dept.getDepartmentDescription(),
                dept.getDepartmentCode()

        );
        return deptDto;
    }
}
