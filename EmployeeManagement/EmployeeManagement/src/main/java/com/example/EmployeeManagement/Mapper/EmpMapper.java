package com.example.EmployeeManagement.Mapper;

import com.example.EmployeeManagement.Dto.EmployeeRequestDto;
import com.example.EmployeeManagement.Dto.EmployeeResponseDto;
import com.example.EmployeeManagement.Entity.Department;
import com.example.EmployeeManagement.Entity.Employee;
import com.example.EmployeeManagement.Exception.ResourceNotFoundException;
import com.example.EmployeeManagement.Repository.DepartmentRepository;
import com.example.EmployeeManagement.Repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@Component
public class EmpMapper {
    private final ModelMapper modelMapper;
    private DepartmentRepository deptRepo;
    private EmployeeRepository empRepo;

    public EmployeeResponseDto toDto(Employee employee){
        EmployeeResponseDto dto=modelMapper.map(employee,EmployeeResponseDto.class);

        if(employee.getDepartment()==null){
            throw new ResourceNotFoundException("No department found");
        }
        dto.setDeptId(employee.getDepartment().getId());

        return dto;

    }

    public Employee toEntity(EmployeeRequestDto dto){

        return modelMapper.map(dto,Employee.class);
    }
}
