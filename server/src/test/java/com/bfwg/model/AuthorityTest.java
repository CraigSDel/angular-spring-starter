package com.bfwg.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthorityTest {

    private Authority authority;

    @BeforeEach
    public void setUp() {
        authority = new Authority();
    }

    @Test
    public void testAuthorityGettersAndSetters() {
        authority.setId(1L);
        authority.setName(UserRoleName.ROLE_USER);

        assertEquals(1L, authority.getId());
        assertEquals(UserRoleName.ROLE_USER, authority.getName());
        assertEquals("ROLE_USER", authority.getAuthority());
    }

    @Test
    public void testAuthorityWithAdminRole() {
        authority.setName(UserRoleName.ROLE_ADMIN);

        assertEquals(UserRoleName.ROLE_ADMIN, authority.getName());
        assertEquals("ROLE_ADMIN", authority.getAuthority());
    }
}

