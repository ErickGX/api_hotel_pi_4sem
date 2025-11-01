package com.pi.senac.Hotel4ma.dtos.Espacos.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pi.senac.Hotel4ma.dtos.Hotel.Response.HotelResumoDTO;
import com.pi.senac.Hotel4ma.enums.TipoEspacos;

import java.time.LocalDateTime;

public record EspacosResponseDTO(
        Long id,
        String tipoEspacos,
        String descricao,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime dataCadastro,
        HotelResumoDTO hotel
) {
}
