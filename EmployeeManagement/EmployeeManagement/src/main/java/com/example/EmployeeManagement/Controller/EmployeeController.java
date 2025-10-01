package com.example.EmployeeManagement.Controller;

import com.example.EmployeeManagement.Dto.EmployeeRequestDto;
import com.example.EmployeeManagement.Dto.EmployeeResponseDto;
import com.example.EmployeeManagement.Service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/emp")
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponseDto> addEmployee(@RequestBody EmployeeRequestDto dto){
        return new ResponseEntity<>(employeeService.addEmployee(dto), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployees(){
        return new ResponseEntity<>(employeeService.getAllEmps(),HttpStatus.OK);
    }
}
