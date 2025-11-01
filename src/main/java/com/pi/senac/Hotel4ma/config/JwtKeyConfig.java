package com.pi.senac.Hotel4ma.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
public class JwtKeyConfig {

    /**
     * Gera um par de chaves RSA (Pública e Privada) uma única vez
     * quando a aplicação inicia. Este par de chaves fica em memória.
     *
     *
     * Em produção, as chaves NUNCA devem ser geradas em tempo de execução.
     * Elas devem ser carregadas de um local seguro (Vault, Variáveis de Ambiente, etc.).
     *
     * @return O KeyPair gerado.
     */
    @Bean
    public KeyPair rsaKeyPair() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048); // Tamanho da chave
            return generator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            // Em um ambiente real, tratar isso de forma mais robusta
            throw new IllegalStateException("Não foi possível gerar o par de chaves RSA", e);
        }
    }

    /**
     * Disponibiliza a Chave Pública RSA como um Bean para ser injetada
     * em outros serviços (no JwtService para verificar tokens).
     */
    @Bean
    public RSAPublicKey rsaPublicKey(KeyPair keyPair) {
        return (RSAPublicKey) keyPair.getPublic();
    }

    /**
     * Disponibiliza a Chave Privada RSA como um Bean para ser injetada
     * em outros serviços (no JwtService para assinar tokens).
     */
    @Bean
    public RSAPrivateKey rsaPrivateKey(KeyPair keyPair) {
        return (RSAPrivateKey) keyPair.getPrivate();
    }
}
