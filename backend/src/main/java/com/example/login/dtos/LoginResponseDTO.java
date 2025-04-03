package com.example.login.dtos;

public class LoginResponseDTO {
    private int statusCode;
    private String message;
    private String token;

    public LoginResponseDTO() {}

    public LoginResponseDTO(int statusCode, String message, String token) {
        this.statusCode = statusCode;
        this.message = message;
        this.token = token;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}