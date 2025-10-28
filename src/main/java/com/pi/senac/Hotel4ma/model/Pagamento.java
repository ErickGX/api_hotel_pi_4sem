package com.pi.senac.Hotel4ma.model;

import com.pi.senac.Hotel4ma.enums.TipoPagamento;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@SQLDelete(sql = "UPDATE {h-schema}pagamento SET ativo = false WHERE id = ?") // 1. Intercepta o DELETE
@SQLRestriction(value = "ativo = true") // 2. Garante que UPDATEs também respeitem o filtro
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pagamento", nullable = false)
    private TipoPagamento tipoPagamento;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal valorTotal;

    @Column(nullable = false)
    private boolean ativo = true;

    @CreationTimestamp
    @Column(name = "hora_pagamento", nullable = false)
    private LocalDateTime horaPagamento;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cliente") //FK na tabela cliente
    private Cliente cliente; // sempre existe

    @OneToOne(optional = false)
    @JoinColumn(name = "reserva_id", nullable = false, unique = true) // FK
    private Reserva reserva;

}