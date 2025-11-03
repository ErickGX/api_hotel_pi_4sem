package com.pi.senac.Hotel4ma.config; // Ajuste seu pacote

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 2. Injete o caminho do upload-dir que você definiu no .yml
    @Value("${file-storage.upload-dir}")
    private String uploadDir;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 🔒 Configuração para permitir requisições APENAS
        //    da sua aplicação Angular rodando localmente.
        registry.addMapping("/api/**") // 1. Aplica APENAS aos endpoints da sua API
                .allowedOrigins("http://localhost:4200") // 2. URL EXATA do seu Angular (sem wildcard)
                .allowedMethods("GET", "POST", "PUT", "PATCH" , "DELETE", "OPTIONS") // 3. Métodos HTTP permitidos
                .allowedHeaders("*"); // 4. Permite todos os headers (incluindo Authorization)

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Mapeia a URL pública (ex: /uploads/imagem.jpg)
        // para a pasta física no disco (ex: file:./uploads/imagem.jpg)

        // Garantia que o caminho funcione em Windows (com \ ) e Linux (com / )
        String resourcePath = "file:" + uploadDir.replace("\\", "/") + "/";

        registry.addResourceHandler("/uploads/**") //URL publica
                .addResourceLocations(resourcePath); //O local no disco0
    }
}