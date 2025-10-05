package com.pi.senac.Hotel4ma.dtos.Funcionario.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pi.senac.Hotel4ma.dtos.Hotel.Response.HotelResumoDTO;
import java.time.LocalDateTime;

public record FuncionarioResponseDTO(
        Long id,
        String nome,
        String email,
        String telefone,
        String cargo,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime dataCadastro,
        HotelResumoDTO hotel
) {
}
