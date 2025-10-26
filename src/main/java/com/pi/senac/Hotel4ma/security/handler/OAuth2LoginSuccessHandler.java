package com.pi.senac.Hotel4ma.security.handler;

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
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private static final Logger log = LoggerFactory.getLogger(OAuth2LoginSuccessHandler.class);


    // !! IMPORTANTE !!
    // Defina a URL para onde seu front-end espera o callback
    // (Ex: uma página no seu React/Angular que lê o token da URL)
    private static final String FRONTEND_REDIRECT_URL = "http://localhost:4200/auth-callback";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {


        log.info("Sucesso no login com OAuth2 (Google). Gerando token JWT e redirecionando.");

        // 1. Gera o token JWT
        String jwt = jwtService.generateToken(authentication);

        // 2. Constrói a URL de redirecionamento para o front-end,
        //    adicionando o token como um parâmetro de consulta (query param)
        String redirectUrl = UriComponentsBuilder.fromUriString(FRONTEND_REDIRECT_URL)
                .queryParam("token", jwt)
                .build().toUriString();

        // 3. Redireciona o usuário para o front-end
        response.sendRedirect(redirectUrl);
    }
}
