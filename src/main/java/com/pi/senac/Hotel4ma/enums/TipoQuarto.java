package com.pi.senac.Hotel4ma.enums;

public enum TipoQuarto {
    //fator de multiplicação baseado em tipo para o valor base da classe
    PRESIDENCIAL(5.0), // Topo da cadeia, máximo luxo
    DELUXE(3.0), // Mais conforto, decoração premium
    EXECUTIVO(2.0), // Pensado para viajantes de negócios
    STANDARD(1.0); // Básico, bom custo-benefício

    private final double fator;

    TipoQuarto(double fator) {
        this.fator = fator;
    }

    public double getFator() {
        return fator;
    }
}
