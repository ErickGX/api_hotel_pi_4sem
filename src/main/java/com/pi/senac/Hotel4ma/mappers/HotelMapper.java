package com.pi.senac.Hotel4ma.mappers;


import com.pi.senac.Hotel4ma.dtos.Hotel.Request.HotelRequestDTO;
import com.pi.senac.Hotel4ma.dtos.Hotel.Response.HotelResponseDTO;
import com.pi.senac.Hotel4ma.model.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface HotelMapper {

    Hotel toEntity(HotelRequestDTO dto);

    HotelResponseDTO toDTO(Hotel entity);
}
