package com.example.employeeservice.service.impl;


import com.example.employeeservice.dto.ApiResponseDto;
import com.example.employeeservice.dto.DepartmentDto;
import com.example.employeeservice.dto.EmployeeDto;
import com.example.employeeservice.entity.Employee;
import com.example.employeeservice.repository.EmployeeRepository;
import com.example.employeeservice.service.APIClient;
import com.example.employeeservice.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
//    private RestTemplate restTemplate;
//    private WebClient webClient;
        private APIClient apiFeignClient;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto empDto) {
        Employee emp=new Employee(
                empDto.getId(),
                empDto.getFirstName(),
                empDto.getLastName(),
                empDto.getEmail(),
                empDto.getDepartmentCode()
        );
        Employee savedEmp=employeeRepository.save(emp);

        EmployeeDto savedEmpDto= new EmployeeDto(
                savedEmp.getId(),
                savedEmp.getFirstName(),
                savedEmp.getLastName(),
                savedEmp.getEmail(),
                savedEmp.getDepartmentCode()
        );
        return savedEmpDto;
    }

    @Override
    public ApiResponseDto getEmployeeById(Long id) {

        Employee emp=employeeRepository.findById(id).get();

        //Using REst Template
//        ResponseEntity<DepartmentDto> responseEntity= restTemplate.getForEntity("http://localhost:8080/api/departments/"+ emp.getDepartmentCode(), DepartmentDto.class);

//        DepartmentDto departmentDto=responseEntity.getBody();

        //Using Web Client
//        DepartmentDto departmentDto=webClient.get()
//                .uri("http://localhost:8080/api/departments/"+ emp.getDepartmentCode())
//                .retrieve()
//                .bodyToMono(DepartmentDto.class)
//                .block();

        //Using OpenFeign library
        DepartmentDto departmentDto=apiFeignClient.getDepartment(emp.getDepartmentCode());

        EmployeeDto empDto= new EmployeeDto(
                emp.getId(),
                emp.getFirstName(),
                emp.getLastName(),
                emp.getEmail(),
                emp.getDepartmentCode()
        );

        ApiResponseDto apiResponseDto=new ApiResponseDto();
        apiResponseDto.setEmployee(empDto);
        apiResponseDto.setDepartment(departmentDto);
        return apiResponseDto;
    }
}
