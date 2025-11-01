package com.pi.senac.Hotel4ma.dtos.Reserva.Request;

import com.pi.senac.Hotel4ma.enums.StatusReserva;
import com.pi.senac.Hotel4ma.enums.TipoPagamento;
import com.pi.senac.Hotel4ma.exceptions.BadRequestException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ReservaRequest(
        @NotBlank(message = "Campo tipoPagamento é obrigatório")
        TipoPagamento tipoPagamento,
        @NotBlank(message = "Campo statusReserva é obrigatório")
        StatusReserva statusReserva,
        @NotBlank(message = "Campo checkIn é obrigatório")
        LocalDateTime checkIn,
        @NotBlank(message = "Campo checkOut é obrigatório")
        LocalDateTime checkOut,
        @NotBlank(message = "Campo clienteId é obrigatório")
        Long clienteId,
        @NotEmpty(message = "Não pode estar vazio")
        Long funcionarioId,
        @NotBlank(message = "Campo instalacaoAlugavelId é obrigatório")
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
