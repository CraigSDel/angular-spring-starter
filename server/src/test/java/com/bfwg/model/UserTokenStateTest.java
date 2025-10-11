package com.bfwg.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTokenStateTest {

    @Test
    public void testUserTokenStateConstructor() {
        String accessToken = "test-token";
        int expiresIn = 3600;

        UserTokenState tokenState = new UserTokenState(accessToken, expiresIn);

        assertEquals(accessToken, tokenState.getAccess_token());
        assertEquals(Long.valueOf(expiresIn), tokenState.getExpires_in());
    }

    @Test
    public void testUserTokenStateDefaultConstructor() {
        UserTokenState tokenState = new UserTokenState();

        assertNull(tokenState.getAccess_token());
        assertNull(tokenState.getExpires_in());
    }
}

