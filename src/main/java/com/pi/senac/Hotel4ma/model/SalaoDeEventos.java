package com.pi.senac.Hotel4ma.model;

import com.pi.senac.Hotel4ma.enums.TipoAuditorio;
import com.pi.senac.Hotel4ma.enums.TipoSalaoEventos;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;

@Entity
public class SalaoDeEventos extends InstalacaoAlugavel{

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoSalaoEventos tipoSalaoEventos;


    //calcula o preço total multiplicando o preco base pelo fator e diarias
    @Override
    public BigDecimal calcularCustoTotal(int horas) {
        return getPrecoBase()
                .multiply(BigDecimal.valueOf(tipoSalaoEventos.getFator()))
                .multiply(BigDecimal.valueOf(horas));
    }
}
