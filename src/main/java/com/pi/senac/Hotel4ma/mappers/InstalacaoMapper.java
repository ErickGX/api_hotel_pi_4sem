package com.pi.senac.Hotel4ma.mappers;

import com.pi.senac.Hotel4ma.dtos.Hotel.Response.HotelResumoDTO;
import com.pi.senac.Hotel4ma.dtos.Instalacao.Request.InstalacaoRequest;
import com.pi.senac.Hotel4ma.dtos.Instalacao.Request.InstalacaoUpdateRequest;
import com.pi.senac.Hotel4ma.dtos.Instalacao.Response.InstalacaoResponseDTO;
import com.pi.senac.Hotel4ma.exceptions.ResourceNotFoundException;
import com.pi.senac.Hotel4ma.model.*;
import com.pi.senac.Hotel4ma.repository.HotelRepository;
import com.pi.senac.Hotel4ma.security.sanitizer.InputSanitizer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
//abstrata quando preciso compor um objeto que possui outro
public abstract class InstalacaoMapper {


    @Autowired
    protected InputSanitizer sanitizer;


    // Atualização: mescla dados no objeto existente
    // Criação: usado após a Factory para preencher dados comuns
    //@Mapping(target = "hotel", source = "id_hotel", qualifiedByName = "mapHotel")
    @Mapping(target = "nome", expression = "java(sanitizer.sanitizeText(dto.nome()))")
    @Mapping(target = "descricao", expression = "java(sanitizer.sanitizeText(dto.descricao()))")
    public abstract void MergeEntidadeFromDto(InstalacaoRequest dto, @MappingTarget InstalacaoAlugavel instalacaoAlugavel, Hotel hotel);

    // DTO -> Response
    @Mapping(source = "hotel", target = "hotel")
    @Mapping(target = "numeroQuarto", expression = "java(getNumeroQuarto(instalacao))")
    @Mapping(target = "tipo", expression = "java(getTipo(instalacao))")
    public abstract  InstalacaoResponseDTO toDto(InstalacaoAlugavel instalacao);

    // Lista -> Lista DTO
    public abstract List<InstalacaoResponseDTO> toList(List<InstalacaoAlugavel> instalacoes);


    // Mapper auxiliar para hotel resumido
    // Esse metodo é chamado automaticamente para mapear o campo hotel
    public abstract HotelResumoDTO toHotelResumoDTO(Hotel hotel);


    // Metodo auxiliar para retornar o enum correto
    protected String getTipo(InstalacaoAlugavel instalacao) {
        if (instalacao instanceof Sauna s) return s.getTipoSauna().name();
        if (instalacao instanceof Spa s) return s.getTipoSpa().name();
        if (instalacao instanceof Auditorio a) return a.getTipoAuditorio().name();
        if (instalacao instanceof SalaoDeConferencia sc) return sc.getTipoSalaConferencia().name();
        if (instalacao instanceof Quarto q) return q.getTipoQuarto().name();
        if (instalacao instanceof SalaoDeEventos e) return e.getTipoSalaoEventos().name();
        return null;
    }

    // Metodo auxiliar para retornar o numero do quarto, se for quarto
    protected String getNumeroQuarto(InstalacaoAlugavel instalacao) {
        if (instalacao instanceof Quarto q) return String.valueOf(q.getNumeroQuarto());
        return null;
    }

}
