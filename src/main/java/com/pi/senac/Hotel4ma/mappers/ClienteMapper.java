package com.pi.senac.Hotel4ma.mappers;

import com.pi.senac.Hotel4ma.dtos.Cliente.Request.ClienteFisicoRequest;
import com.pi.senac.Hotel4ma.dtos.Cliente.Request.ClienteJuridicoRequest;
import com.pi.senac.Hotel4ma.dtos.Cliente.Request.ClienteUpdateRequest;
import com.pi.senac.Hotel4ma.dtos.Cliente.Response.ClienteResponseDTO;
import com.pi.senac.Hotel4ma.model.Cliente;
import com.pi.senac.Hotel4ma.model.ClienteFisico;
import com.pi.senac.Hotel4ma.model.ClienteJuridico;
import org.mapstruct.*;
import java.util.List;

//transforma em um componente spring em tempo de compilação
@Mapper(componentModel = "spring")
public interface ClienteMapper {

    @Mapping(target = "cpf", expression = "java(cliente instanceof ClienteFisico ? ((ClienteFisico) cliente).getCpf() : null)")
    @Mapping(target = "cnpj", expression = "java(cliente instanceof ClienteJuridico ? ((ClienteJuridico) cliente).getCnpj() : null)")
    @Mapping(target = "tipoCliente", expression = "java(cliente instanceof ClienteFisico ? TipoCliente.FISICA : cliente instanceof ClienteJuridico ? TipoCliente.JURIDICA : null)")
    ClienteResponseDTO toDTO(Cliente cliente);


    ClienteFisico toEntity(ClienteFisicoRequest dto);

    ClienteJuridico toEntity(ClienteJuridicoRequest dto);

    void updateEntidadeFromDto(ClienteUpdateRequest dto, @MappingTarget ClienteFisico cliente);
    void updateEntidadeFromDto(ClienteUpdateRequest dto, @MappingTarget ClienteJuridico cliente);
    List<ClienteResponseDTO> toList(List<Cliente> clientes);

}
