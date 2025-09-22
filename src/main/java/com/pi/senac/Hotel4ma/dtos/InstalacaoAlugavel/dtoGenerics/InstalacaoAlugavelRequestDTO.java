package com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.dtoGenerics;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record InstalacaoAlugavelRequestDTO(
        @Size(min = 3, max = 150)
        @NotBlank
        String nome,

        @NotNull
        @DecimalMin(value = "0.00", inclusive = false)
        BigDecimal precoBase,

        @NotNull
        Boolean isDisponivel,

        @Size(min = 10, max = 500)
        @NotBlank
        String descricao,

        @NotNull
        Long id_hotel
) implements InstalacaoAlugavelDTO{}