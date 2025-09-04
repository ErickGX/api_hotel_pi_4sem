package com.pi.senac.Hotel4ma.validation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Embeddable
public class Cpf extends ValidadorDocumento {

    @JsonCreator
    public Cpf(String numero) {
        this.numero = numero;
    }

    @JsonValue
    public String getNumero() {
        return this.numero;
    }

    @Override
    public boolean validarDoc() {
        if (numero == null || numero.length() != 11 || numero.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int soma = 0, resto;

            // primeiro dígito verificador
            for (int i = 1; i <= 9; i++) {
                soma += Integer.parseInt(numero.substring(i - 1, i)) * (11 - i);
            }
            resto = (soma * 10) % 11;
            if (resto == 10 || resto == 11) resto = 0;
            if (resto != Integer.parseInt(numero.substring(9, 10))) return false;

            // segundo dígito verificador
            soma = 0;
            for (int i = 1; i <= 10; i++) {
                soma += Integer.parseInt(numero.substring(i - 1, i)) * (12 - i);
            }
            resto = (soma * 10) % 11;
            if (resto == 10 || resto == 11) resto = 0;
            if (resto != Integer.parseInt(numero.substring(10, 11))) return false;

            return true;

        } catch (NumberFormatException e) {
            return false;
        }
    }
}