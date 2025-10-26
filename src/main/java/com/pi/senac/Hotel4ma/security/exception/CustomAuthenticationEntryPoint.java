package com.pi.senac.Hotel4ma.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pi.senac.Hotel4ma.exceptions.ApiError;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
//Como o 401 Unauthorized é sobre autenticação (não autenticado),
//implementamos AuthenticationEntryPoint para personalizar a resposta
//401 Unauthorized não chega ate os controllers, por isso precisamos de um handler separado
//GlobalHandler não captura erros 401
public class CustomAuthenticationEntryPoint  implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper; // Para converter o ApiError em JSON
    private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        log.warn("Falha na autenticação (não autenticado): {}", authException.getMessage());

        // DTO Padrão de erro padrão para 401
        ApiError errorApi = new ApiError(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                "Não Autorizado",
                "Autenticação necessária para acessar este recurso.", // Mensagem genérica para 401
                request.getRequestURI(),
                null
        );

        // Configura a resposta
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        // Escreve o JSON no corpo da resposta
        response.getWriter().write(objectMapper.writeValueAsString(errorApi));
        response.getWriter().flush();


    }
}
