package com.pi.senac.Hotel4ma.dtos.Reserva.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public record ReservaResumoDTO(
        Long id,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime checkIn,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime checkOut
) {}
