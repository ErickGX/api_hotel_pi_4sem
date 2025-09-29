package com.pi.senac.Hotel4ma.mappers;


import com.pi.senac.Hotel4ma.dtos.Administrador.Request.AdminRequestDTO;
import com.pi.senac.Hotel4ma.model.Administrador;
import com.pi.senac.Hotel4ma.security.sanitizer.InputSanitizer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

//transforma em um componente spring em tempo de compilação

@Mapper(componentModel = "spring", uses = InputSanitizer.class)
public interface AdministradorMapper {

    AdminRequestDTO toDto(Administrador administrador);


    //anotacoes de sanitizacao de segurança - XSS e injeção de SQL
    @Mapping(source = "nome", target = "nome" , qualifiedByName = "textSanitizer")
    @Mapping(source = "email", target = "email" , qualifiedByName = "emailSanitizer")
    @Mapping(source = "telefone", target = "telefone" , qualifiedByName = "numericSanitizer")
    @Mapping(source = "senha", target = "senha" , qualifiedByName = "passwordSanitizer")
    @Mapping(source = "cpf", target = "cpf" , qualifiedByName = "numericSanitizer")
    Administrador toEntity(AdminRequestDTO dto);
}
