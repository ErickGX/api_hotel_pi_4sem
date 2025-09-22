package com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.spa.response;

import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.dtoGenerics.InstalacaoAlugavelResponseDTO;
import com.pi.senac.Hotel4ma.enums.TipoSpa;

public record SpaResponseDTO(
        TipoSpa tipoSpa,
        InstalacaoAlugavelResponseDTO instalacao
) {}