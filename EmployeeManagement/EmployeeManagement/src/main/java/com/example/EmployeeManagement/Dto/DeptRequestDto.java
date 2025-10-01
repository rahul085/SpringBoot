package com.example.EmployeeManagement.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeptRequestDto {
    @NotNull(message = "name is mandatory")
    private String name;

}
