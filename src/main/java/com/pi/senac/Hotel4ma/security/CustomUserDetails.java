package com.pi.senac.Hotel4ma.security;

import com.pi.senac.Hotel4ma.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Collection;
import java.util.List;
import java.util.Map;


public class CustomUserDetails implements UserDetails, OidcUser {

    private final Usuario usuario;

    // (Opcional) Armazena os atributos originais do Google
    private Map<String, Object> attributes;
    private OidcIdToken idToken;

    public CustomUserDetails(Usuario usuario) {
        this.usuario = usuario;
    }


    /**
     * Construtor para o FLUXO OAUTH2 (Google Login)
     * (Usado pelo CustomOidcUserService)
     * Este construtor "traduz" o OidcUser para o nosso CustomUserDetails.
     */
//    public CustomUserDetails(Usuario usuario, OidcUser oidcUser) {
//        this.usuario = usuario;
//        this.attributes = oidcUser.getAttributes();
//        this.idToken = oidcUser.getIdToken();
//    }


    // =================================================================
    // MÉTODOS OBRIGATÓRIOS DA INTERFACE UserDetails
    // =================================================================


    //captura a role do usuario e transforma em uma lista de GrantedAuthority
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(usuario.getRole().name()));
    }
    //retorna a senha do usuario
    @Override
    public String getPassword() {
        return usuario.getSenha();
    }
    //retorna o username do usuario (email)
    @Override
    public String getUsername() {
        return usuario.getEmail();
    }


    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }



    // =================================================================
    // MÉTODOS OBRIGATÓRIOS DA INTERFACE OidcUser
    // =================================================================
    @Override
    public Map<String, Object> getAttributes() {
        // Retorna os atributos do Google (se existirem)
        return this.attributes;
    }

    @Override
    public Map<String, Object> getClaims() {
        // Posso simplesmente delegar aos atributos
        return this.attributes;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return null;
    }

    @Override
    public OidcIdToken getIdToken() {
        return this.idToken;
    }

    @Override
    public String getName() {
        return this.usuario.getEmail();
    }


    //capturo o tipo do usuario (Administrador, Funcionario, Cliente)
    //metodo usado no LoginController
    public Object getTipoUsuario() {
        return usuario.getClass().getSimpleName();
    }
}
