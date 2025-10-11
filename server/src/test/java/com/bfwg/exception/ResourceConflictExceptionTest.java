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
    public void testResourceConflictExceptionSetResourceId() {
        Long id = 2L;
        String message = "Another conflict";

        ResourceConflictException exception = new ResourceConflictException(id, message);
        exception.setResourceId(3L);

        assertEquals(3L, exception.getResourceId());
        assertEquals(message, exception.getMessage());
    }
}

