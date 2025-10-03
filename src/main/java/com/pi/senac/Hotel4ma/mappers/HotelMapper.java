package com.pi.senac.Hotel4ma.mappers;


import com.pi.senac.Hotel4ma.dtos.Hotel.Request.HotelRequestDTO;
import com.pi.senac.Hotel4ma.dtos.Hotel.Response.HotelResponseDTO;
import com.pi.senac.Hotel4ma.model.Hotel;
import com.pi.senac.Hotel4ma.security.sanitizer.InputSanitizer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        uses = {InputSanitizer.class, EspacosMapper.class, InstalacaoMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)public interface HotelMapper {


    HotelResponseDTO toDTO(Hotel entity);

    //anotacoes de sanitizacao de segurança - XSS e injeção de SQL
    //qualifiedByName referencia o nome do metodo no sanitizador
    @Mapping(source = "nome", target = "nome" , qualifiedByName = "textSanitizer")
    Hotel toEntity(HotelRequestDTO dto);

    @Mapping(source = "nome", target = "nome" , qualifiedByName = "textSanitizer")
    void updateEntityFromDTO(HotelRequestDTO dto, @MappingTarget Hotel entity);

}
