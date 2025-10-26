package com.pi.senac.Hotel4ma.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pi.senac.Hotel4ma.dtos.Auth.LoginResponse;
import com.pi.senac.Hotel4ma.security.jwt.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class FormLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private static final Logger log = LoggerFactory.getLogger(FormLoginSuccessHandler.class);
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {


            log.info("Sucesso no login com formulário. Gerando token JWT.");

            // 1. Gera o token JWT usando o serviço que criamos
            String jwt =  jwtService.generateToken(authentication);
            // response.setHeader("Authorization", "Bearer " + jwt); // Alternativa: enviar no header

            // 2. Prepara a resposta JSON
            // (Em um projeto real, você usaria um DTO e ObjectMapper,
            // mas isso é o mais simples e direto)

            LoginResponse loginResponse = new LoginResponse(jwt);

            // 3. Define o status da resposta como 200 OK
            response.setStatus(HttpServletResponse.SC_OK);
            // 4. Define o tipo de conteúdo como JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            // 5. Escreve o JSON no corpo da resposta
            response.getWriter().write(objectMapper.writeValueAsString(loginResponse));
            response.getWriter().flush();

    }
}
