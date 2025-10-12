package com.pi.senac.Hotel4ma.mappers;

import com.pi.senac.Hotel4ma.dtos.Cliente.Response.ClienteResumoDTO;
import com.pi.senac.Hotel4ma.dtos.Pagamento.Response.PagamentoResponseDTO;
import com.pi.senac.Hotel4ma.dtos.Reserva.Response.ReservaResumoDTO;
import com.pi.senac.Hotel4ma.model.Cliente;
import com.pi.senac.Hotel4ma.model.Pagamento;
import com.pi.senac.Hotel4ma.model.Reserva;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PagamentoMapper {

//Mappers nao devem acessar camadas de dados de outras entidades
//Uma camada so deve se comunicar com a camada imediatamente abaixo dela

    PagamentoResponseDTO toDto (Pagamento pagamento);

    List<PagamentoResponseDTO> toList (List<Pagamento> pagamentos);

    ClienteResumoDTO toClienteResumoDTO(Cliente cliente);
    ReservaResumoDTO toReservaResumoDTO(Reserva reserva);
}
