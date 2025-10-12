package com.pi.senac.Hotel4ma.mappers;


import com.pi.senac.Hotel4ma.dtos.Espacos.Request.EspacosRequestDTO;
import com.pi.senac.Hotel4ma.dtos.Espacos.Response.EspacosResponseDTO;
import com.pi.senac.Hotel4ma.dtos.Hotel.Response.HotelResumoDTO;
import com.pi.senac.Hotel4ma.model.Espacos;
import com.pi.senac.Hotel4ma.model.Hotel;
import com.pi.senac.Hotel4ma.repository.HotelRepository;
import com.pi.senac.Hotel4ma.security.sanitizer.InputSanitizer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class EspacosMapper {

    //Conversao de classe e montagem de classe concreta
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    protected InputSanitizer sanitizer;


    @Mapping(target = "descricao", expression = "java(sanitizer.sanitizeText(dto.descricao()))")
    @Mapping(target = "id", ignore = true) // Impede o mapeamento automático do id do hotel
    @Mapping(target = "hotel", source = "hotel")
    public abstract Espacos toEntity(EspacosRequestDTO dto, Hotel hotel);

    @Mapping(source = "hotel", target = "hotel")
    public abstract EspacosResponseDTO toDto(Espacos dto);

    // Esse metodo é chamado automaticamente para mapear o campo hotel
    public abstract HotelResumoDTO toHotelResumoDTO(Hotel hotel);

    public abstract List<EspacosResponseDTO> toList(List<Espacos> espacos);

}
