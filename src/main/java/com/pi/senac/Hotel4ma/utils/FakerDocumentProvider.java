package com.pi.senac.Hotel4ma.utils;

public class FakerDocumentProvider {

    public static String cpf(){
        return CpfUtils.gerarCpf();
    }

    public static String cnpj(){
        return CnpjUtils.gerarCnpj();
    }
}
