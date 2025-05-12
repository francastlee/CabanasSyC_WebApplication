package com.castleedev.cabanassyc_backend.Security;

import java.io.IOException;
import java.util.Date;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        
        String errorMessage = getCustomErrorMessage(request);
        String jsonResponse = String.format(
            "{\"timestamp\":\"%s\",\"error\":\"%s\"}", 
            new Date().toString(), 
            errorMessage
        );
        
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(jsonResponse);
    }
    
    private String getCustomErrorMessage(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        
        if (authHeader == null) {
            return "Acceso no autorizado: Token no proporcionado";
        }
        
        if (!authHeader.startsWith("Bearer ")) {
            return "Acceso no autorizado: Formato de token inválido";
        }
        
        return "Acceso no autorizado: Token inválido o expirado";
    }
}
