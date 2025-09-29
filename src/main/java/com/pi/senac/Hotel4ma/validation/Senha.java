package com.pi.senac.Hotel4ma.validation;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Senha {
    //verificar possivel uso das funcoes aqui criadas pelo vagner nas DTOs para regex de senha

    @Column(nullable = false, length = 255, name = "senha")
    private String valor;

    protected Senha(){}

    public Senha(String valor) {
        if (valor == null || !validarSenha(valor)) {
            throw new IllegalArgumentException(
                    "Senha inválida. A senha deve conter no mínimo 4 caracteres, " +
                            "incluindo: uma letra maiúscula, uma letra minúscula, um número " +
                            "e um caractere especial."
            );
        }
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    private boolean validarSenha(String senha) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%?&])[A-Za-z\\d@$!%?&]{4,}$";
        return senha.matches(regex);
    }
}
