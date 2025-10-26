package com.pi.senac.Hotel4ma.security.jwt;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;


/**
 * Este filtro é executado UMA VEZ por requisição (graças ao OncePerRequestFilter).
 * Sua responsabilidade é verificar se há um token JWT no cabeçalho
 * Authorization e, se for válido, autenticar o usuário no
 * SecurityContextHolder, tornando a aplicação stateless.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private static final Logger log = LoggerFactory.getLogger(JwtAuthorizationFilter.class);
    private static final String HEADER_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {


        //Pega o cabeçalho Authorization da requisição
        Optional<String> token = extractTokenFromHeader(request);

        if (token.isEmpty()){
            // Se não há token, esta não é uma requisição que podemos autenticar.
            // Apenas continua a cadeia de filtros. Se o endpoint for protegido,
            // os filtros posteriores do Spring Security vão bloquear.
            filterChain.doFilter(request, response);
            return;
        }

        //Se um token foi encontrado, tenta validá-lo usando o JwtService
        Optional<Authentication> authentication = jwtService.validateToken(token.get());

        if (authentication.isPresent()) {
            // SUCESSO: O token é válido.
            // Informa ao Spring Security quem é o usuário autenticado.
            // O SecurityContextHolder armazena a autenticação para esta thread
            log.debug("Token JWT válido. Autenticando usuário: {}", authentication.get().getName());
            SecurityContextHolder.getContext().setAuthentication(authentication.get());
        }else {
            // FALHA: O token foi enviado, mas é inválido (expirado, assinatura errada).
            // Limpa o contexto para garantir que não haja um usuário "stale" (antigo).
            log.warn("Token JWT inválido ou expirado recebido.");
            SecurityContextHolder.clearContext();
        }

        //Independentemente do resultado, continua a cadeia de filtros.
        filterChain.doFilter(request, response);

    }


    /**
     * Metodo auxiliar para extrair o token JWT (sem o "Bearer ")
     * do cabeçalho "Authorization".
     */
    private Optional<String> extractTokenFromHeader(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        //VERIFICA SE O CABEÇALHO COMEÇA COM "Bearer "
        if (header != null && header.startsWith(HEADER_PREFIX)) {
            // RETORNA APENAS O TOKEN (remove o "Bearer ")
            return Optional.of(header.substring(HEADER_PREFIX.length()));
        }
        return Optional.empty();
    }
}
