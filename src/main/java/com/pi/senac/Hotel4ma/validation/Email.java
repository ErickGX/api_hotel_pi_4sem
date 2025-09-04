package com.pi.senac.Hotel4ma.validation;

import jakarta.persistence.Column;

public class Email {

    @Column(nullable = false, unique = true, length = 120)
    private String valor;

    public Email(){}

    public Email(String valor) {
        if (valor == null || !validarEmail(valor)) {
            throw new IllegalArgumentException("E-mail inválido: " + valor);
        }
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    private boolean validarEmail(String email) {
         String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(regex);
    }
}