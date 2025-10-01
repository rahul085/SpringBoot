package com.example.EmployeeManagement.Controller;

import com.example.EmployeeManagement.Dto.DeptRequestDto;
import com.example.EmployeeManagement.Dto.DeptResponseDto;
import com.example.EmployeeManagement.Service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/Dept")
public class DepartmentController {
    private final DepartmentService deptService;
    @PostMapping
    public ResponseEntity<DeptResponseDto> addDept(@RequestBody DeptRequestDto dto){
        return new ResponseEntity<>(deptService.addDept(dto), HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<DeptResponseDto>> getAllDepts(){
        return new ResponseEntity<>(deptService.getAllDepts(),HttpStatus.OK);

    }
}
