package com.pi.senac.Hotel4ma.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2LoginFailureHandler implements AuthenticationFailureHandler {

    private static final Logger log = LoggerFactory.getLogger(OAuth2LoginFailureHandler.class);

    // !! IMPORTANTE !!
    // Defina a URL da SUA página de login no Angular
    private static final String FRONTEND_LOGIN_URL = "http://localhost:4200/login"; // Ajuste conforme necessário

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        log.warn("Falha na autenticação OAuth2: {}", exception.getMessage());

        String errorCode = "login_failed"; // Código de erro padrão

        // Verifica se a exceção é a que lançamos no CustomOidcUserService
        if (exception instanceof OAuth2AuthenticationException oauthEx) {
            OAuth2Error error = oauthEx.getError();
            // Verifica se o código de erro é o que definimos ("email_not_registered")
            if ("email_not_registered".equals(error.getErrorCode())) {
                errorCode = error.getErrorCode(); // Usa nosso código específico
                log.info("Falha específica: Email do Google não cadastrado no sistema.");
            }
        }

        // Constrói a URL de redirecionamento para o front-end,
        // adicionando o código de erro como um parâmetro de consulta
        String redirectUrl = UriComponentsBuilder.fromUriString(FRONTEND_LOGIN_URL)
                .queryParam("error", errorCode) // Ex: .../login?error=email_not_registered
                .build().toUriString();

        // Redireciona o usuário para a página de login do front-end com a mensagem de erro
        response.sendRedirect(redirectUrl);
    }
}