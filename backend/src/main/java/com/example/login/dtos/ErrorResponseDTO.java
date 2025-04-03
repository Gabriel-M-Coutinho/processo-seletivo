package com.example.login.dtos;

public class ErrorResponseDTO {
    private int status;
    private String message;
    private String error;
    private String path;
    private long timestamp;

    // Construtor
    public ErrorResponseDTO(int status, String message, String error) {
        this.status = status;
        this.message = message;
        this.error = error;
        this.timestamp = System.currentTimeMillis();
    }

    // Getters (os setters podem ser omitidos se não forem necessários)
    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}