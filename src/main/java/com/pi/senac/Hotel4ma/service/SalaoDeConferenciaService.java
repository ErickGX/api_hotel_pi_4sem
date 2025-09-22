package com.pi.senac.Hotel4ma.service;

import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.dtoGenerics.InstalacaoAlugavelRequestDTO;
import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.dtoGenerics.InstalacaoCustoRequestDTO;
import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.dtoGenerics.InstalacaoCustoResponseDTO;
import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.salaoDeConferencia.Request.SalaoDeConferenciaRequestDTO;
import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.salaoDeConferencia.Response.SalaoDeConferenciaResponseDTO;
import com.pi.senac.Hotel4ma.exceptions.ResourceNotFoundException;
import com.pi.senac.Hotel4ma.mappers.SalaoDeConferenciaMapper;
import com.pi.senac.Hotel4ma.model.Hotel;
import com.pi.senac.Hotel4ma.model.InstalacaoAlugavel;
import com.pi.senac.Hotel4ma.model.SalaoDeConferencia;
import com.pi.senac.Hotel4ma.repository.HotelRepository;
import com.pi.senac.Hotel4ma.repository.InstalacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalaoDeConferenciaService {

    private final InstalacaoRepository repository;
    private final HotelRepository hotelRepository;
    private final SalaoDeConferenciaMapper mapper;

    public SalaoDeConferenciaResponseDTO cadastrar(SalaoDeConferenciaRequestDTO request) {
        Hotel hotel = hotelRepository.findById(request.instalacao().id_hotel())
                .orElseThrow(() -> new ResourceNotFoundException("Hotel não encontrado"));

        SalaoDeConferencia salao = mapper.toEntity(request);
        salao.setHotel(hotel);

        SalaoDeConferencia salvo = (SalaoDeConferencia) repository.save(salao);
        return mapper.toDTO(salao);
    }

    public InstalacaoCustoResponseDTO cadastrarComCusto(InstalacaoCustoRequestDTO request) {
        Hotel hotel = hotelRepository.findById(request.id_hotel())
                .orElseThrow(() -> new ResourceNotFoundException("Hotel não encontrado"));

        SalaoDeConferencia salao = new SalaoDeConferencia();
        salao.setNome(request.nome());
        salao.setPrecoBase(request.precoBase());
        salao.setIsDisponivel(request.isDisponivel());
        salao.setDescricao(request.descricao());
        salao.setTipoSalaConferencia(request.tipoSalaConferencia());
        salao.setHotel(hotel);

        SalaoDeConferencia salvo = (SalaoDeConferencia) repository.save(salao);
        BigDecimal custo = salvo.calcularCustoTotal(request.horas());

        return new InstalacaoCustoResponseDTO(
                salvo.getId(),
                salvo.getNome(),
                request.horas(),
                custo
        );
    }

    public List<SalaoDeConferenciaResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .filter(i -> i instanceof SalaoDeConferencia)
                .map(i -> mapper.toDTO((SalaoDeConferencia) i))
                .collect(Collectors.toList());
    }

    public SalaoDeConferenciaResponseDTO buscarPorId(Long id) {
        InstalacaoAlugavel instalacao = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instalação não encontrada"));

        if (!(instalacao instanceof SalaoDeConferencia salao)) {
            throw new ResourceNotFoundException("Instalação não é um Salão de Conferência");
        }

        return mapper.toDTO(salao);
    }

    public SalaoDeConferenciaResponseDTO atualizar(Long id, SalaoDeConferenciaRequestDTO request) {
        InstalacaoAlugavel instalacao = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instalação não encontrada"));

        if (!(instalacao instanceof SalaoDeConferencia salao)) {
            throw new ResourceNotFoundException("Instalação não é um Salão de Conferência");
        }

        InstalacaoAlugavelRequestDTO base = request.instalacao();
        salao.setNome(base.nome());
        salao.setPrecoBase(base.precoBase());
        salao.setIsDisponivel(base.isDisponivel());
        salao.setDescricao(base.descricao());
        salao.setTipoSalaConferencia(request.tipoSalaConferencia());

        Hotel hotel = hotelRepository.findById(base.id_hotel())
                .orElseThrow(() -> new ResourceNotFoundException("Hotel não encontrado"));
        salao.setHotel(hotel);

        SalaoDeConferencia atualizado = (SalaoDeConferencia) repository.save(salao);
        return mapper.toDTO(atualizado);
    }

    public InstalacaoCustoResponseDTO calcularCusto(Long id, int horas) {
        InstalacaoAlugavel instalacao = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instalação não encontrada"));

        if (!(instalacao instanceof SalaoDeConferencia salao)) {
            throw new ResourceNotFoundException("Instalação não é um Salão de Conferência");
        }

        BigDecimal custo = salao.calcularCustoTotal(horas);

        return new InstalacaoCustoResponseDTO(
                salao.getId(),
                salao.getNome(),
                horas,
                custo
        );
    }

    public void deletar(Long id) {
        InstalacaoAlugavel instalacao = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instalação não encontrada"));

        if (!(instalacao instanceof SalaoDeConferencia)) {
            throw new ResourceNotFoundException("Instalação não é um Salão de Conferência");
        }

        repository.deleteById(id);
    }
}