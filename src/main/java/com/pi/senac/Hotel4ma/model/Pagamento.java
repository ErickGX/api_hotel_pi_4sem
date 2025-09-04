package com.pi.senac.Hotel4ma.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pagamento")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "forma_pagamento", nullable = false, length = 30)
    private String formaPagamento;

    @Column(nullable = false)
    private Double valor;

    @Column(name = "data_pagamento", nullable = false)
    private LocalDate dataPagamento;

    @Column(name = "status_pagamento", nullable = false, length = 20)
    private String status;

    @OneToOne
    @JoinColumn(name = "id_reserva_sala")
    private ReservaSala reservaSala;

    @OneToOne
    @JoinColumn(name = "id_reserva_hospedagem")
    private ReservaHospedagem reservaHospedagem;
}