package com.example.login.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


public class AuthenticationDTO {

    @Email(message = "E-mail inválido")
    @NotBlank(message = "O e-mail não pode estar em branco")
    private String email;

    @NotBlank(message = "A senha não pode estar em branco")
    private String password;

    public AuthenticationDTO() {}

    public AuthenticationDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
