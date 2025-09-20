package com.pi.senac.Hotel4ma.model;

import com.pi.senac.Hotel4ma.enums.TipoSalaConferencia;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class SalaoDeConferencia extends InstalacaoAlugavel{


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoSalaConferencia  tipoSalaConferencia;


    //calcula o preço total multiplicando o preco base pelo fator e diarias
    @Override
    public BigDecimal calcularCustoTotal(int horas) {
        return getPrecoBase()
                .multiply(BigDecimal.valueOf(tipoSalaConferencia.getFator()))
                .multiply(BigDecimal.valueOf(horas));
    }
}
