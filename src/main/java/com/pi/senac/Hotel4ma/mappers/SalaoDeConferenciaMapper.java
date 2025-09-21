package com.pi.senac.Hotel4ma.mappers;

import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.dtoGenerics.InstalacaoAlugavelRequestDTO;
import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.dtoGenerics.InstalacaoAlugavelResponseDTO;
import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.salaoDeConferencia.Request.SalaoDeConferenciaRequestDTO;
import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.salaoDeConferencia.Response.SalaoDeConferenciaResponseDTO;
import com.pi.senac.Hotel4ma.model.Hotel;
import com.pi.senac.Hotel4ma.model.SalaoDeConferencia;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SalaoDeConferenciaMapper {

    public SalaoDeConferencia toEntity(SalaoDeConferenciaRequestDTO dto) {
        InstalacaoAlugavelRequestDTO base = dto.instalacao();

        SalaoDeConferencia salao = new SalaoDeConferencia();
        salao.setNome(base.nome());
        salao.setPrecoBase(base.precoBase());
        salao.setIsDisponivel(base.isDisponivel());
        salao.setDescricao(base.descricao());
        salao.setTipoSalaConferencia(dto.tipoSalaConferencia());

        Hotel hotel = new Hotel();
        hotel.setId(base.id_hotel());
        salao.setHotel(hotel);

        return salao;
    }

    public void updateEntityFromDTO(SalaoDeConferenciaRequestDTO dto, SalaoDeConferencia salao) {
        InstalacaoAlugavelRequestDTO base = dto.instalacao();

        salao.setNome(base.nome());
        salao.setPrecoBase(base.precoBase());
        salao.setIsDisponivel(base.isDisponivel());
        salao.setDescricao(base.descricao());
        salao.setTipoSalaConferencia(dto.tipoSalaConferencia());

        if (salao.getHotel() == null) {
            salao.setHotel(new Hotel());
        }
        salao.getHotel().setId(base.id_hotel());
    }

    public SalaoDeConferenciaResponseDTO toDTO(SalaoDeConferencia salao) {
        InstalacaoAlugavelResponseDTO base = new InstalacaoAlugavelResponseDTO(
                salao.getId(),
                salao.getNome(),
                salao.getPrecoBase(),
                salao.getDescricao(),
                salao.getIsDisponivel(),
                salao.getHotel().getId(),
                "SALAO" // ou salao.getTipoSalaConferencia().name() se quiser o tipo específico
        );

        return new SalaoDeConferenciaResponseDTO(
                salao.getTipoSalaConferencia(),
                base
        );
    }

    public List<SalaoDeConferenciaResponseDTO> toList(List<SalaoDeConferencia> lista) {
        return lista.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}