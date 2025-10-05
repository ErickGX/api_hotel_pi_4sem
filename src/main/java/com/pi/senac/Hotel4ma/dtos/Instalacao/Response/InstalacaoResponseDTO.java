package com.pi.senac.Hotel4ma.dtos.Instalacao.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pi.senac.Hotel4ma.dtos.Hotel.Response.HotelResumoDTO;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record InstalacaoResponseDTO(
        Long id,
        String nome,
        Boolean isDisponivel,
        String descricao,
        String tipo,
        String numeroQuarto,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime dataCadastro,
        HotelResumoDTO hotel
) {}
