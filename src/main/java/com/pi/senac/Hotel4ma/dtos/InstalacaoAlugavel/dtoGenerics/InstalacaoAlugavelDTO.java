package com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.dtoGenerics;

import java.math.BigDecimal;

public interface InstalacaoAlugavelDTO {
    String nome();
    BigDecimal precoBase();
    Boolean isDisponivel();
    String descricao();
    Long id_hotel();
}


