package com.example.demo.models;

import java.io.Serializable;

public class CreateUserRequest implements Serializable {

    private String username;
    private String password;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public CreateUserRequest() {
    }

    public CreateUserRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }
}
