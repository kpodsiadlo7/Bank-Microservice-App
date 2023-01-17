package com.bankwebapp.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private Logger logger = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

    @Override
    public void commence(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response, final AuthenticationException authException) throws IOException, ServletException {
        logger.error("nie autoryzowany jesteś");

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized User");
    }
}
