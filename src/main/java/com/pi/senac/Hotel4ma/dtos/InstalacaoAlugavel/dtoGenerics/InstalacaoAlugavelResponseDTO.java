package com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.dtoGenerics;

import java.math.BigDecimal;

public record InstalacaoAlugavelResponseDTO(
        Long id,
        String nome,
        BigDecimal precoBase,
        String descricao,
        Boolean isDisponivel,
        Long id_hotel,
        String tipoEspaco // Ex: "SALAO", "SPA", "ACADEMIA"
) implements InstalacaoAlugavelDTO {}