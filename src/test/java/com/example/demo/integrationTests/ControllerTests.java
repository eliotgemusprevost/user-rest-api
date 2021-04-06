package com.example.demo.integrationTests;

import com.example.demo.Main;
import com.example.demo.models.AuthenticationRequest;
import com.example.demo.pojo.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerTests {

    @LocalServerPort
    private int port;

    private String token;

    TestRestTemplate restTemplate = new TestRestTemplate();


    @Before
    public void setup() throws JsonProcessingException {
        setupTokenAuthentication();
    }

    @Test
    public void canGetAllUser() {
        // Setup header and information for http call
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + this.token);
        ParameterizedTypeReference<List<User>> responseType = new ParameterizedTypeReference<>() {};
        HttpEntity<String> entity = new HttpEntity<>("",headers);

        // Call the endpoint and retrieve the answer
        ResponseEntity<List<User>> result = restTemplate.exchange(createURL("/users/all"), HttpMethod.GET, entity, responseType);
        Object responseBody = result.getBody();
        Iterable<User> users = (Iterable<User>) responseBody;

        // The total user is hardcoded as  2 by default
        assertTrue(2 == ((List<?>) users).size());
    }

    @Test
    public void canCreateUser() {
        //TODO:implement canCreateUser test
    }

    @Test
    public void canDeleteUser() {
        //TODO:implement canDeleteUser test
    }

    @Test
    public void canUpdateUser() {
        //TODO:implement canUpdateUser test
    }

    @Test
    public void canGetSpecificUser() {
        //TODO:implement canGetSpecificUser test
    }

    @Test
    public void cannotGetSpecificUserThatDoesNotExist() {
        //TODO:implement cannotGetUserThatDoesNotExist test
    }

    @Test
    public void cannotUpdateUserThatDoesNotExist() {
        //TODO:implement cannotUpdateUserThatDoesNotExist test
    }

    @Test
    public void cannotDeleteUserThatDoesNotExist() {
        //TODO:implement cannotDeleteUserThatDoesNotExist test
    }


    private String createURL(String uri) {
        return "http://localhost:" + port + uri;
    }

    private void setupTokenAuthentication() throws JsonProcessingException {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("eliotprevost","admin123");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AuthenticationRequest> entity = new HttpEntity<>(authenticationRequest, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURL("/authenticate"),
                HttpMethod.POST, entity, String.class);

        // Not the most elegant way I admit. Time constraint!
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = mapper.readValue(response.getBody(), Map.class);

        this.token = map.get("token");
    }
}
