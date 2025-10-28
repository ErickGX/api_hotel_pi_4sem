package com.pi.senac.Hotel4ma.mappers;

import com.pi.senac.Hotel4ma.dtos.Cliente.Response.ClienteResumoDTO;
import com.pi.senac.Hotel4ma.dtos.Funcionario.Response.FuncionarioResumoDTO;
import com.pi.senac.Hotel4ma.dtos.Instalacao.Response.InstalacaoResumoDTO;
import com.pi.senac.Hotel4ma.dtos.Reserva.Request.ReservaRequest;
import com.pi.senac.Hotel4ma.dtos.Reserva.Response.ReservaResponseDTO;
import com.pi.senac.Hotel4ma.model.*;
import lombok.Getter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Getter
@Mapper(componentModel = "spring")
public abstract class ReservaMapper {



    @Mapping(target = "pagamento", ignore = true)
    @Mapping(target = "id", ignore = true)// diversas entidades possuem esse campo igual
    @Mapping(target = "ativo", ignore = true)// diversas entidades possuem esse campo igual
    @Mapping(target = "dataCadastro", ignore = true)//ignorando eu evito o erro de possibilidade de varias fontes
    // para um campo que tem o mesmo nome em outras entidades

    public abstract Reserva toEntity(ReservaRequest dto, Cliente cliente, Funcionario funcionario, InstalacaoAlugavel instalacaoAlugavel);

    // Converte Reserva -> ReservaResponseDTO
    public abstract ReservaResponseDTO toDto(Reserva reserva);
    public abstract  List<ReservaResponseDTO> toList(List<Reserva> reservas);
    // Mapeia cliente para versão resumida
    public abstract  ClienteResumoDTO toClienteResumoDTO(Cliente cliente);
    // Mapeia funcionario para versão resumida
    public abstract   FuncionarioResumoDTO toFuncionarioResumoDTO(Funcionario funcionario);
    public abstract InstalacaoResumoDTO toInstalacaoResumoDTO(InstalacaoAlugavel instalacao);


}
