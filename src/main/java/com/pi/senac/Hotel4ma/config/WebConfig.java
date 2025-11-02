package com.pi.senac.Hotel4ma.config; // Ajuste seu pacote

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 🔒 Configuração para permitir requisições APENAS
        //    da sua aplicação Angular rodando localmente.
        registry.addMapping("/api/**") // 1. Aplica APENAS aos endpoints da sua API
                .allowedOrigins("http://localhost:4200") // 2. URL EXATA do seu Angular (sem wildcard)
                .allowedMethods("GET", "POST", "PUT", "PATCH" , "DELETE", "OPTIONS") // 3. Métodos HTTP permitidos
                .allowedHeaders("*"); // 4. Permite todos os headers (incluindo Authorization)

    }
}