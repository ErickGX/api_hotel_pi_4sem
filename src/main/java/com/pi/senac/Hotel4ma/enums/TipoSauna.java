package com.pi.senac.Hotel4ma.enums;

public enum TipoSauna {
    //fator de multiplicação baseado em tipo para o valor base da classe
    SECA(1.0),          // Sauna finlandesa
    VAPOR(1.6),         // Sauna úmida/turca
    INFRAVERMELHO(1.8), // Sauna moderna com calor infravermelho
    MISTA(1.4);          // Combina seca e vapor

    private final double fator;

    TipoSauna(double fator) {
        this.fator = fator;
    }
    public double getFator() {
        return fator;
    }
}
