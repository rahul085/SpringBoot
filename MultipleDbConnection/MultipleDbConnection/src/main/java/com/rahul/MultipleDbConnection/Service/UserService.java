package com.rahul.MultipleDbConnection.Service;

import com.rahul.MultipleDbConnection.Entity.User;
import com.rahul.MultipleDbConnection.Repository.Userdb.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User addUsers(User user){
        return userRepository.save(user);

    }
}
