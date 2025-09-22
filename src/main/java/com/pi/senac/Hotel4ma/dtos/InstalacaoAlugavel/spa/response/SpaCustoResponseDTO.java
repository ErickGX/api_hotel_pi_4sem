package com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.spa.response;

import java.math.BigDecimal;

public record SpaCustoResponseDTO(
        Long id,
        String nome,
        int horas,
        BigDecimal custoTotal
) {}
