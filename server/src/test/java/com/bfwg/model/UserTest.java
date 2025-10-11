package com.bfwg.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
    }

    @Test
    public void testUserGettersAndSetters() {
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password");
        user.setFirstname("Test");
        user.setLastname("User");

        assertEquals(1L, user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals("Test", user.getFirstname());
        assertEquals("User", user.getLastname());
    }

    @Test
    public void testUserAuthorities() {
        Authority authority = new Authority();
        authority.setName(UserRoleName.ROLE_USER);
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authority);

        user.setAuthorities(authorities);

        assertNotNull(user.getAuthorities());
        assertEquals(1, user.getAuthorities().size());
    }

    @Test
    public void testUserDetailsImplementation() {
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user.isEnabled());
    }
}

