package com.pi.senac.Hotel4ma.model;

import com.pi.senac.Hotel4ma.enums.TipoSalaConferencia;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;

@Entity
public class SalaoDeConferencia extends InstalacaoAlugavel{


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
     private TipoSalaConferencia  tipoSalaConferencia;


    //calcula o preço total multiplicando o preco base pelo fator e diarias
    @Override
    protected BigDecimal calcularCustoTotal(int horas) {
        return getPrecoBase()
                .multiply(BigDecimal.valueOf(tipoSalaConferencia.getFator()))
                .multiply(BigDecimal.valueOf(horas));
    }
}
