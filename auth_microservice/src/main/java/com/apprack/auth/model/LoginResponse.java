package com.apprack.auth.model;

public class LoginResponse {
    private Long id;
    private String username;
    private String createdTimeStamp;
    private String token;
    private String message;

    public LoginResponse(Long id, String username, String createdTimeStamp, String token, String message) {
        this.id = id;
        this.username = username;
        this.createdTimeStamp = createdTimeStamp;
        this.token = token;
        this.message = message;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setCreatedTimeStamp(String createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
