package com.example.EmployeeManagement.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuccessResponse <T>{
    private String response;
    private String message;
    private T object;
}
