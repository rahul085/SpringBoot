package com.example.EmployeeManagement.Mapper;

import com.example.EmployeeManagement.Dto.DeptRequestDto;
import com.example.EmployeeManagement.Dto.DeptResponseDto;
import com.example.EmployeeManagement.Entity.Department;
import com.example.EmployeeManagement.Entity.Employee;
import com.example.EmployeeManagement.Repository.DepartmentRepository;
import com.example.EmployeeManagement.Repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Component
@AllArgsConstructor
public class DeptMapper {
    private final ModelMapper modelMapper;


    public DeptResponseDto toDto(Department dept){
        DeptResponseDto dto=modelMapper.map(dept,DeptResponseDto.class);
        if(dept.getEmployees()!=null){
            List<Long> empIdList=dept.getEmployees()
                    .stream().map(Employee::getId).
                    collect(Collectors.toList());
            dto.setEmpIdList(empIdList);
        }
     return dto;
    }


    public Department toEntity (DeptRequestDto dto){
        return  modelMapper.map(dto,Department.class);

    }

}


