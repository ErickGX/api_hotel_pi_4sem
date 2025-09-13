package com.pi.senac.Hotel4ma.model;

import com.pi.senac.Hotel4ma.enums.TipoQuarto;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;



@Entity
public class Quarto extends InstalacaoAlugavel {

    @Enumerated(EnumType.STRING)
    private TipoQuarto tipoQuarto;

    private Integer numeroQuarto;

    //calcula o preço total multiplicando o preco base pelo fator e diarias
    @Override
    protected BigDecimal calcularCustoTotal(int diarias) {
        return getPrecoBase()
                .multiply(BigDecimal.valueOf(tipoQuarto.getFator()))
                .multiply(BigDecimal.valueOf(diarias));
    }
}





