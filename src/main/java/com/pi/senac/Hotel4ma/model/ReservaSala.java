package com.pi.senac.Hotel4ma.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reserva_sala")
public class ReservaSala implements Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idCliente", nullable = false)
    @JsonBackReference
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idFuncionario", nullable = false)
    @JsonBackReference
    private Funcionario funcionario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idSala", nullable = false)
    @JsonBackReference
    private Sala sala;

    @Column(name = "data_reserva", nullable = false)
    private LocalDate dataReserva;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fim", nullable = false)
    private LocalTime horaFim;

    @Column(length = 20, nullable = false)
    private String status;

    @Column(name = "valor_total", nullable = false)
    private Double valorTotal;

    @Column(length = 200)
    private String finalidade;

    @Column(length = 100)
    private String responsavel;

    @Column(name = "numero_sala")
    private Integer numeroSala;

    @OneToOne(mappedBy = "reservaSala", cascade = CascadeType.ALL)
    private Pagamento pagamento;

    @PrePersist
    @PreUpdate
    private void calcularValorTotal() {
        if (sala != null && sala.getValorHora() != null && horaInicio != null && horaFim != null) {
            long horas = ChronoUnit.HOURS.between(horaInicio, horaFim);
            if (horas <= 0) horas = 1;
            this.valorTotal = sala.getValorHora() * horas;
        } else {
            this.valorTotal = 0.0;
        }
    }

    @Override
    public String getStatus() {
        LocalDate hoje = LocalDate.now();

        if ("Cancelada".equalsIgnoreCase(status)) return "Cancelada";
        if (hoje.isBefore(dataReserva)) return "Reservada";
        if (hoje.isEqual(dataReserva)) return "Em andamento";
        if (hoje.isAfter(dataReserva)) return "Finalizada";

        return status;
    }

    @Override
    public Double getValorTotal() {
        return valorTotal != null ? valorTotal : 0.0;
    }

    @Override
    public Boolean verificarDisponibilidade() {
        return sala != null &&
                dataReserva != null &&
                horaInicio != null &&
                horaFim != null &&
                horaFim.isAfter(horaInicio);
    }
}