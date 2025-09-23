package com.pi.senac.Hotel4ma.mappers;

import com.pi.senac.Hotel4ma.dtos.Funcionario.Request.FuncionarioRequest;
import com.pi.senac.Hotel4ma.dtos.Funcionario.Request.FuncionarioUpdateRequest;
import com.pi.senac.Hotel4ma.dtos.Funcionario.Response.FuncionarioResponseDTO;
import com.pi.senac.Hotel4ma.dtos.Hotel.Response.HotelResumoDTO;
import com.pi.senac.Hotel4ma.model.Funcionario;
import com.pi.senac.Hotel4ma.model.Hotel;
import com.pi.senac.Hotel4ma.repository.HotelRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
//abstrata quando preciso compor um objeto que possui outro
public abstract class FuncionarioMapper {

    @Autowired
    private HotelRepository hotelRepository;

    @Mapping(source = "hotel", target = "hotel")
    public abstract FuncionarioResponseDTO toDTO(Funcionario funcionario);

    @Mapping(target = "hotel", source = "id_hotel", qualifiedByName = "mapHotel")
    public abstract Funcionario toEntity(FuncionarioRequest dto);

    // Esse metodo é chamado automaticamente para mapear o campo hotel
    public abstract HotelResumoDTO toHotelResumoDTO(Hotel hotel);

    public abstract List<FuncionarioResponseDTO> toList(List<Funcionario> funcionarios);

    public abstract void updateEntidadeFromDto(FuncionarioUpdateRequest dto, @MappingTarget Funcionario funcionario);

    //na composicao de mapeamento o funcionario tem referencia ao Hotel
    //Responsabilidade do mapper montar a entidade completa e nao a service
    @Named("mapHotel")
    protected Hotel mapHotel(Long idHotel) {
        return hotelRepository.findById(idHotel)
                .orElseThrow(() -> new RuntimeException("Hotel não encontrado"));
    }
}
