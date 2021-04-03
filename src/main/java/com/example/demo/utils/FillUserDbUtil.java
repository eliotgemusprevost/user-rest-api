package com.example.demo.utils;

import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FillUserDbUtil {

    @Autowired
    UserRepository userRepository;

    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {

        return args -> {
            repository.createUser("eliotprevost","admin123");
            repository.createUser("buakawHaintongkham","password123");
        };
    }
}
