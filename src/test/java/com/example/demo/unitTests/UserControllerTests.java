package com.example.demo.unitTests;

import com.example.demo.controllers.UserController;
import com.example.demo.models.CreateUserRequest;
import com.example.demo.models.UpdateUserRequest;
import com.example.demo.pojo.User;
import com.example.demo.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class UserControllerTests {

    private MockMvc mockMvc;
    private static final UserRepository userRepository = mock(UserRepository.class);

    @InjectMocks
    private UserController controller;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        setupUserRepository();
    }

    @Test
    public void getUser_UserExists_ReturnStatusOK200()throws Exception{
        MvcResult mvcResult = mockMvc.perform(get("/users/{userId}", 1))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void getUser_UserNotExists_ReturnStatusNotFound404()throws Exception{
        MvcResult mvcResult = mockMvc.perform(get("/users/{userId}", 3))
                .andExpect(status().isNotFound()).andReturn();
    }

    @Test
    public void getAllUser_NoParticularState_ReturnStatusOK200()throws Exception{
        MvcResult mvcResult = mockMvc.perform(get("/users/all"))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void createUser_correctValues_ReturnStatusOK200()throws Exception{
        // Arrange
        CreateUserRequest createUserRequest = new CreateUserRequest("username","password");

        // Act
        // Assert
        MvcResult mvcResult = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(createUserRequest)))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void createUser_noUsername_ReturnStatusBadRequest400()throws Exception{
        // Arrange
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setPassword("password");

        // Act
        // Assert
        MvcResult mvcResult = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(createUserRequest)))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void createUser_noPassword_ReturnStatusBadRequest400()throws Exception{
        // Arrange
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("username");

        // Act
        // Assert
        MvcResult mvcResult = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(createUserRequest)))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void updateUser_userExist_ReturnStatusOK200()throws Exception{
        // Arrange
        CreateUserRequest updateUserRequest = new CreateUserRequest();

        // Act
        // Assert
        MvcResult mvcResult = mockMvc.perform(put("/users/{userId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updateUserRequest)))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void updateUser_userDontExist_ReturnStatusNotFound404()throws Exception{
        // Arrange
        CreateUserRequest updateUserRequest = new CreateUserRequest();

        // Act
        // Assert
        MvcResult mvcResult = mockMvc.perform(put("/users/{userId}", 3)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updateUserRequest)))
                .andExpect(status().isNotFound()).andReturn();
    }

    @Test
    public void deleteUser_userExist_ReturnStatusOK200()throws Exception{
        MvcResult mvcResult = mockMvc.perform(delete("/users/{userId}", 1))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void deleteUser_userDontExist_ReturnStatusNotFound404()throws Exception{
        MvcResult mvcResult = mockMvc.perform(delete("/users/{userId}", 3))
                .andExpect(status().isNotFound()).andReturn();
    }

    private void setupUserRepository() {
        Long idUserExists = new Long(1);
        Long idUserNoExists = new Long(3);
        User userTest = new User(1, "username","password123");

        doReturn(userTest).when(userRepository).getUser(idUserExists);
        doReturn(null).when(userRepository).getUser(idUserNoExists);
        doReturn(userTest).when(userRepository).updateUser(Mockito.eq(idUserExists), Mockito.any(UpdateUserRequest.class));
        doReturn(null).when(userRepository).updateUser(Mockito.eq(idUserNoExists), Mockito.any(UpdateUserRequest.class));
        doReturn(null).when(userRepository).updateUser(Mockito.eq(idUserNoExists), Mockito.any(UpdateUserRequest.class));
        doReturn(true).when(userRepository).userExist(Mockito.eq(idUserExists));
        doReturn(false).when(userRepository).userExist(Mockito.eq(idUserNoExists));
    }

}
