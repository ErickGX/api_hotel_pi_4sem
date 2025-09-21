package com.pi.senac.Hotel4ma.mappers;

import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.salaoDeConferencia.Request.SalaoRequestDTO;
import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.salaoDeConferencia.Response.SalaoResponseDTO;
import com.pi.senac.Hotel4ma.model.SalaoDeConferencia;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SalaoDeConferenciaMapper {

    public SalaoDeConferencia toEntity(SalaoRequestDTO dto) {
        SalaoDeConferencia salao = new SalaoDeConferencia();

        salao.setNome(dto.instalacao().nome());
        salao.setPrecoBase(dto.instalacao().precoBase());
        salao.setIsDisponivel(dto.instalacao().isDisponivel());
        salao.setDescricao(dto.instalacao().descricao());
        salao.setTipoSalaConferencia(dto.tipoSalaConferencia());

        return salao;
    }

    public void updateEntityFromDTO(SalaoRequestDTO dto, SalaoDeConferencia salao) {
        salao.setNome(dto.instalacao().nome());
        salao.setPrecoBase(dto.instalacao().precoBase());
        salao.setIsDisponivel(dto.instalacao().isDisponivel());
        salao.setDescricao(dto.instalacao().descricao());
        salao.setTipoSalaConferencia(dto.tipoSalaConferencia());
    }

    public SalaoResponseDTO toDTO(SalaoDeConferencia salao) {
        return new SalaoResponseDTO(
                salao.getTipoSalaConferencia(),
                salao.getNome(),
                salao.getPrecoBase(),
                salao.getDescricao(),
                salao.getIsDisponivel(),
                salao.getHotel().getId()
        );
    }

    public List<SalaoResponseDTO> toList(List<SalaoDeConferencia> lista) {
        return lista.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}