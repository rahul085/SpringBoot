package com.rahul.JwtPractise.service;

import com.rahul.JwtPractise.Security.CustomUserDetailService;
import com.rahul.JwtPractise.Security.JwtUtil;
import com.rahul.JwtPractise.dto.LoginRequestDto;
import com.rahul.JwtPractise.dto.LoginResponseDto;
import com.rahul.JwtPractise.dto.UserRegisterDto;
import com.rahul.JwtPractise.entity.Users;
import com.rahul.JwtPractise.exception.RoleNotFoundException;
import com.rahul.JwtPractise.repository.RolesRepository;
import com.rahul.JwtPractise.repository.UsersRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.stream.Collectors;


@Slf4j
@Service
@AllArgsConstructor
public class AuthService {
    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailService service;
    private  final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public Users registerUser(UserRegisterDto dto){
        Users user=new Users();
        user.setUserName(dto.getUserName());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(dto.getRoles().stream().map(role->rolesRepository.findByRoleName(role).orElseThrow(
                ()-> new RoleNotFoundException("Role with name "+role+" not found")
        )).collect(Collectors.toSet()));

        return usersRepository.save(user);
    }

    public LoginResponseDto login(LoginRequestDto dto){

        log.info("Inside login() of AuthService");

        LoginResponseDto responseDto=new LoginResponseDto();

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUserName(), dto.getPassword()));

        UserDetails userDetails =(UserDetails) authenticate.getPrincipal();

        responseDto.setActiveToken(jwtUtil.generateActiveToken(userDetails));
        responseDto.setRefreshToken(jwtUtil.generateRefreshToken(userDetails));
        return responseDto;
    }


    public String generateActiveToken(String refreshToken){
        String userName = jwtUtil.extractUsername(refreshToken);
        UserDetails userDetails = service.loadUserByUsername(userName);
        return jwtUtil.generateActiveToken(userDetails);
    }
}
