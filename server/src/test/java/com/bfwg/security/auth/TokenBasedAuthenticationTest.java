package com.bfwg.security.auth;

import com.bfwg.model.Authority;
import com.bfwg.model.User;
import com.bfwg.model.UserRoleName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TokenBasedAuthenticationTest {

    private User user;
    private TokenBasedAuthentication authentication;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setUsername("testuser");
        
        Authority authority = new Authority();
        authority.setName(UserRoleName.ROLE_USER);
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authority);
        user.setAuthorities(authorities);

        authentication = new TokenBasedAuthentication(user);
    }

    @Test
    public void testGetAuthorities() {
        assertNotNull(authentication.getAuthorities());
        assertEquals(1, authentication.getAuthorities().size());
    }

    @Test
    public void testGetPrincipal() {
        assertEquals(user, authentication.getPrincipal());
    }

    @Test
    public void testIsAuthenticated() {
        assertFalse(authentication.isAuthenticated());
        
        authentication.setAuthenticated(true);
        assertTrue(authentication.isAuthenticated());
    }

    @Test
    public void testGetToken() {
        String token = "test-token";
        authentication.setToken(token);
        assertEquals(token, authentication.getToken());
    }

    @Test
    public void testGetName() {
        assertEquals("testuser", authentication.getName());
    }
}

