package com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.salaoDeConferencia.Response;

import com.pi.senac.Hotel4ma.enums.TipoSalaConferencia;

import java.math.BigDecimal;

public record SalaoResponseDTO(
        TipoSalaConferencia tipoSalaConferencia,
        String nome,
        BigDecimal precoBase,
        String descricao,
        Boolean isDisponivel,
        Long id_hotel
) {}