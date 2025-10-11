package com.bfwg.rest;

import com.bfwg.exception.ResourceConflictException;
import com.bfwg.model.User;
import com.bfwg.model.UserRequest;
import com.bfwg.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadById() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        when(userService.findById(1L)).thenReturn(user);

        User result = userController.loadById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("testuser", result.getUsername());
    }

    @Test
    public void testLoadAll() {
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setId(1L);
        users.add(user1);

        when(userService.findAll()).thenReturn(users);

        List<User> result = userController.loadAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testResetCredentials() {
        doNothing().when(userService).resetCredentials();

        ResponseEntity<Map> response = userController.resetCredentials();

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertTrue(response.getBody().containsKey("result"));
        assertEquals("success", response.getBody().get("result"));
    }

    @Test
    public void testAddUser_Success() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("newuser");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername("newuser");

        when(userService.findByUsername("newuser")).thenReturn(null);
        when(userService.save(any(UserRequest.class))).thenReturn(savedUser);

        UriComponentsBuilder ucBuilder = UriComponentsBuilder.newInstance();
        ResponseEntity<?> response = userController.addUser(userRequest, ucBuilder);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().getLocation());
    }

    @Test
    public void testAddUser_UsernameExists() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("existinguser");

        User existingUser = new User();
        existingUser.setUsername("existinguser");

        when(userService.findByUsername("existinguser")).thenReturn(existingUser);

        UriComponentsBuilder ucBuilder = UriComponentsBuilder.newInstance();

        assertThrows(ResourceConflictException.class, () ->
                userController.addUser(userRequest, ucBuilder));
    }
}

