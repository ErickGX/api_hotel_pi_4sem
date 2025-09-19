package com.pi.senac.Hotel4ma.mappers;


import com.pi.senac.Hotel4ma.dtos.Administrador.Request.AdminRequestDTO;
import com.pi.senac.Hotel4ma.model.Administrador;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

//transforma em um componente spring em tempo de compilação
@Mapper(componentModel = "spring")
public interface AdministradorMapper {

    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "telefone", source = "telefone")
    @Mapping(target = "senha", source = "senha")

    AdminRequestDTO toDto(Administrador administrador);
    Administrador toEntity(AdminRequestDTO dto);
}
