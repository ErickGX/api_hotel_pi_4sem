package com.pi.senac.Hotel4ma.mappers;

import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.salaoDeConferencia.Request.SalaoRequestDTO;
import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.salaoDeConferencia.Response.SalaoResponseDTO;
import com.pi.senac.Hotel4ma.model.SalaoDeConferencia;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SalaoDeConferenciaMapper {

    @Mapping(source = "id_hotel", target = "hotel.id")
    SalaoDeConferencia toEntity(SalaoRequestDTO dto);

    @Mapping(source = "hotel.id", target = "id_hotel")
    SalaoResponseDTO toDTO(SalaoDeConferencia entity);

    void updateEntityFromDTO(SalaoRequestDTO dto, @MappingTarget SalaoDeConferencia entity);

    List<SalaoResponseDTO> toList(List<SalaoDeConferencia> entidades);
}