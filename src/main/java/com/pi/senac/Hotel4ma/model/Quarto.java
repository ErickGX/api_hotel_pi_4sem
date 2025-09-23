package com.pi.senac.Hotel4ma.model;

import com.pi.senac.Hotel4ma.enums.TipoQuarto;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@Entity
public class Quarto extends InstalacaoAlugavel {

    @Enumerated(EnumType.STRING)
    private TipoQuarto tipoQuarto;

    private String numeroQuarto;


    @Override
    protected double getFator() {
        return tipoQuarto.getFator() ;
    }
}





