package com.pi.senac.Hotel4ma.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.pi.senac.Hotel4ma.security.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class JwtService {

    private static final Logger log = LoggerFactory.getLogger(JwtService.class);

    // Tempo de expiração do token (ex: 1 hora)
    private static final long EXPIRATION_HOURS = 1L;

    // Algoritmo usado para assinar e verificar os tokens
    private final Algorithm algorithm;

    // Verificador reutilizável
    private final JWTVerifier verifier;

    /**
     * Construtor que injeta as chaves RSA (pública e privada)
     * criadas no meu JwtKeyConfig.
     */
    public JwtService(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
        // A biblioteca da Auth0 usa um objeto Algorithm para ambas as operações
        this.algorithm = Algorithm.RSA256(publicKey, privateKey);

        // Constrói um verificador reutilizável
        this.verifier = JWT.require(this.algorithm).build();
    }


    /**
     * Gera um novo JWT assinado com base no usuário autenticado.
     *
     * @param authentication O objeto de autenticação do Spring (contendo o CustomUserDetails).
     * @return Uma string de token JWT.
     */
    public String generateToken(Authentication authentication) {

        // Pega o seu CustomUserDetails do objeto de autenticação
        if (!(authentication.getPrincipal() instanceof CustomUserDetails userDetails)) {
            throw new IllegalArgumentException("Principal de autenticação não é do tipo CustomUserDetails");
        }

        Instant now = Instant.now();
        Instant expiration = now.plus(EXPIRATION_HOURS, ChronoUnit.HOURS);

        // Converte as roles (GrantedAuthority) para uma Lista de Strings
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        log.info("Gerando token para o usuário: {}", userDetails.getUsername());

        // Cria o token usando a sintaxe da Auth0
        return JWT.create()
                .withSubject(userDetails.getUsername()) // O "Subject" é o email (username)
                .withClaim("roles", roles)
                .withClaim("id_user", userDetails.getIdUsuario())// Adiciona as roles como uma custom claim
                .withIssuedAt(now) // Data de emissão
                .withExpiresAt(expiration) // Data de expiração
                .sign(this.algorithm); // Assina com a chave privada
    }

    /**
     * Valida um token JWT (verifica assinatura, expiração) e, se válido,
     * retorna um objeto de autenticação para o Spring Security.
     *
     * @param token A string do token JWT (sem o "Bearer ").
     * @return Um Optional contendo o objeto Authentication se o token for válido.
     */
    public Optional<Authentication> validateToken(String token) {
        try {
            // Valida a assinatura e decodifica o token
            DecodedJWT decodedJWT = this.verifier.verify(token);

            // Pega os dados de dentro do token
            String email = decodedJWT.getSubject();
            List<String> roles = decodedJWT.getClaim("roles").asList(String.class);

            if (email == null || roles == null) {
                log.warn("Token inválido - 'subject' ou 'roles' não encontrados.");
                return Optional.empty();
            }

            // Converte as strings de "roles" de volta para GrantedAuthority
            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();

            // Cria o objeto de autenticação que o SecurityContextHolder usará
            // Usamos o email como "principal". A senha (credentials) é nula
            // pois estamos em um fluxo stateless (baseado em token).
            Authentication auth = new UsernamePasswordAuthenticationToken(email, null, authorities);
            return Optional.of(auth);

        } catch (JWTVerificationException e) {
            // Se a verificação falhar (assinatura inválida, token expirado, etc.)
            log.warn("Falha na validação do JWT: {}", e.getMessage());
            return Optional.empty();
        }
    }


}
