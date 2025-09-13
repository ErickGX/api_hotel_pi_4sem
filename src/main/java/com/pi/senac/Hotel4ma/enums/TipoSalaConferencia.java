package com.pi.senac.Hotel4ma.enums;

public enum TipoSalaConferencia {
    BOARDROOM(2.0),     // Estilo mesa de diretoria
    TRAINING(1.6),      // Sala de treinamento
    CO_WORKING(1.4);     // Espaço colaborativo

    private final double fator;

    TipoSalaConferencia(double fator) {
        this.fator = fator;
    }

    public double getFator() {
        return fator;
    }


}
