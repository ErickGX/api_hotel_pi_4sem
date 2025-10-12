package com.pi.senac.Hotel4ma.service;

import com.pi.senac.Hotel4ma.dtos.Pagamento.Response.PagamentoResponseDTO;
import com.pi.senac.Hotel4ma.exceptions.ResourceNotFoundException;
import com.pi.senac.Hotel4ma.mappers.PagamentoMapper;
import com.pi.senac.Hotel4ma.model.Pagamento;
import com.pi.senac.Hotel4ma.repository.PagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final PagamentoMapper mapper;

    @Transactional(readOnly = true)
    public PagamentoResponseDTO findById(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pagamento não encontrado com id: " + id));
        return mapper.toDto(pagamento);
    }

    @Transactional(readOnly = true)
    public List<PagamentoResponseDTO> findAll() {
        return mapper.toList(pagamentoRepository.findAll());
    }

    //nao permitido o delete de nenhum registro de pagamento
    //criação de pagamento é feita automaticamente na criação da reserva
}