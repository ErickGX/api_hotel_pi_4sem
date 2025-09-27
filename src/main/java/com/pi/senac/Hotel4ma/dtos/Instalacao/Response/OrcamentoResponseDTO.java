package com.pi.senac.Hotel4ma.dtos.Instalacao.Response;

import java.math.BigDecimal;

public record OrcamentoResponseDTO(
        String classe,
        String categoria,
        BigDecimal valorFinal
) {}