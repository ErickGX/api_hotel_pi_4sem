package com.pi.senac.Hotel4ma.model;

import com.pi.senac.Hotel4ma.enums.TipoPagamento;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "pagamento")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pagamento", nullable = false)
    private TipoPagamento tipoPagamento;

    @Column(nullable = false)
    private BigDecimal valor;

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