package com.example.login.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class CreateUserDTO {
    @NotNull
    @Email
    public String email;
    @NotNull
    public String password;
    @NotNull
    public String name;

    public CreateUserDTO(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }


}
