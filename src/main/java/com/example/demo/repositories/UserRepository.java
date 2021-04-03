package com.example.demo.repositories;

import com.example.demo.models.UpdateUserRequest;
import com.example.demo.pojo.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserRepository {

    private Map<Long, User> users;
    private long latestId = 0;

    public long createUser(String username, String password) {
        User newUser = new User(GenerateUserId(), username, password);
        if(users == null) users = new HashMap<>();
        users.put(newUser.getId(), newUser);
        return newUser.getId();
    }

    public User getUser(String username) {

        for (User user : users.values()) {
         if(user.getUsername().equals(username)) {
             return user;
         }
        }
        return null;
    }

    public Iterable<User> getAll() {
        return users.values();
    }

    public User getUser(long userId) {
        return users.get(userId);
    }

    public User updateUser(long userId, UpdateUserRequest newUserDetails) {
        User userDetails = users.get(userId);
        if(userDetails == null)
            return null;

        userDetails.setFirstName(newUserDetails.getFirstname());
        userDetails.setLastName(newUserDetails.getLastname());
        userDetails.setEmail(newUserDetails.getEmail());
        users.put(userId, userDetails);

        return userDetails;
    }

    public void delete(long userId) {
        users.remove(userId);
    }

    public Boolean userExist(long userId) {
        User userDetails = users.get(userId);
        if(userDetails == null)
            return false;

        return true;
    }

    private long GenerateUserId() {
        return ++latestId;
    }

}
