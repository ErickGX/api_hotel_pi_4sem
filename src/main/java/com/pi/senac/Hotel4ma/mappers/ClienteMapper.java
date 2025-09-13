package com.pi.senac.Hotel4ma.mappers;

import com.pi.senac.Hotel4ma.dtos.Cliente.Request.ClienteFisicoRequest;
import com.pi.senac.Hotel4ma.dtos.Cliente.Request.ClienteJuridicoRequest;
import com.pi.senac.Hotel4ma.dtos.Cliente.Response.ClienteResponseDTO;
import com.pi.senac.Hotel4ma.model.Cliente;
import com.pi.senac.Hotel4ma.model.ClienteFisico;
import com.pi.senac.Hotel4ma.model.ClienteJuridico;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

//transforma em um componente spring em tempo de compilação
@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    @Mapping(target = "cpf", expression = "java(cliente instanceof ClienteFisico ? ((ClienteFisico) cliente).getCpf() : null)")
    @Mapping(target = "cnpj", expression = "java(cliente instanceof ClienteJuridico ? ((ClienteJuridico) cliente).getCnpj() : null)")
    @Mapping(target = "tipoCliente", expression = "java(cliente instanceof ClienteFisico ? TipoCliente.FISICA : cliente instanceof ClienteJuridico ? TipoCliente.JURIDICA : null)")
    ClienteResponseDTO toDTO(Cliente cliente);



    ClienteFisico toEntity(ClienteFisicoRequest dto);

    ClienteJuridico toEntity(ClienteJuridicoRequest dto);


//    @Named("documentMapping")
//    default String mapDocumento(Cliente cliente) {
//        if (cliente == null) {
//            return null;
//        }
//        if (cliente instanceof ClienteFisico fisico) {
//            return fisico.getCpf();
//        } else if (cliente instanceof ClienteJuridico juridico) {
//            return juridico.getCnpj();
//        }
//        return null; // ou lança exception se for inesperado
//    }
}
