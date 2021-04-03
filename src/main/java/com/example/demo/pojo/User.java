package com.example.demo.pojo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class User implements UserDetails {

    private long id = 0;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private ArrayList grantedAuthority;

    public User(long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.grantedAuthority = new ArrayList<>();
    }

    public User() { }

    public long getId() { return id; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) {  firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) {  lastName = lastName; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {  email = email; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthority;
    }

    public void setAuthorities(ArrayList<?> grantedAuthority) { this.grantedAuthority = grantedAuthority; }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
