package com.pi.senac.Hotel4ma.utils;

import java.util.Random;

public class CnpjUtils {

    public static String gerarCnpj() {
        Random random = new Random();
        int[] cnpj = new int[14];

        // Gera os 12 primeiros dígitos (base do CNPJ)
        for (int i = 0; i < 12; i++) {
            cnpj[i] = random.nextInt(10);
        }

        // Primeiro dígito verificador
        int soma = 0;
        int[] pesos1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        for (int i = 0; i < 12; i++) {
            soma += cnpj[i] * pesos1[i];
        }
        int resto = soma % 11;
        cnpj[12] = (resto < 2) ? 0 : 11 - resto;

        // Segundo dígito verificador
        soma = 0;
        int[] pesos2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        for (int i = 0; i < 13; i++) {
            soma += cnpj[i] * pesos2[i];
        }
        resto = soma % 11;
        cnpj[13] = (resto < 2) ? 0 : 11 - resto;

        // Retornar como string sem máscara
        return String.format("%d%d%d%d%d%d%d%d%d%d%d%d%d%d",
                cnpj[0], cnpj[1], cnpj[2], cnpj[3],
                cnpj[4], cnpj[5], cnpj[6], cnpj[7],
                cnpj[8], cnpj[9], cnpj[10], cnpj[11],
                cnpj[12], cnpj[13]
        );

    }


}
