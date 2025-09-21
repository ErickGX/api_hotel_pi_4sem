package com.pi.senac.Hotel4ma.mappers;

import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.spa.request.SpaCustoRequestDTO;
import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.spa.request.SpaRequestDTO;
import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.spa.response.SpaResponseDTO;
import com.pi.senac.Hotel4ma.model.Hotel;
import com.pi.senac.Hotel4ma.model.Spa;
import org.springframework.stereotype.Component;

@Component
public class SpaMapper {

    public Spa toEntity(SpaRequestDTO dto) {
        Spa spa = new Spa();
        spa.setTipoSpa(dto.tipoSpa());
        spa.setNome(dto.nome());
        spa.setPrecoBase(dto.precoBase());
        spa.setIsDisponivel(dto.isDisponivel());
        spa.setDescricao(dto.descricao());

        // Associa o hotel via ID recebido no DTO
        Hotel hotel = new Hotel();
        hotel.setId(dto.id_hotel());
        spa.setHotel(hotel);

        return spa;
    }

    public Spa toEntity(SpaCustoRequestDTO dto) {
        return toEntity(new SpaRequestDTO(
                dto.tipoSpa(),
                dto.nome(),
                dto.precoBase(),
                dto.isDisponivel(),
                dto.descricao(),
                dto.id_hotel()
        ));
    }

    public SpaResponseDTO toResponse(Spa spa) {
        return new SpaResponseDTO(
                spa.getId(),
                spa.getNome(),
                spa.getTipoSpa(),
                spa.getPrecoBase(),
                spa.getIsDisponivel(),
                spa.getDescricao()
        );
    }

    public void updateEntityFromDTO(Spa spa, SpaRequestDTO dto) {
        spa.setTipoSpa(dto.tipoSpa());
        spa.setNome(dto.nome());
        spa.setPrecoBase(dto.precoBase());
        spa.setIsDisponivel(dto.isDisponivel());
        spa.setDescricao(dto.descricao());

        // Atualiza a associação com o hotel
        Hotel hotel = new Hotel();
        hotel.setId(dto.id_hotel());
        spa.setHotel(hotel);
    }
}