package com.pi.senac.Hotel4ma.enums;

public enum TipoSpa implements FatorMultiplicador {
    //fator de multiplicação baseado em tipo para o valor base da classe
    HIDROTERAPIA(2.0), // Banhos termais, ofurô
    TERAPEUTICO(1.5), // Fisioterapia, recuperação muscular
    DETOX(1.2);  // Limpeza de pele, sauna detox

    private final double fator;  //o valor não pode ser alterado após a inicialização

    TipoSpa(double fator) {
        this.fator = fator;
    }

    @Override
    public double getFator() {
        return fator;
     }

}
