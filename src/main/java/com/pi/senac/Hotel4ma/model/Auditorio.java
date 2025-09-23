package com.pi.senac.Hotel4ma.model;

import com.pi.senac.Hotel4ma.enums.TipoAuditorio;
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
public class Auditorio extends InstalacaoAlugavel{

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAuditorio tipoAuditorio;


    @Override
    protected double getFator() {
         return tipoAuditorio.getFator() ;
    }
}
