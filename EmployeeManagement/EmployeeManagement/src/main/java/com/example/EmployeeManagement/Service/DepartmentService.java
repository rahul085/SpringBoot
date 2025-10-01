package com.example.EmployeeManagement.Service;

import com.example.EmployeeManagement.Dto.DeptRequestDto;
import com.example.EmployeeManagement.Dto.DeptResponseDto;
import com.example.EmployeeManagement.Entity.Department;
import com.example.EmployeeManagement.Entity.Employee;
import com.example.EmployeeManagement.Exception.ResourceNotFoundException;
import com.example.EmployeeManagement.Mapper.DeptMapper;
import com.example.EmployeeManagement.Repository.DepartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DepartmentService {
    private final DepartmentRepository deptRepo;
    private final DeptMapper mapper;

    public DeptResponseDto addDept(DeptRequestDto dto){
        Department department=mapper.toEntity(dto);
        Department savedDept=deptRepo.save(department);

        return mapper.toDto(savedDept);

    }


    public List<DeptResponseDto> getAllDepts(){
        return deptRepo.findAll().stream()
                .map(mapper::toDto).collect(Collectors.toList());
    }

}
