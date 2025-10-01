package com.rahul.MultipleDbConnection.Controller;

import com.rahul.MultipleDbConnection.Entity.User;
import com.rahul.MultipleDbConnection.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> addUsers(@RequestBody  User user){
        return new ResponseEntity<>(userService.addUsers(user), HttpStatus.OK);
    }
}
