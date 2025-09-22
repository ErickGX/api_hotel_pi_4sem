package com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.spa.request;

import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.dtoGenerics.InstalacaoAlugavelRequestDTO;
import com.pi.senac.Hotel4ma.enums.TipoSpa;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record SpaRequestDTO(
        @NotNull TipoSpa tipoSpa,
        @Valid InstalacaoAlugavelRequestDTO instalacao
) {}

