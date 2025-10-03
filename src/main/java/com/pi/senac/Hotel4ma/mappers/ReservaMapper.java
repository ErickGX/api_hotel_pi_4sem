//package com.pi.senac.Hotel4ma.mappers;
//
//import com.pi.senac.Hotel4ma.dtos.Cliente.Response.ClienteResumoDTO;
//import com.pi.senac.Hotel4ma.dtos.Funcionario.Response.FuncionarioResumoDTO;
//import com.pi.senac.Hotel4ma.dtos.Pagamento.Response.PagamentoResponseDTO;
//import com.pi.senac.Hotel4ma.dtos.Reserva.Response.ReservaResponseDTO;
//import com.pi.senac.Hotel4ma.model.Cliente;
//import com.pi.senac.Hotel4ma.model.Funcionario;
//import com.pi.senac.Hotel4ma.model.Pagamento;
//import com.pi.senac.Hotel4ma.model.Reserva;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//
//import java.util.List;
//
//@Mapper(componentModel = "spring")
//public abstract class ReservaMapper {
//
//
//    // Converte Reserva -> ReservaResponseDTO
//    @Mapping(source = "instalacaoAlugavel.id", target = "idInstalacao")
//    @Mapping(source = "cliente", target = "cliente") // será resolvido por toClienteResumoDTO
//    @Mapping(source = "funcionario", target = "funcionario") // será resolvido por toFuncionarioResumoDTO
//    @Mapping(source = "pagamento", target = "pagamento") // será resolvido por toPagamentoResponseDTO
//    public abstract ReservaResponseDTO toDto(Reserva reserva);
//
//
//
//    public abstract  List<ReservaResponseDTO> toList(List<Reserva> reservas);
//
//    // Mapeia cliente para versão resumida
//    public abstract  ClienteResumoDTO toClienteResumoDTO(Cliente cliente);
//
//    // Mapeia funcionario para versão resumida
//    public abstract   FuncionarioResumoDTO toFuncionarioResumoDTO(Funcionario funcionario);
//
//    // Mapeia pagamento para resposta
//    public abstract PagamentoResponseDTO toPagamentoResponseDTO(Pagamento pagamento);
//
//}
