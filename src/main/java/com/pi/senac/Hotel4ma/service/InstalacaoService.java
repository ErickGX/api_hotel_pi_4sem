package com.pi.senac.Hotel4ma.service;

import com.pi.senac.Hotel4ma.dtos.Instalacao.Request.InstalacaoRequest;
import com.pi.senac.Hotel4ma.dtos.Instalacao.Request.InstalacaoUpdateRequest;
import com.pi.senac.Hotel4ma.dtos.Instalacao.Response.InstalacaoResponseDTO;
import com.pi.senac.Hotel4ma.enums.FatorMultiplicador;
import com.pi.senac.Hotel4ma.exceptions.ResourceNotFoundException;
import com.pi.senac.Hotel4ma.factory.InstalacaoFactory;
import com.pi.senac.Hotel4ma.mappers.InstalacaoMapper;
import com.pi.senac.Hotel4ma.model.InstalacaoAlugavel;
import com.pi.senac.Hotel4ma.repository.InstalacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InstalacaoService {

    private final InstalacaoRepository repository;
    private final InstalacaoMapper mapper;
    private final InstalacaoFactory factory;

    //create apenas retorna o id da instalação criada
    @Transactional(propagation = Propagation.REQUIRED)
    public Long create(InstalacaoRequest dto) {

        //Factory escolhe a subclasse Correta
        InstalacaoAlugavel entity = factory.criarInstalacao(dto);

        //Mapper preenche campos comuns (nome, preço, hotel etc.)
        mapper.MergeEntidadeFromDto(dto, entity);

        //Um único save - JPA sabe lidar com a herança
        return repository.save(entity).getId();

        //Converte para resposta
        //return mapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public InstalacaoResponseDTO findById(Long id) {
        InstalacaoAlugavel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instalação não encontrada com ID: " + id));
        return mapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public InstalacaoAlugavel getInstalacaoById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instalação não encontrada com ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<InstalacaoResponseDTO> findAll() {
        List<InstalacaoAlugavel> entities = repository.findAll();
        return mapper.toList(entities);
    }

    @Transactional
    public InstalacaoResponseDTO update(Long id, InstalacaoUpdateRequest dto) {
        InstalacaoAlugavel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instalação não encontrada com ID: " + id));

        // Aplica apenas a alteração de disponibilidade
        entity.setIsDisponivel(dto.isDisponivel());

        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    @Transactional
    public void delete(Long id) {
        InstalacaoAlugavel entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instalação não encontrada com ID: " + id));
        repository.delete(entity);
    }


    private static final BigDecimal PRECO_BASE_PADRAO = new BigDecimal("1000.00");

    public BigDecimal calcularValor(FatorMultiplicador tipo) {
        return PRECO_BASE_PADRAO.multiply(BigDecimal.valueOf(tipo.getFator()));
    }


}
