package com.pi.senac.Hotel4ma.service;

import com.pi.senac.Hotel4ma.dtos.Instalacao.Request.InstalacaoRequest;
import com.pi.senac.Hotel4ma.dtos.Instalacao.Response.InstalacaoResponseDTO;
import com.pi.senac.Hotel4ma.factory.InstalacaoFactory;
import com.pi.senac.Hotel4ma.mappers.InstalacaoMapper;
import com.pi.senac.Hotel4ma.model.InstalacaoAlugavel;
import com.pi.senac.Hotel4ma.repository.InstalacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstalacaoService {

    private final InstalacaoRepository repository;
    private final InstalacaoMapper mapper;
    private final InstalacaoFactory factory;


    public InstalacaoResponseDTO create(InstalacaoRequest dto) {

        //Factory escolhe a subclasse Correta
        InstalacaoAlugavel entity  = factory.criarInstalacao(dto);

        //Mapper preenche campos comuns (nome, preço, hotel etc.)
        mapper.MergeEntidadeFromDto(dto, entity);

        //Um único save - JPA sabe lidar com a herança
        entity = repository.save(entity);

        //Converte para resposta
        return mapper.toDto(entity);
    }


}
