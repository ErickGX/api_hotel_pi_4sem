package com.pi.senac.Hotel4ma.dtos.Reserva.Request;

import com.pi.senac.Hotel4ma.enums.StatusReserva;
import com.pi.senac.Hotel4ma.enums.TipoPagamento;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReservaRequest(
        TipoPagamento tipoPagamento,
        StatusReserva statusReserva,
        LocalDate checkIn,
        LocalDate checkOut,
        Long clienteId,
        Long funcionarioId,
        Long instalacaoAlugavelId
) {
}
