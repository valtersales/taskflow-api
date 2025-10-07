package com.taskflow.api.dto;

public class AuthResponse {

    private String token;
    private String type = "Bearer";
    private Long userId;
    private String userName;
    private String email;

    // Constructors
    public AuthResponse() {
    }

    public AuthResponse(String token, Long userId, String userName, String email) {
        this.token = token;
        this.userId = userId;
        this.userName = userName;
        this.email = email;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
