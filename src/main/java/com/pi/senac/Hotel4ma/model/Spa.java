package com.pi.senac.Hotel4ma.model;


import com.pi.senac.Hotel4ma.enums.TipoSpa;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;

@Entity
public class Spa extends InstalacaoAlugavel {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoSpa tipoSpa;


    @Override
    protected BigDecimal calcularCustoTotal(int horas) {
        return getPrecoBase()
                .multiply(BigDecimal.valueOf(tipoSpa.getFator()))
                .multiply(BigDecimal.valueOf(horas));
    }
}
