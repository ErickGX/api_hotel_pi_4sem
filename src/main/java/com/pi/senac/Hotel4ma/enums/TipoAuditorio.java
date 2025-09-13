package com.pi.senac.Hotel4ma.enums;

public enum TipoAuditorio {
    ANFITEATRO(2.0), // Estrutura em semicírculo
    TEATRO(3.0), // Layout em estilo teatro
    CONGRESSO(5.0); // Estrutura para grandes eventos (>300)

    private final double fator;

    TipoAuditorio(double fator) {
        this.fator = fator;
    }

    public double getFator() {
        return fator;
    }
}
