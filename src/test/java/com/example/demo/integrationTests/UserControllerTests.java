package com.example.demo.integrationTests;

import com.example.demo.Main;
import com.example.demo.models.AuthenticationRequest;
import com.example.demo.pojo.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTests {

    @LocalServerPort
    private int port;

    private String token;

    TestRestTemplate restTemplate = new TestRestTemplate();


    @Before
    public void setup() throws JsonProcessingException {
        setupTokenAuthentication();
    }

    @Test
    public void testRetrieveStudentCourse() throws JSONException, JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + this.token);

        ParameterizedTypeReference<List<User>> responseType = new ParameterizedTypeReference<List<User>>() {};

        HttpEntity<String> entity = new HttpEntity<String>("",headers);
        ResponseEntity<List<User>> result = restTemplate.exchange(createURL("/users/all"), HttpMethod.GET, entity, responseType);
        Iterable users = Arrays.asList(result.getBody());

        JSONObject json = (JSONObject) ((List<?>) users).get(0);
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
