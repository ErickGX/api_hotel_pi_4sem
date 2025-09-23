package com.pi.senac.Hotel4ma.model;

import com.pi.senac.Hotel4ma.enums.TipoSalaConferencia;
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
public class SalaoDeConferencia extends InstalacaoAlugavel {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoSalaConferencia tipoSalaConferencia;


    @Override
    protected double getFator() {
        return tipoSalaConferencia.getFator();
    }
}