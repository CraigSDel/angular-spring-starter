package com.bfwg.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PublicControllerTest {

    private PublicController publicController;

    @BeforeEach
    public void setUp() {
        publicController = new PublicController();
    }

    @Test
    public void testGetFoo() {
        Map<String, String> result = publicController.getFoo();

        assertNotNull(result);
        assertTrue(result.containsKey("foo"));
        assertEquals("bar", result.get("foo"));
    }
}

