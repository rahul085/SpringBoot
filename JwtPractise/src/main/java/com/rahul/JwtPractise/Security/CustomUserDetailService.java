package com.rahul.JwtPractise.Security;

import com.rahul.JwtPractise.entity.Users;
import com.rahul.JwtPractise.repository.UsersRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UsersRepository usersRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("Inside loadUserByUsername() of CustomUserDetailService");

        Users user= usersRepository.findByUserName(username).orElseThrow(
                ()-> new UsernameNotFoundException("user with name: "+username+" not found!!")
        );

        Set<SimpleGrantedAuthority> authorities = user.getRoles().stream().map(roles -> new SimpleGrantedAuthority(roles.getRoleName()))
                .collect(Collectors.toSet());


        return new User(user.getUserName(),user.getPassword(),authorities);
    }
}
