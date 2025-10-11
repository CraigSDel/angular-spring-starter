package com.bfwg.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExceptionResponseTest {

    @Test
    public void testExceptionResponse() {
        String errorCode = "ERR001";
        String errorMessage = "Error message";

        ExceptionResponse response = new ExceptionResponse();
        response.setErrorCode(errorCode);
        response.setErrorMessage(errorMessage);

        assertEquals(errorCode, response.getErrorCode());
        assertEquals(errorMessage, response.getErrorMessage());
    }

    @Test
    public void testDefaultConstructor() {
        ExceptionResponse response = new ExceptionResponse();

        assertNull(response.getErrorCode());
        assertNull(response.getErrorMessage());
    }
}

