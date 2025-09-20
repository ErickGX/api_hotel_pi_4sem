package com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.salaoDeConferencia.Response;

import java.math.BigDecimal;

public record SalaoCustoResponseDTO(
        Long id,
        String nome,
        int horas,
        BigDecimal custoTotal
) {}