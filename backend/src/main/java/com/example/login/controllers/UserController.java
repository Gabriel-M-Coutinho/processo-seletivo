package com.example.login.controllers;

import com.example.login.dtos.AuthenticationDTO;
import com.example.login.dtos.CreateUserDTO;
import com.example.login.dtos.LoginResponseDTO;
import com.example.login.models.User;
import com.example.login.repositories.UserRepository;
import com.example.login.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/signin")
    public ResponseEntity<?> createUser(@RequestBody @Valid CreateUserDTO createUserDTO){
        Optional<User> userOptional = userRepository.findByEmail(createUserDTO.email);

        if(userOptional.isEmpty()){
            User user = new User(createUserDTO.name,createUserDTO.email,createUserDTO.password);
            userRepository.save(user);
            return ResponseEntity.ok(user);
        }else {
            return ResponseEntity.badRequest().body("Usuario ja existe no banco");
        }
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.getEmail(),data.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        String token = tokenService.generateToken((User) auth.getPrincipal());
        LoginResponseDTO responseDto = new LoginResponseDTO(
            200,"login realizado com sucesso",
                token
        );

        return ResponseEntity.ok().body(responseDto);
    }




}
