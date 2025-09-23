package com.pi.senac.Hotel4ma.model;

import com.pi.senac.Hotel4ma.enums.TipoSpa;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@Entity
public class Spa extends InstalacaoAlugavel {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoSpa tipoSpa;


    @Override
    protected double getFator() {
        return tipoSpa.getFator();
    }
}
