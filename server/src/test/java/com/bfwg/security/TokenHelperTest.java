package com.bfwg.security;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.Keys;
import org.joda.time.DateTimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by fan.jin on 2017-01-08.
 */
public class TokenHelperTest {

    private TokenHelper tokenHelper;
    
    @Mock
    private UserDetailsService userDetailsService;

    @BeforeEach
    public void init() {
        tokenHelper = new TokenHelper();
        DateTimeUtils.setCurrentMillisFixed(20L);
        ReflectionTestUtils.setField(tokenHelper, "EXPIRES_IN", 1);
        ReflectionTestUtils.setField(tokenHelper, "SECRET", "mySecretKeyThatIsLongEnoughForHS256Algorithm");
        ReflectionTestUtils.setField(tokenHelper, "APP_NAME", "test-app");
    }

    @Test
    public void testGenerateToken() {
        String token = tokenHelper.generateToken("testuser");
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    public void testGetUsernameFromToken() {
        DateTimeUtils.setCurrentMillisFixed(System.currentTimeMillis());
        ReflectionTestUtils.setField(tokenHelper, "EXPIRES_IN", 60000);
        String token = tokenHelper.generateToken("testuser");
        String username = tokenHelper.getUsernameFromToken(token);
        assertEquals("testuser", username);
    }

    @Test
    public void testExpiredToken() {
        String token = tokenHelper.generateToken("testuser");
        // Token is created at time 20ms with 1ms expiration
        // So it's already expired
        String username = tokenHelper.getUsernameFromToken(token);
        // With expired token, should return null
        assertNull(username);
    }
}
