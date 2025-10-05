package com.pi.senac.Hotel4ma.dtos.Reserva.Request;

import com.pi.senac.Hotel4ma.enums.StatusReserva;
import com.pi.senac.Hotel4ma.enums.TipoPagamento;
import java.time.LocalDateTime;

public record ReservaRequest(
        TipoPagamento tipoPagamento,
        StatusReserva statusReserva,
        LocalDateTime checkIn,
        LocalDateTime checkOut,
        Long clienteId,
        Long funcionarioId,
        Long instalacaoAlugavelId
) {

    private void checkDatas() {
        if (checkIn != null && checkOut != null && checkIn.isAfter(checkOut)) {
            throw new IllegalArgumentException("Data de check-in não pode ser após o check-out.");
        }
    }
}
