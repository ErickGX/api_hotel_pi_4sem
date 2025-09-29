package com.pi.senac.Hotel4ma.security.sanitizer;


import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import org.owasp.encoder.Encode;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class InputSanitizer {

    // Palavras perigosas para XSS e SQL -Lista agressiva posso ser menos brando , Pode gerar falso positivos
    private static final List<String> FORBIDDEN_KEYWORDS = Arrays.asList(
            "script", "onerror", "onload", "alert", "prompt", "eval",
            "drop", "select", "insert", "delete", "update", "union",
            "--", ";", "/*", "*/", "@@", "@", "char", "nchar", "varchar",
            "nvarchar", "alter", "begin", "cast", "create", "cursor",
            "declare", "exec", "fetch", "kill", "open", "sys", "sysobjects",
            "syscolumns", "table", "information_schema", "where", "admin", "root",
            "<", ">", "\"", "'", "%", "(", ")", "+", "-", "*", "="
    );


    /**
     * Sanitiza texto genérico removendo caracteres perigosos,
     * espaços extras e tags HTML.
     */
    @Named("textSanitizer")
    public String sanitizeText(String input) {

        // Normaliza para evitar caracteres invisíveis e homoglyph attacks
        String sanitized = Normalizer.normalize(input.trim(), Normalizer.Form.NFKC);

        // Remove caracteres de controle invisíveis
        sanitized = sanitized.replaceAll("\\p{C}", "").trim();

        // Remove tags HTML para evitar XSS
        sanitized = sanitized.replaceAll("(?i)<.*?>", "");

        // Remove eventos JS
        sanitized = sanitized.replaceAll("(?i)on\\w+\\s*=\\s*['\"].*?['\"]", "");

//        // Remove palavra script
//        sanitized = sanitized.replaceAll("(?i)script", "");

        // Remove palavras proibidas
        // Escapa cada palavra-chave para uso seguro no regex
        for (String keyword : FORBIDDEN_KEYWORDS) {
            String safeKeyword = Pattern.quote(keyword);
            sanitized = sanitized.replaceAll("(?i)" + safeKeyword, "");
        }

        // Escapa caracteres HTML restantes para evitar XSS
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
        return Normalizer.normalize(input.trim(), Normalizer.Form.NFKC);
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
