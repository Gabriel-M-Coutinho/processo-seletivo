package com.example.login.services;

import com.example.login.models.User;
import com.example.login.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User getUserById(String id){
        Optional<User> user0 =  userRepository.findById(id);

        if(user0.isEmpty()){
            throw new Error("id enviado não existe");
        }
        return user0.get();
    }

}
