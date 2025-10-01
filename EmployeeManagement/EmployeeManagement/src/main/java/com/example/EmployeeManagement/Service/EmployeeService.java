package com.example.EmployeeManagement.Service;

import com.example.EmployeeManagement.Dto.EmployeeRequestDto;
import com.example.EmployeeManagement.Dto.EmployeeResponseDto;
import com.example.EmployeeManagement.Entity.Department;
import com.example.EmployeeManagement.Entity.Employee;
import com.example.EmployeeManagement.Exception.ResourceNotFoundException;
import com.example.EmployeeManagement.Mapper.EmpMapper;
import com.example.EmployeeManagement.Repository.DepartmentRepository;
import com.example.EmployeeManagement.Repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository empRepo;
    private final DepartmentRepository deptRepo;
    private final EmpMapper mapper;


    public EmployeeResponseDto addEmployee(EmployeeRequestDto dto){
        Department dept=deptRepo.findById(dto.getDeptId()).orElseThrow(()->new ResourceNotFoundException("Department not found with id "+dto.getDeptId()));

        Employee employee=mapper.toEntity(dto);
        employee.setDepartment(dept);
        Employee savedEmp=empRepo.save(employee);

        return mapper.toDto(savedEmp);

    }

    public List<EmployeeResponseDto> getAllEmps(){
        List<Employee> emps=empRepo.findAll();
      return emps.stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
