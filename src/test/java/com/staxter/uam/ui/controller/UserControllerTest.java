package com.staxter.uam.ui.controller;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.staxter.uam.dto.User;
import com.staxter.uam.repository.UserEntity;
import com.staxter.uam.repository.UserRepository;
import com.staxter.uam.service.UserService;
import com.staxter.uam.ui.model.request.UserRegistrationRequest;
import com.staxter.uam.ui.model.response.UserRegistrationResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;



@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
	
	@Autowired                           
    private MockMvc mockMvc;  
                                                 
    @MockBean                           
    private UserService userService; 
   
    @Autowired
    private ObjectMapper objectMapper;
    
    private UserRegistrationRequest userDetails;
    private User user;
    private User user2;
    @BeforeEach                                           
    void setUp() {
    	
    	this.userDetails=new UserRegistrationRequest();
    	this.userDetails.setFirstName("Veena");
    	this.userDetails.setLastName("Anil Kumar");
    	this.userDetails.setUserName("VNAKLKUMA");
    	this.userDetails.setPassword("PASS12345");
    	
    	this.user=new User();
    	this.user.setFirstName("Veena");
    	this.user.setLastName("Anil Kumar");
    	this.user.setUserName("VNAKLKUMA");
    	this.user.setPlainTextPassword("PASS12345");
    	this.user.setId("1L");
    	
    }	
    @Test
    void shouldCreateNewUser() throws Exception {
     given(userService.createUser(any())).willReturn(user);
     //test
          this.mockMvc.perform(post("/userservice/register")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(userDetails)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(user.getId())))
                .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
                .andExpect(jsonPath("$.userName", is(user.getUserName())))
        ;
    }
}
