package com.bfwg.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserRequestTest {

    @Test
    public void testUserRequestGettersAndSetters() {
        UserRequest userRequest = new UserRequest();
        
        userRequest.setUsername("testuser");
        userRequest.setPassword("password");
        userRequest.setFirstname("Test");
        userRequest.setLastname("User");

        assertEquals("testuser", userRequest.getUsername());
        assertEquals("password", userRequest.getPassword());
        assertEquals("Test", userRequest.getFirstname());
        assertEquals("User", userRequest.getLastname());
    }
}

