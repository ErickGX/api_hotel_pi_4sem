// src/main/java/com/pi/senac/Hotel4ma/validation/Cnpj.java
package com.pi.senac.Hotel4ma.validation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;

import java.util.InputMismatchException;

@Embeddable
public class Cnpj extends ValidadorDocumento {

    public Cnpj() {
        // construtor padrão necessário para JPA
    }

    @JsonCreator
    public Cnpj(String numero) {
        this.numero = numero == null ? null : numero.replaceAll("[^\\d]", "");  // remove caracteres não numéricos
    }

    @JsonValue
    public String getNumero() {
        return this.numero;
    }

    @Override
    public boolean validarDoc() {
        String cnpj = this.numero;

        if (cnpj == null || cnpj.length() != 14) return false;

        if (cnpj.matches("(\\d)\\1{13}")) return false;

        try {
            int[] peso1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int[] peso2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

            int soma = 0;
            for (int i = 0; i < 12; i++) {
                soma += Character.getNumericValue(cnpj.charAt(i)) * peso1[i];
            }
            int resto = soma % 11;
            char digito1 = (resto < 2) ? '0' : (char) ((11 - resto) + '0');

            soma = 0;
            for (int i = 0; i < 13; i++) {
                soma += Character.getNumericValue(cnpj.charAt(i)) * peso2[i];
            }
            resto = soma % 11;
            char digito2 = (resto < 2) ? '0' : (char) ((11 - resto) + '0');

            return cnpj.charAt(12) == digito1 && cnpj.charAt(13) == digito2;

        } catch (InputMismatchException e) {
            return false;
        }
    }
}
