package com.pi.senac.Hotel4ma.dtos.Instalacao.Response;

import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;

public record OrcamentoResponseDTO(
        @NotBlank(message = "Campo classe é obrigatório")
        String classe,
        @NotBlank(message = "Campo categoria é obrigatório")
        String categoria,
        @NotBlank(message = "Campo valorFinal é obrigatório")
        BigDecimal valorFinal
) {}