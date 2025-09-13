package com.pi.senac.Hotel4ma.enums;

public enum TipoSalaoEventos {
    CASAMENTO(3.0),
    FESTA_INFANTIL(1.5),
    FORMATURA(2.0),
    CORPORATIVO(3.0);

    private final double fator;

    TipoSalaoEventos(double fator) {
        this.fator = fator;
    }

    public double getFator() {
        return fator;
    }
}
