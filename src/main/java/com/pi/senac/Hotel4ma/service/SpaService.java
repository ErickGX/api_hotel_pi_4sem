package com.pi.senac.Hotel4ma.service;

import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.dtoGenerics.InstalacaoCustoRequestDTO;
import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.dtoGenerics.InstalacaoCustoResponseDTO;
import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.spa.request.InstalacaoCustoSpaRequestDTO;
import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.spa.request.SpaRequestDTO;
import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.spa.response.SpaResponseDTO;
import com.pi.senac.Hotel4ma.exceptions.ResourceNotFoundException;
import com.pi.senac.Hotel4ma.model.Hotel;
import com.pi.senac.Hotel4ma.model.Spa;
import com.pi.senac.Hotel4ma.repository.InstalacaoRepository;
import com.pi.senac.Hotel4ma.mappers.SpaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class SpaService {

    private final InstalacaoRepository repository;
    private final SpaMapper mapper;

    public SpaResponseDTO cadastrar(SpaRequestDTO dto) {
        Spa spa = mapper.toEntity(dto);
        Spa salvo = (Spa) repository.save(spa);
        return mapper.toResponse(salvo);
    }

    public InstalacaoCustoResponseDTO cadastrarComCusto(InstalacaoCustoSpaRequestDTO dto) {
        Spa spa = new Spa();
        spa.setNome(dto.nome());
        spa.setPrecoBase(dto.precoBase());
        spa.setIsDisponivel(dto.isDisponivel());
        spa.setDescricao(dto.descricao());
        spa.setTipoSpa(dto.tipoSpa());

        Hotel hotel = new Hotel();
        hotel.setId(dto.id_hotel());
        spa.setHotel(hotel);

        Spa salvo = (Spa) repository.save(spa);

        return new InstalacaoCustoResponseDTO(
                salvo.getId(),
                salvo.getNome(),
                dto.horas(),
                salvo.calcularCustoTotal(dto.horas())
        );
    }

    public InstalacaoCustoResponseDTO calcularCusto(Long id, int horas) {
        Spa spa = (Spa) repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instalação não encontrada"));

        return new InstalacaoCustoResponseDTO(
                spa.getId(),
                spa.getNome(),
                horas,
                spa.calcularCustoTotal(horas)
        );
    }

    public SpaResponseDTO atualizar(Long id, SpaRequestDTO dto) {
        Spa spa = (Spa) repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instalação não encontrada"));

        mapper.updateEntityFromDTO(dto, spa);
        Spa atualizado = (Spa) repository.save(spa);
        return mapper.toResponse(atualizado);
    }

    public SpaResponseDTO buscarPorId(Long id) {
        Spa spa = (Spa) repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instalação não encontrada"));

        return mapper.toResponse(spa);
    }

    public List<SpaResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .filter(instalacao -> instalacao instanceof Spa)
                .map(instalacao -> mapper.toResponse((Spa) instalacao))
                .toList();
    }

    public void deletar(Long id) {
        Spa spa = (Spa) repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instalação não encontrada"));
        repository.delete(spa);
    }
}