package com.pi.senac.Hotel4ma.webConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    //Mudar para true para liberar tudo (modo dev total)
    private static final boolean LIBERAR_TOTAL = false;


    public void addCorsMappings(CorsRegistry registry) {
        if (LIBERAR_TOTAL) {
            // 🔓 Liberação total (qualquer origem)
            registry.addMapping("/**")
                    .allowedOriginPatterns("*")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*")
                    .allowCredentials(true);
        } else {
            // 🔒 Liberação apenas para o frontend local (Angular)
            registry.addMapping("/**")
                    .allowedOrigins("http://localhost:4200")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*")
                    .allowCredentials(true);
        }
    }
}
