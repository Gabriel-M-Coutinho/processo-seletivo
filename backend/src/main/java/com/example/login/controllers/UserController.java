package com.example.login.controllers;

import com.example.login.dtos.AuthenticationDTO;
import com.example.login.dtos.CreateUserDTO;
import com.example.login.dtos.ErrorResponseDTO;
import com.example.login.dtos.LoginResponseDTO;
import com.example.login.models.User;
import com.example.login.repositories.UserRepository;
import com.example.login.services.TokenService;
import com.example.login.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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

    @Autowired
    private UserService userService;




    @PostMapping("/signin")
    public ResponseEntity<?> createUser(@RequestBody @Valid CreateUserDTO createUserDTO, HttpServletRequest request) {
        try {
            User newUser = userService.parseDtoToUser(createUserDTO);
            User savedUser = userService.saveUser(newUser);

            return ResponseEntity.ok(savedUser);
        } catch (RuntimeException e) {
            ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                    400,
                    "Erro ao criar usuário",
                    e.getMessage()
            );
            errorResponse.setPath(request.getRequestURI());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.getEmail().toLowerCase(), data.getPassword());
            var auth = this.authenticationManager.authenticate(usernamePassword);

            String token = tokenService.generateToken((User) auth.getPrincipal());

            return ResponseEntity.ok(new LoginResponseDTO(
                    200,
                    "Login realizado com sucesso",
                    token
            ));
        } catch (BadCredentialsException e) {
            // Resposta para credenciais inválidas
            ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                    401, // Unauthorized
                    "Falha na autenticação",
                    "Email ou senha incorretos"
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        } catch (Exception e) {
            // Resposta para outros erros de autenticação
            ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                    500,
                    "Erro durante o login",
                    e.getMessage()
            );
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
    @GetMapping("/me")
    public ResponseEntity<?> getProtectedResource(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso negado");
        }

        Optional<User> user = userRepository.findById(userId);
        if(!user.isEmpty()){
              return ResponseEntity.ok(user.get());
        }else {
            ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                    500,
                    "Usuario nao encontrado",
            ""
            );
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }



}
