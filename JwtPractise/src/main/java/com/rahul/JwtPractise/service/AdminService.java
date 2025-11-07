package com.rahul.JwtPractise.service;

import com.rahul.JwtPractise.entity.Users;
import com.rahul.JwtPractise.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {
    private final UsersRepository usersRepository;

    public List<Users> getAllUsers(){
        return usersRepository.findAll();
    }
}
