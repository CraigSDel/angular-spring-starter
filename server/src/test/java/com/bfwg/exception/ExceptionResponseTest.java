package com.bfwg.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExceptionResponseTest {

    @Test
    public void testExceptionResponse() {
        String message = "Error message";

        ExceptionResponse response = new ExceptionResponse(message);

        assertEquals(message, response.getErrorMessage());
    }
}

