package com.rahul.JwtPractise.repository;

import com.rahul.JwtPractise.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByUserName(String userName);
}
