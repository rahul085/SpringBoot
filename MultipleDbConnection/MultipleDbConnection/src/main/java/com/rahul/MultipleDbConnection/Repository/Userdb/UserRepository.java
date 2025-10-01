package com.rahul.MultipleDbConnection.Repository.Userdb;

import com.rahul.MultipleDbConnection.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
