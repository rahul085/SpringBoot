package com.example.EmployeeManagement.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeptResponseDto {
    private Long id;
    private String name;
    private List<Long> empIdList;

}
