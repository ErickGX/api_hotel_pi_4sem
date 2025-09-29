package com.pi.senac.Hotel4ma.security.sanitizer;


import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import org.owasp.encoder.Encode;
import java.text.Normalizer;

@Component
public class InputSanitizer {


    /**
     * Sanitiza texto genérico removendo caracteres perigosos,
     * espaços extras e tags HTML.
     */
    @Named("textSanitizer")
    public String sanitizeText(String input) {

        // Normaliza para evitar caracteres invisíveis e homoglyph attacks
        String sanitized = Normalizer.normalize(input, Normalizer.Form.NFKC);

        // Remove caracteres de controle invisíveis
        sanitized = sanitized.replaceAll("\\p{C}", "").trim();

        // Remove tags HTML para evitar XSS
        sanitized = sanitized.replaceAll("<.*?>", "");

        // Remove eventos JS
        sanitized = sanitized.replaceAll("on\\w+\\s*=\\s*['\"].*?['\"]", "");

        // Remove palavra script
        sanitized = sanitized.replaceAll("(?i)script", "");

        // Escapa caracteres especiais para HTML usando OWASP Encoder
        sanitized = Encode.forHtml(sanitized);

        return sanitized;
    }

    /**
     * Sanitiza email removendo espaços e normalizando caracteres
     * (Validação de formato já está na DTO com @Email)
     */
    @Named("emailSanitizer")
    public String sanitizeEmail(String input) {

        String sanitized = Normalizer.normalize(input.trim(), Normalizer.Form.NFKC);
        return Encode.forHtml(sanitized); // Proteção contra XSS
    }

    /**
     * Sanitiza senhas - não remove caracteres,
     * apenas normaliza para evitar injeções indiretas.
     */
    @Named("passwordSanitizer")
    public String sanitizePassword(String input) {

        // Normaliza e mantém tudo sem alterar conteúdo
        return Normalizer.normalize(input, Normalizer.Form.NFKC);
    }

    /**
     * Sanitiza números, remove caracteres não numéricos
     * (usado para CPF, telefone, etc.).
     */
    @Named("numericSanitizer")
    public String sanitizeNumeric(String input) {

        return input.replaceAll("[^0-9]", ""); // Mantém só números
    }

}
