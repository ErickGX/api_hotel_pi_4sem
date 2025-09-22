package com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.salaoDeConferencia.Response;

import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.dtoGenerics.InstalacaoAlugavelResponseDTO;
import com.pi.senac.Hotel4ma.enums.TipoSalaConferencia;

public record SalaoDeConferenciaResponseDTO(
        TipoSalaConferencia tipoSalaConferencia,
        InstalacaoAlugavelResponseDTO instalacao
) {}
