package com.bfwg.security.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class RestAuthenticationEntryPointTest {

    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private AuthenticationException authException;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        restAuthenticationEntryPoint = new RestAuthenticationEntryPoint();
    }

    @Test
    public void testCommence() throws IOException {
        when(authException.getMessage()).thenReturn("Unauthorized");

        restAuthenticationEntryPoint.commence(request, response, authException);

        verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}

