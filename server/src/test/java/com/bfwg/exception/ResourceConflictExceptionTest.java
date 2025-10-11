package com.bfwg.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ResourceConflictExceptionTest {

    @Test
    public void testResourceConflictExceptionWithIdAndMessage() {
        Long id = 1L;
        String message = "Resource conflict";

        ResourceConflictException exception = new ResourceConflictException(id, message);

        assertEquals(id, exception.getResourceId());
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void testResourceConflictExceptionWithMessage() {
        String message = "Resource conflict";

        ResourceConflictException exception = new ResourceConflictException(message);

        assertNull(exception.getResourceId());
        assertEquals(message, exception.getMessage());
    }
}

