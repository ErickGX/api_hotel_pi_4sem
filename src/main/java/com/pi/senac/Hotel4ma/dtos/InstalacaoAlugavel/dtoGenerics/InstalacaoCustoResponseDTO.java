package com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.dtoGenerics;

import java.math.BigDecimal;

public record InstalacaoCustoResponseDTO(
        Long id,
        String nome,
        int horas,
        BigDecimal custoTotal
) {}