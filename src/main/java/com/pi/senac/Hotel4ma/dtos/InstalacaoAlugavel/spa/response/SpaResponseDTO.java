package com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.spa.response;

import com.pi.senac.Hotel4ma.enums.TipoSpa;
import java.math.BigDecimal;

public record SpaResponseDTO(
        Long id,
        String nome,
        TipoSpa tipoSpa,
        BigDecimal precoBase,
        Boolean isDisponivel,
        String descricao
) {}