package com.pi.senac.Hotel4ma.utils;

import java.util.Random;

public class CpfUtils {

    public static String gerarCpf() {
        Random random = new Random();
        int[] cpf = new int[11];

        // Gera os 9 primeiros dígitos
        for (int i = 0; i < 9; i++) {
            cpf[i] = random.nextInt(10);
        }

        // Primeiro dígito verificador
        int soma = 0;
        int peso = 10;
        for (int i = 0; i < 9; i++) {
            soma += cpf[i] * peso--;
        }
        int resto = soma % 11;
        cpf[9] = (resto < 2) ? 0 : 11 - resto;

        // Segundo dígito verificador
        soma = 0;
        peso = 11;
        for (int i = 0; i < 10; i++) {
            soma += cpf[i] * peso--;
        }
        resto = soma % 11;
        cpf[10] = (resto < 2) ? 0 : 11 - resto;

        // Formatar para 000.000.000-00
        return String.format("%d%d%d%d%d%d%d%d%d%d%d",
                cpf[0], cpf[1], cpf[2],
                cpf[3], cpf[4], cpf[5],
                cpf[6], cpf[7], cpf[8],
                cpf[9], cpf[10]);
    }

}
