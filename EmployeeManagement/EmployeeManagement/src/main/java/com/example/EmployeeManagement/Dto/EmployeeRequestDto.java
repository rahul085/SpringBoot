package com.example.EmployeeManagement.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequestDto {
    @NotBlank(message = "name is required")
    private String name;
    @NotNull(message = "age is mandatory")
    private int age;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid format")
    private String email;
    @NotNull(message = "salary is required")
    private Double salary;
    @NotNull(message = "Department id is required")
    private Long deptId;
}
