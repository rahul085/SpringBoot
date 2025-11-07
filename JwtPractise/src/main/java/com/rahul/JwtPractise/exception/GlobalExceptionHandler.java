package com.rahul.JwtPractise.exception;

import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(InvalidTokenException.class)
//    public ResponseEntity<Map<String,Object>> handleInvalidToken(InvalidTokenException ex){
//        Map<String ,Object> errorResponse=new HashMap<>();
//        errorResponse.put("Status", HttpStatus.BAD_REQUEST);
//        errorResponse.put("Message","Invalid token , please enter the active token or a valid token");
//        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<Map<String,Object>> handleMalformedJwtException(MalformedJwtException ex){
        Map<String ,Object> errorResponse=new HashMap<>();
        errorResponse.put("Status", HttpStatus.UNAUTHORIZED);
        errorResponse.put("Message", ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<Map<String,Object>> handleSignatureException(SignatureException ex){
        Map<String ,Object> errorResponse=new HashMap<>();
        errorResponse.put("Status", HttpStatus.UNAUTHORIZED);
        errorResponse.put("Message", ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String,Object>> handleIllegalArgumentException(IllegalArgumentException ex){
        Map<String ,Object> errorResponse=new HashMap<>();
        errorResponse.put("Status", HttpStatus.UNAUTHORIZED);
        errorResponse.put("Message", ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.UNAUTHORIZED);
    }

//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<Map<String,Object>> handleUserNotFound(UserNotFoundException ex){
//        Map<String ,Object> errorResponse=new HashMap<>();
//        errorResponse.put("Status", HttpStatus.NOT_FOUND);
//        errorResponse.put("Message","User not found");
//        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(RoleNotFoundException.class)
//    public ResponseEntity<Map<String,Object>> handleRoleNotFound(RoleNotFoundException ex){
//        Map<String ,Object> errorResponse=new HashMap<>();
//        errorResponse.put("Status", HttpStatus.NOT_FOUND);
//        errorResponse.put("Message","Role not found");
//        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
//    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String,Object>> handleBadCredentialsException(BadCredentialsException ex){
        Map<String ,Object> errorResponse=new HashMap<>();
        errorResponse.put("Status", HttpStatus.BAD_REQUEST);
        errorResponse.put("Message", ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }
}
