package com.example.demo.controllers;

import com.example.demo.models.CreateUserRequest;
import com.example.demo.models.UpdateUserRequest;
import com.example.demo.pojo.User;
import com.example.demo.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable long userId) {

        return Optional
                .ofNullable( userRepository.getUser(userId) )
                .map( userFound -> ResponseEntity.ok().body(userFound) )          //200 OK
                .orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<User>> getUsers() {
        return new ResponseEntity<>(userRepository.getAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Long> createUser(@RequestBody CreateUserRequest requestBody)
    {
        String requestedUsername = requestBody.getUsername();
        String requestedPassword = requestBody.getPassword();

        if(requestedUsername == null || requestedUsername.isEmpty()
                || requestedPassword == null || requestedPassword.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        Long createdId = userRepository.createUser(requestBody.getUsername(),requestBody.getPassword());
        return new ResponseEntity<>(createdId, HttpStatus.OK);
    }

    @PutMapping(path="/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody UpdateUserRequest user)
    {
        return Optional
                .ofNullable( userRepository.updateUser(userId, user) )
                .map( userUpdated -> ResponseEntity.ok().body(userUpdated) )
                .orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @DeleteMapping(path="/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long userId)
    {
        if(!userRepository.userExist(userId))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        userRepository.delete(userId);
        return new ResponseEntity<>( HttpStatus.OK);
    }

}