package com.pi.senac.Hotel4ma.mappers;


import com.pi.senac.Hotel4ma.dtos.Espacos.Request.EspacosRequestDTO;
import com.pi.senac.Hotel4ma.dtos.Espacos.Response.EspacosResponseDTO;
import com.pi.senac.Hotel4ma.dtos.Hotel.Response.HotelResumoDTO;
import com.pi.senac.Hotel4ma.model.Espacos;
import com.pi.senac.Hotel4ma.model.Hotel;
import com.pi.senac.Hotel4ma.repository.HotelRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class EspacosMapper {

    @Autowired
    private HotelRepository hotelRepository;


    @Mapping(target = "hotel", source = "id_hotel", qualifiedByName = "mapHotel")
    public abstract Espacos toEntity(EspacosRequestDTO dto);

    @Mapping(source = "hotel", target = "hotel")
    public abstract EspacosResponseDTO toDto(Espacos dto);

    // Esse metodo é chamado automaticamente para mapear o campo hotel
    public abstract HotelResumoDTO toHotelResumoDTO(Hotel hotel);

    public abstract List<EspacosResponseDTO> toList(List<Espacos> espacos);

    //na composicao de mapeamento o espaco tem referencia ao Hotel
    //Responsabilidade do mapper montar a entidade completa e nao a service
    @Named("mapHotel")
    protected Hotel mapHotel(Long idHotel) {
        return hotelRepository.findById(idHotel)
                .orElseThrow(() -> new RuntimeException("Hotel não encontrado"));
    }
}
