package com.bfwg.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTokenStateTest {

    @Test
    public void testUserTokenStateConstructor() {
        String accessToken = "test-token";
        int expiresIn = 3600;

        UserTokenState tokenState = new UserTokenState(accessToken, expiresIn);

        assertEquals(accessToken, tokenState.getAccessToken());
        assertEquals(expiresIn, tokenState.getExpiresIn());
    }

    @Test
    public void testUserTokenStateDefaultConstructor() {
        UserTokenState tokenState = new UserTokenState();

        assertNull(tokenState.getAccessToken());
        assertEquals(0, tokenState.getExpiresIn());
    }
}

