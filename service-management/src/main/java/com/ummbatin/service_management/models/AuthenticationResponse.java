package com.ummbatin.service_management.models;

import com.ummbatin.service_management.dtos.UserDto;

public class AuthenticationResponse {
    private String token;
    private UserDto user;

    public AuthenticationResponse(String token, UserDto user) {
        this.token = token;
        this.user = user;
    }
    public AuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}