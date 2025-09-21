package com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.salaoDeConferencia.Request;

import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.dtoGenerics.InstalacaoAlugavelRequestDTO;
import com.pi.senac.Hotel4ma.enums.TipoSalaConferencia;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record SalaoDeConferenciaRequestDTO(
        @NotNull TipoSalaConferencia tipoSalaConferencia,
        @Valid InstalacaoAlugavelRequestDTO instalacao
) {}