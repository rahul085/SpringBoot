package com.example.EmployeeManagement.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String reason;
    private String message;
}
