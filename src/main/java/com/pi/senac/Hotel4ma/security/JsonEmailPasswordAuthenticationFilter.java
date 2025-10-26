package com.pi.senac.Hotel4ma.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pi.senac.Hotel4ma.dtos.Auth.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.ArrayList;

public class JsonEmailPasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;
    private static final Logger log = LoggerFactory.getLogger(JsonEmailPasswordAuthenticationFilter.class);


    public JsonEmailPasswordAuthenticationFilter(ObjectMapper objectMapper, AuthenticationManager authenticationManager) {
        // Ouve apenas em POST /api/auth/login
        super(new AntPathRequestMatcher("/api/auth/login", "POST")); // Endpoint de login via JSON
        this.objectMapper = objectMapper;
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException, IOException {

        log.debug("Tentando autenticação via JSON no endpoint /api/auth/login");

        try {
            // Lê o JSON do corpo e converte para LoginRequest
            LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);

            // Cria o token de autenticação (ainda não autenticado)
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    loginRequest.email(), // Usa o email do DTO
                    loginRequest.senha(), // Usa a senha do DTO
                    new ArrayList<>()
            );

            // Delega ao AuthenticationManager (que usará seu CustomUserDetailsService)
            return getAuthenticationManager().authenticate(authToken);

        } catch (IOException e) {
            log.error("Erro ao ler o JSON do corpo da requisição de login", e);
            throw new RuntimeException("Formato de requisição inválido", e); // Ou lance uma AuthenticationException
        }
    }
}