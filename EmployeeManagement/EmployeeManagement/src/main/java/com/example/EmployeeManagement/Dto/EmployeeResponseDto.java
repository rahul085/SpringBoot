package com.example.EmployeeManagement.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDto {

    private Long id;
    private String name;
    private int age;
    private String email;
    private double salary;
    private Long deptId;
}
