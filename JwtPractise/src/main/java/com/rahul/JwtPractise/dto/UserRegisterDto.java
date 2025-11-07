package com.rahul.JwtPractise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {

    private String userName;
    private String password;
    private Set<String> roles=new HashSet<>();
}
