package com.pi.senac.Hotel4ma.dtos.Hotel.Response;

import com.pi.senac.Hotel4ma.dtos.Espacos.Response.EspacosResponseDTO;
import com.pi.senac.Hotel4ma.dtos.Instalacao.Response.InstalacaoResponseDTO;
import java.util.List;

public record HotelResponseDTO(
        Long id,
        String nome,
        List<EspacosResponseDTO> espacos,
        List<InstalacaoResponseDTO> instalacaoAlugaveis
) {
}
