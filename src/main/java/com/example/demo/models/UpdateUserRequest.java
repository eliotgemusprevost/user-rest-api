package com.example.demo.models;

import java.io.Serializable;

public class UpdateUserRequest implements Serializable {

    private String firstname;
    private String lastname;
    private String email;

    public String getFirstname() { return this.firstname; }

    public String getLastname() {
        return this.lastname;
    }

    public String getEmail() {
        return this.email;
    }

    public UpdateUserRequest() {
    }

    public UpdateUserRequest(String firstname, String lastname, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }
}
