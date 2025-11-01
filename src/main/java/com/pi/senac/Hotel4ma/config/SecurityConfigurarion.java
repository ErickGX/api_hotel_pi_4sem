package com.pi.senac.Hotel4ma.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pi.senac.Hotel4ma.security.CustomOidcUserService;
import com.pi.senac.Hotel4ma.security.CustomUserDetailsService;
import com.pi.senac.Hotel4ma.security.JsonEmailPasswordAuthenticationFilter;
import com.pi.senac.Hotel4ma.security.exception.CustomAuthenticationEntryPoint;
import com.pi.senac.Hotel4ma.security.handler.FormLoginSuccessHandler;
import com.pi.senac.Hotel4ma.security.handler.OAuth2LoginFailureHandler;
import com.pi.senac.Hotel4ma.security.handler.OAuth2LoginSuccessHandler;
import com.pi.senac.Hotel4ma.security.jwt.JwtAuthorizationFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;



@Component
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true) //habilita o uso de anotações @Secured e @RolesAllowed nos endpoints
@RequiredArgsConstructor
public class SecurityConfigurarion {


    private final CustomUserDetailsService  customUserDetailsService;
    private final CustomOidcUserService customOidcUserService;
    private final FormLoginSuccessHandler formLoginSuccessHandler;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final JwtAuthorizationFilter jwtAuthFilter;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint; //entry point customizado para tratar erros 401 em requisições sem token
    private final OAuth2LoginFailureHandler oauth2LoginFailureHandler; //handler para lidar com usuarios google não cadastrados





    /**
     * Este é o Bean principal que configura TODA a cadeia de filtros de segurança.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JsonEmailPasswordAuthenticationFilter jsonAuth) throws Exception {
        http
                // 1. DESABILITA CSRF (não necessário para uma API stateless)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())

                // 2. CONFIGURA A POLÍTICA DE SESSÃO COMO STATELESS
                // O Spring Security NÃO criará ou usará sessões HTTP, dependerá apenas das Tokens JWT.
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 3. ADICIONA NOSSO FILTRO JWT
                // Diz ao Spring para usar nosso JwtAuthorizationFilter (Passo 4)
                // ANTES do filtro padrão de login (UsernamePasswordAuthenticationFilter).
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)


                // !!! MUDANÇA PRINCIPAL AQUI !!!
                // Adiciona nosso filtro de login JSON (Passo 3)
                // Ele substitui o filtro de form login padrão
                .addFilterAt(jsonAuth, UsernamePasswordAuthenticationFilter.class)

//                .formLogin(form ->
//                        form
//                                // Define o endpoint que o Spring vai "ouvir" para o login
//                                .loginProcessingUrl("/api/auth/login")
//                                // Diz ao Spring para usar nosso handler customizado (Passo 3)
//                                .successHandler(formLoginSuccessHandler)
//                                // Em caso de falha, retorna um 401 Unauthorized
//                                .failureHandler((req, res, ex) ->
//                                        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED))
//                )
                .oauth2Login(oauth2 ->
                        oauth2
                                // Diz ao Spring para usar nosso serviço para "traduzir" o usuário do Google
                                .userInfoEndpoint(userInfo ->
                                        userInfo.oidcUserService(customOidcUserService)
                                )
                                // Diz ao Spring para usar nosso handler para redirecionar com o JWT
                                .successHandler(oAuth2LoginSuccessHandler)
                                .failureHandler(oauth2LoginFailureHandler)
                )
                // 4. CONFIGURA AS REGRAS DE AUTORIZAÇÃO
                .authorizeHttpRequests(auth ->
                        auth
                                //Permite acesso público à documentação da API
                                .requestMatchers("/v3/api-docs/**","/swagger-ui/**","/swagger-ui.html").permitAll()//Documentação pública da API
                                .requestMatchers("/api/auth/login", "/oauth2/**").permitAll()//Permite acesso público ao endpoint de login e OAuth2
                                .requestMatchers(HttpMethod.GET,"/api/instalacoes").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/instalacoes/orcamento").permitAll()//Lista GET de instalacoes publica para frontend
                                .requestMatchers(HttpMethod.GET, "/api/espacos").permitAll() //Lista GET de espaços publica para frontend
                                .requestMatchers(HttpMethod.POST, "/api/clientes/juridico").permitAll() //rota de criação de cliente juridico é pública
                                .requestMatchers(HttpMethod.POST, "/api/clientes/fisico").permitAll() //rota de criação de cliente fisico é pública
                                .anyRequest().authenticated()//Qualquer coisa abaixo dessa linha é ignorada

                )
                // TRATAMENTO DE EXCEÇÃO PARA STATELESS
                // Se um usuário não autenticado (sem token) tentar acessar
                // um recurso protegido, não redirecionarei para uma página de login.
                // Em vez disso, apenas retorne um 401 Unauthorized.
                .exceptionHandling(exceptions ->
                        exceptions
                                // ENTRY POINT CUSTOMIZADO PARA RETORNAR ApiError NO 401(Erro de autenticação não chega no nivel dos controllers)
                                .authenticationEntryPoint(customAuthenticationEntryPoint)
                );

        return http.build();
    }

    @Bean //remover o prefixo ROLE_ padrão do Spring Security
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); //remover o prefixo ROLE_
    }

    /**
     * Define o AuthenticationManager que o Spring Security usará.
     * Ele é configurado para usar nosso CustomUserDetailsService (para buscar o usuário)
     * e o PasswordEncoder (para verificar a senha).
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }


    /**
     * Cria nosso filtro de login JSON customizado como um Bean
     */
    @Bean
    public JsonEmailPasswordAuthenticationFilter jsonEmailPasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        JsonEmailPasswordAuthenticationFilter filter = new JsonEmailPasswordAuthenticationFilter(
                objectMapper,
                authenticationManager
        );

        filter.setAuthenticationSuccessHandler(formLoginSuccessHandler); // Conecta o handler de sucesso
//        filter.setAuthenticationFailureHandler((req, res, ex) -> // Define o handler de falha
//                res.setStatus(HttpStatus.UNAUTHORIZED.value()));
        return filter;
    }


}
