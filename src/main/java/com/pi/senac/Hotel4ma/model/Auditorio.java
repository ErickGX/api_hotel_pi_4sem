package com.pi.senac.Hotel4ma.model;

import com.pi.senac.Hotel4ma.enums.TipoAuditorio;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;

@Entity
public class Auditorio extends InstalacaoAlugavel{

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAuditorio tipoAuditorio;


    //calcula o preço total multiplicando o preco base pelo fator da categoria e pela diarias
    @Override
    protected BigDecimal calcularCustoTotal(int horas) {
        return getPrecoBase()
                .multiply(BigDecimal.valueOf(tipoAuditorio.getFator()))
                .multiply(BigDecimal.valueOf(horas));
    }
}
