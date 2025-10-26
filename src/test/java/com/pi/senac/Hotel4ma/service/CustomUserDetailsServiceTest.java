package com.pi.senac.Hotel4ma.service;


import com.pi.senac.Hotel4ma.security.CustomUserDetails;
import com.pi.senac.Hotel4ma.security.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomUserDetailsServiceTest {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Test
    void testLoadUserByUsername() {
        String email = "ErickGXX@gmail.com"; // coloque aqui um e-mail existente no banco

        try {
            CustomUserDetails user = (CustomUserDetails) userDetailsService.loadUserByUsername(email);

            System.out.println("Usuário encontrado: " + user.getUsername());
            System.out.println("Authorities: " + user.getAuthorities());
            System.out.println("Classe concreta: " + user.getTipoUsuario());
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
