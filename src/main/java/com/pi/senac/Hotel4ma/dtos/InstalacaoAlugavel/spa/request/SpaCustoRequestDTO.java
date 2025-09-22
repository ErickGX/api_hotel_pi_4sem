package com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.spa.request;

import com.pi.senac.Hotel4ma.enums.TipoSpa;

import java.math.BigDecimal;

public record SpaCustoRequestDTO(
        TipoSpa tipoSpa,
        String nome,
        BigDecimal precoBase,
        Boolean isDisponivel,
        String descricao,
        Long id_hotel,
        int horas
) {}