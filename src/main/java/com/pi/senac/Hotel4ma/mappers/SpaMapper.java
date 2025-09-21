package com.pi.senac.Hotel4ma.mappers;

import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.dtoGenerics.InstalacaoAlugavelRequestDTO;
import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.dtoGenerics.InstalacaoAlugavelResponseDTO;
import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.spa.request.SpaRequestDTO;
import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.spa.response.SpaResponseDTO;
import com.pi.senac.Hotel4ma.model.Hotel;
import com.pi.senac.Hotel4ma.model.Spa;
import org.springframework.stereotype.Component;

@Component
public class SpaMapper {

    public Spa toEntity(SpaRequestDTO dto) {
        InstalacaoAlugavelRequestDTO base = dto.instalacao();

        Spa spa = new Spa();
        spa.setTipoSpa(dto.tipoSpa());
        spa.setNome(base.nome());
        spa.setPrecoBase(base.precoBase());
        spa.setIsDisponivel(base.isDisponivel());
        spa.setDescricao(base.descricao());

        Hotel hotel = new Hotel();
        hotel.setId(base.id_hotel());
        spa.setHotel(hotel);

        return spa;
    }

    public void updateEntityFromDTO(SpaRequestDTO dto, Spa spa) {
        InstalacaoAlugavelRequestDTO base = dto.instalacao();

        spa.setTipoSpa(dto.tipoSpa());
        spa.setNome(base.nome());
        spa.setPrecoBase(base.precoBase());
        spa.setIsDisponivel(base.isDisponivel());
        spa.setDescricao(base.descricao());

        if (spa.getHotel() == null) {
            spa.setHotel(new Hotel());
        }
        spa.getHotel().setId(base.id_hotel());
    }

    public SpaResponseDTO toResponse(Spa spa) {
        InstalacaoAlugavelResponseDTO base = new InstalacaoAlugavelResponseDTO(
                spa.getId(),
                spa.getNome(),
                spa.getPrecoBase(),
                spa.getDescricao(),
                spa.getIsDisponivel(),
                spa.getHotel().getId(),
                "SPA" // ou spa.getTipoSpa().name() se quiser o valor do enum
        );

        return new SpaResponseDTO(
                spa.getTipoSpa(),
                base
        );
    }
}