package com.example.EmployeeManagement.Exception;

import com.example.EmployeeManagement.Response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex){
        return new ResponseEntity<>(new ErrorResponse("NOT FOUND",ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleResourceAlreadyExist(ResourceAlreadyExistsException ex){
        return new ResponseEntity<>(new ErrorResponse("CONFLIT",ex.getMessage()),HttpStatus.CONFLICT);
    }


    @ExceptionHandler({NullPointerException.class, IllegalArgumentException.class, RuntimeException.class})
    public ResponseEntity<Map<String,Object>> handleGlobalExceptions(Exception ex){
        Map<String,Object> errorResponse=new HashMap<>();
        errorResponse.put("Message",ex.getMessage());
        errorResponse.put("Status",HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidation(MethodArgumentNotValidException ex){
        Map<String,String> errors=ex.getBindingResult().getFieldErrors()
                .stream().collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage
                ));

        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);


    }
}
