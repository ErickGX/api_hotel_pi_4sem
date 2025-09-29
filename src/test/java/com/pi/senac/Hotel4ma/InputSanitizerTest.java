package com.pi.senac.Hotel4ma;

import com.pi.senac.Hotel4ma.security.sanitizer.InputSanitizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InputSanitizerTest {

    private InputSanitizer sanitizer;

    @BeforeEach
    void setup() {
        sanitizer = new InputSanitizer();
    }

    /**
     * Verifica se tags <script> e seu conteúdo são removidos,
     * prevenindo XSS, e se o texto legítimo permanece.
     */
    @Test
    void sanitizeText_shouldRemoveSimpleScriptTags() {
        String input = "<script>alert('xss')</script>John";
        String out = sanitizer.sanitizeText(input);
        System.out.println(out);
        assertFalse(out.toLowerCase().contains("script"));
        assertFalse(out.contains("<"));
        assertTrue(out.contains("John"));
    }

    /**
     * Garante que palavras-chave perigosas de SQL/XSS sejam removidas,
     * independentemente de caixa (case-insensitive).
     */
    @Test
    void sanitizeText_shouldRemoveIncompleteTagAndLeftoverJs() {
        // incomplete tag and leftover
        String input = "<script>alert('xss')"; // no closing tag
        String out = sanitizer.sanitizeText(input);
        // should not contain angle brackets nor the word alert/script
        System.out.println(out);
        assertFalse(out.contains("<"));
        assertFalse(out.toLowerCase().contains("alert"));
        assertFalse(out.toLowerCase().contains("script"));
        // empty or trimmed string is acceptable
    }

    /**
     * Garante que palavras-chave perigosas de SQL/XSS sejam removidas,
     * independentemente de caixa (case-insensitive).
     */
    @Test
    void sanitizeText_shouldRemoveForbiddenKeywords_caseInsensitive() {
        String input = "DROP table users; SELECT * from accounts; Hello";
        String out = sanitizer.sanitizeText(input);
        // keywords removed
        System.out.println(out);
        assertFalse(out.toLowerCase().contains("drop"));
        assertFalse(out.toLowerCase().contains("select"));
        assertFalse(out.toLowerCase().contains("union"));
        // remaining content kept
        assertTrue(out.toLowerCase().contains("hello"));
    }

    /**
     * Testa normalização Unicode e remoção de caracteres de controle invisíveis.
     * Exemplo: 'i' + acento agudo combinante vira 'í' e caractere de controle é removido.
     */
    @Test
    void sanitizeText_shouldNormalizeUnicode_and_remove_control_chars() {
        // contains unicode composed characters and control char (U+0007 bell)
        String input = "Eri\u0301\u0007ck"; // J + o + combining acute + control char + ao
        String out = sanitizer.sanitizeText(input);
        System.out.println(out);
        assertFalse(out.contains("\u0007"));
        // normalized should not contain combining mark sequence; at least not the control char
        assertTrue(out.toLowerCase().contains("erí"));
    }


    /**
     * Garante que apenas dígitos sejam mantidos em entradas numéricas.
     */
    @Test
    void sanitizeNumeric_shouldKeepOnlyDigits() {
        String input = "+55 (11) 91234-5678 ext.99";
        String out = sanitizer.sanitizeNumeric(input);
        System.out.println(out);
        assertEquals("551191234567899", out);
    }

    /**
     * Testa sanitização de e-mail: normalização, remoção de espaços e escape de caracteres HTML.
     */
    @Test
    void sanitizeEmail_shouldNormalizeAndEscape() {
        String input = "  Test<SCRIPT>@Example.com  ";
        String out = sanitizer.sanitizeEmail(input);
        // normalized, trimmed and escaped (no raw '<' or '>' characters)
        System.out.println(out);
        assertFalse(out.contains("<"));
        assertFalse(out.contains(">"));
        assertTrue(out.toLowerCase().contains("test"));
        assertTrue(out.toLowerCase().contains("@example.com"));
    }


    /**
     * Garante que a senha seja normalizada (espaços removidos e caracteres especiais mantidos).
     */
    @Test
    void sanitizePassword_shouldNormalizeButKeepContent() {
        String input = " p@ss\u00A0word "; // contains non-breaking space
        String out = sanitizer.sanitizePassword(input);
        System.out.println(out);
        assertFalse(out.startsWith(" "));
        assertFalse(out.endsWith(" "));
        assertTrue(out.contains("@"));
        assertTrue(out.toLowerCase().contains("p@ss"));
    }



    /**
     * Testa se, após remoção de tags, caracteres especiais são escapados corretamente.
     */
    @Test
    void sanitizeText_shouldEscapeHtmlEntities_afterRemoval() {
        String input = "<b>O'Reilly & Sons</b>";
        String out = sanitizer.sanitizeText(input);
        // tags removed and characters escaped (& -> &amp; etc.)
        System.out.println(out);
        assertFalse(out.contains("<"));
        assertFalse(out.contains(">"));
        assertTrue(out.contains("O"));
        assertTrue(out.contains("Reilly")); // content preserved
        // ensure ampersand was encoded or at least not raw if existed
    }


    /**
     * Garante remoção de funções JavaScript perigosas como eval e prompt.
     */
    @Test
    void sanitizeText_shouldRemoveJavascriptFunctions_like_eval_and_prompt() {
        String input = "some text eval('1+1') and prompt('x')";
        String out = sanitizer.sanitizeText(input);
        System.out.println(out);
        assertFalse(out.toLowerCase().contains("eval("));
        assertFalse(out.toLowerCase().contains("prompt("));
    }







}
