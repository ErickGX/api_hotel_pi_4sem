package com.pi.senac.Hotel4ma.mappers;

import com.pi.senac.Hotel4ma.dtos.Cliente.Request.ClienteFisicoRequest;
import com.pi.senac.Hotel4ma.dtos.Cliente.Request.ClienteJuridicoRequest;
import com.pi.senac.Hotel4ma.dtos.Cliente.Request.ClienteUpdateRequest;
import com.pi.senac.Hotel4ma.dtos.Cliente.Response.ClienteResponseDTO;
import com.pi.senac.Hotel4ma.model.Cliente;
import com.pi.senac.Hotel4ma.model.ClienteFisico;
import com.pi.senac.Hotel4ma.model.ClienteJuridico;
import com.pi.senac.Hotel4ma.security.sanitizer.InputSanitizer;
import org.mapstruct.*;
import java.util.List;

//transforma em um componente spring em tempo de compilação
@Mapper(componentModel = "spring", uses = InputSanitizer.class) //interface usa o sanitizador com USES
public interface ClienteMapper {

    //Mapeia os campos comuns de ClienteFisico e ClienteJuridico para ClienteResponseDTO
    //expression permite usar codigo java para mapear campos especificos
    @Mapping(target = "cpf", expression = "java(cliente instanceof ClienteFisico ? ((ClienteFisico) cliente).getCpf() : null)")
    @Mapping(target = "cnpj", expression = "java(cliente instanceof ClienteJuridico ? ((ClienteJuridico) cliente).getCnpj() : null)")
    @Mapping(target = "tipoCliente", expression = "java(cliente instanceof ClienteFisico ? TipoCliente.FISICA : cliente instanceof ClienteJuridico ? TipoCliente.JURIDICA : null)")
    ClienteResponseDTO toDTO(Cliente cliente);

    List<ClienteResponseDTO> toList(List<Cliente> clientes);


    //anotacoes de sanitizacao de segurança - XSS e injeção de SQL
    //qualifiedByName referencia o nome do metodo no sanitizador
    @Mapping(source = "nome", target = "nome" , qualifiedByName = "textSanitizer")
    @Mapping(source = "email", target = "email" , qualifiedByName = "emailSanitizer")
    @Mapping(source = "telefone", target = "telefone" , qualifiedByName = "numericSanitizer")
    @Mapping(source = "senha", target = "senha" , qualifiedByName = "passwordSanitizer")
    @Mapping(source = "endereco", target = "endereco" , qualifiedByName = "textSanitizer")
    @Mapping(source = "cpf", target = "cpf" , qualifiedByName = "numericSanitizer")
    ClienteFisico toEntity(ClienteFisicoRequest dto);




    @Mapping(source = "nome", target = "nome" , qualifiedByName = "textSanitizer")
    @Mapping(source = "email", target = "email" , qualifiedByName = "emailSanitizer")
    @Mapping(source = "telefone", target = "telefone" , qualifiedByName = "numericSanitizer")
    @Mapping(source = "senha", target = "senha" , qualifiedByName = "passwordSanitizer")
    @Mapping(source = "endereco", target = "endereco" , qualifiedByName = "textSanitizer")
    @Mapping(source = "cnpj", target = "cnpj" , qualifiedByName = "numericSanitizer")
    ClienteJuridico toEntity(ClienteJuridicoRequest dto);


    @Mapping(source = "nome", target = "nome" , qualifiedByName = "textSanitizer")
    @Mapping(source = "email", target = "email" , qualifiedByName = "emailSanitizer")
    @Mapping(source = "telefone", target = "telefone" , qualifiedByName = "numericSanitizer")
    @Mapping(source = "endereco", target = "endereco" , qualifiedByName = "textSanitizer")
    void updateEntidadeFromDto(ClienteUpdateRequest dto, @MappingTarget ClienteFisico cliente);



    @Mapping(source = "nome", target = "nome" , qualifiedByName = "textSanitizer")
    @Mapping(source = "email", target = "email" , qualifiedByName = "emailSanitizer")
    @Mapping(source = "telefone", target = "telefone" , qualifiedByName = "numericSanitizer")
    @Mapping(source = "endereco", target = "endereco" , qualifiedByName = "textSanitizer")
    void updateEntidadeFromDto(ClienteUpdateRequest dto, @MappingTarget ClienteJuridico cliente);



}
