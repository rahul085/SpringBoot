package com.rahul.JwtPractise.controller;


import com.rahul.JwtPractise.dto.LoginRequestDto;
import com.rahul.JwtPractise.dto.LoginResponseDto;
import com.rahul.JwtPractise.dto.UserRegisterDto;
import com.rahul.JwtPractise.entity.Users;
import com.rahul.JwtPractise.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class UserRegisterController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<Users> registerUser(@RequestBody UserRegisterDto dto){
        return new ResponseEntity<>(service.registerUser(dto), HttpStatus.OK);

    }

    @GetMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto dto){

        log.info("Inside login() of UserRegisterController");

        return new ResponseEntity<>(service.login(dto),HttpStatus.OK);
    }

    @GetMapping("/activeToken")
    public ResponseEntity<String> generateActiveToken(@RequestParam String refreshToken){
        return new ResponseEntity<>(service.generateActiveToken(refreshToken),HttpStatus.OK);
    }
}
