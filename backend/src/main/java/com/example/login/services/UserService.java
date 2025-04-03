package com.example.login.services;

import com.example.login.dtos.CreateUserDTO;
import com.example.login.models.User;
import com.example.login.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User getUserById(String id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public User parseDtoToUser(CreateUserDTO userDto) {

        if(userRepository.findByEmail(userDto.email.toLowerCase()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }


        User user = new User();
        user.setName(userDto.name);
        user.setEmail(userDto.email.toLowerCase());


        String encryptedPassword = new BCryptPasswordEncoder().encode(userDto.password);
        user.setPassword(encryptedPassword);

        return user;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

}
