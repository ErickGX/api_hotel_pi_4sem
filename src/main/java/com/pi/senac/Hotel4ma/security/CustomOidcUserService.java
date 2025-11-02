package com.pi.senac.Hotel4ma.security;


import com.pi.senac.Hotel4ma.model.Usuario;
import com.pi.senac.Hotel4ma.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//lida com fluxo de login pelo Google OIDC
@Service
@RequiredArgsConstructor
public class CustomOidcUserService extends OidcUserService {

    private final ClienteRepository clienteRepository;
    private static final Logger log = LoggerFactory.getLogger(CustomOidcUserService.class);


    /**
     * Este metodo é chamado pelo Spring Security após o Google
     * autenticar o usuário com sucesso.
     */
    @Override
    @Transactional(readOnly = true) // Apenas lemos o banco, não escrevemos
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {

        // 1. Carrega o usuário padrão do Google
        OidcUser oidcUser = super.loadUser(userRequest);
        String email = oidcUser.getEmail();

        log.info("Tentativa de login OAuth2 com o email: {}", email);

        // 2. LÓGICA "FIND OR FAIL"
        // Procuramos APENAS na tabela de Clientes por esse email.
        Usuario usuario = clienteRepository.findByEmailAndAtivoTrue(email)
                .map(u -> (Usuario) u) // Converte o Cliente encontrado para Usuario
                .orElseThrow(() -> {

                    // 3. SE NÃO ACHAR, LANÇA UMA EXCEÇÃO
                    // Isso vai parar o fluxo de login e impedir a autenticação.
                    log.warn("Falha no login OAuth2: Email {} não encontrado no banco de dados de Clientes.", email);
                    OAuth2Error error = new OAuth2Error("email_not_registered",
                            "O email " + email + " não está cadastrado. Por favor, faça o cadastro no sistema primeiro.",
                            null);
                    return new OAuth2AuthenticationException(error, error.getDescription());
                });

        // 4. Se o usuário foi encontrado, retorna o CustomUserDetails
        log.info("Login OAuth2 bem-sucedido para o email: {}", email);
        return new CustomUserDetails(usuario);
    }
}
