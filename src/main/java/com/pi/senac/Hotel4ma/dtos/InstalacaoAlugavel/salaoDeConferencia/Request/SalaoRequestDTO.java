package com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.salaoDeConferencia.Request;

import com.pi.senac.Hotel4ma.enums.TipoSalaConferencia;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record SalaoRequestDTO(
        @NotNull TipoSalaConferencia tipoSalaConferencia,
        @NotNull InstalacaoRequestDTO instalacao

) {}