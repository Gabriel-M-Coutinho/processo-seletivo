package com.example.login.controllers;

import com.example.login.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/")
    public String home() {
        User user = new User("arena","Bulbogc@gmail.com","123123");

        return "Hello Docker World";
    }
    @PostMapping("/signin")
    public ResponseEntity<?> createUser( ){
    return  ResponseEntity.ok("bom dia");

    }
}
