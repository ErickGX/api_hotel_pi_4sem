//package com.pi.senac.Hotel4ma.model;
//
//import jakarta.persistence.*;
//import lombok.*;
//import java.time.LocalDate;
//import java.time.temporal.ChronoUnit;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "reserva_hospedagem")
//public class ReservaHospedagem implements Reserva {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "idCliente", nullable = false)
//    private Cliente cliente;
//
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "idFuncionario", nullable = false)
//    private Funcionario funcionario;
//
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "idQuarto", nullable = false)
//    private Quarto quarto;
//
//    @Column(name = "data_checkin", nullable = false)
//    private LocalDate dataCheckin;
//
//    @Column(name = "data_checkout", nullable = false)
//    private LocalDate dataCheckout;
//
//    @Column(length = 20, nullable = false)
//    private String status;
//
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "id_pagamento")
//    private Pagamento pagamento;
//
//    @Column(name = "valor_total", nullable = false)
//    private Double valorTotal;
//
//    @PrePersist
//    @PreUpdate
//    private void calcularValorTotal() {
//        if (quarto != null && quarto.getValorDiaria() != null && dataCheckin != null && dataCheckout != null) {
//            long dias = ChronoUnit.DAYS.between(dataCheckin, dataCheckout);
//            if (dias <= 0) dias = 1;
//            this.valorTotal = quarto.getValorDiaria() * dias;
//        } else {
//            this.valorTotal = 0.0;
//        }
//    }
//
//    @Override
//    public String getStatus() {
//        LocalDate hoje = LocalDate.now();
//
//        if ("Cancelada".equalsIgnoreCase(status)) return "Cancelada";
//        if (hoje.isBefore(dataCheckin)) return "Reservada";
//        if ((hoje.isEqual(dataCheckin) || hoje.isAfter(dataCheckin)) && hoje.isBefore(dataCheckout)) return "Em andamento";
//        if (hoje.isAfter(dataCheckout) || hoje.isEqual(dataCheckout)) return "Finalizada";
//
//        return status;
//    }
//
//    @Override
//    public Double getValorTotal() {
//        return this.valorTotal != null ? this.valorTotal : 0.0;
//    }
//
//    @Override
//    public Boolean verificarDisponibilidade() {
//        if (quarto == null || dataCheckin == null || dataCheckout == null || !dataCheckout.isAfter(dataCheckin)) {
//            return false;
//        }
//
//        String statusAtual = getStatus();
//        return "Cancelada".equalsIgnoreCase(statusAtual) || "Finalizada".equalsIgnoreCase(statusAtual);
//    }
//}