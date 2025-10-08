package com.pi.senac.Hotel4ma.dtos.Reserva.Request;

import com.pi.senac.Hotel4ma.enums.StatusReserva;
import com.pi.senac.Hotel4ma.enums.TipoPagamento;
import com.pi.senac.Hotel4ma.exceptions.BadRequestException;
import com.pi.senac.Hotel4ma.exceptions.IllegalArgumentException;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ReservaRequest(
        @NotNull
        TipoPagamento tipoPagamento,
        @NotNull
        StatusReserva statusReserva,
        @NotNull
        LocalDateTime checkIn,
        @NotNull
        LocalDateTime checkOut,
        @NotNull
        Long clienteId,
        @NotNull
        Long funcionarioId,
        @NotNull
        Long instalacaoAlugavelId
) {

    // Este é o construtor compacto. Ele é executado automaticamente.
    public ReservaRequest {
        //Impede que o check-in seja DEPOIS ou IGUAL ao check-out.
        if (checkIn != null && checkOut != null && !checkIn.isBefore(checkOut)) {
            throw new BadRequestException("A data de check-out deve ser posterior à data de check-in.");
        }
    }
}
