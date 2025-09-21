package com.pi.senac.Hotel4ma.service;

import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.salaoDeConferencia.Request.InstalacaoRequestDTO;
import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.salaoDeConferencia.Request.SalaoCustoRequestDTO;
import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.salaoDeConferencia.Request.SalaoRequestDTO;
import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.salaoDeConferencia.Response.SalaoCustoResponseDTO;
import com.pi.senac.Hotel4ma.dtos.InstalacaoAlugavel.salaoDeConferencia.Response.SalaoResponseDTO;
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

    public SalaoResponseDTO cadastrar(SalaoRequestDTO request) {
        Hotel hotel = hotelRepository.findById(request.instalacao().id_hotel())
                .orElseThrow(() -> new ResourceNotFoundException("Hotel não encontrado"));

        SalaoDeConferencia salao = mapper.toEntity(request);
        salao.setHotel(hotel);

        InstalacaoAlugavel salvo = repository.save(salao);
        return mapper.toDTO((SalaoDeConferencia) salvo);
    }

    public SalaoCustoResponseDTO cadastrarComCusto(SalaoCustoRequestDTO request) {
        Hotel hotel = hotelRepository.findById(request.id_hotel())
                .orElseThrow(() -> new ResourceNotFoundException("Hotel não encontrado"));

        SalaoDeConferencia salao = new SalaoDeConferencia();
        salao.setNome(request.nome());
        salao.setPrecoBase(request.precoBase());
        salao.setIsDisponivel(request.isDisponivel());
        salao.setDescricao(request.descricao());
        salao.setTipoSalaConferencia(request.tipoSalaConferencia());
        salao.setHotel(hotel);

        InstalacaoAlugavel salvo = repository.save(salao);
        BigDecimal custo = ((SalaoDeConferencia) salvo).calcularCustoTotal(request.horas());

        return new SalaoCustoResponseDTO(
                salvo.getId(),
                salvo.getNome(),
                request.horas(),
                custo
        );
    }
    public List<SalaoResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .filter(i -> i instanceof SalaoDeConferencia)
                .map(i -> mapper.toDTO((SalaoDeConferencia) i))
                .collect(Collectors.toList());
    }

    public SalaoResponseDTO buscarPorId(Long id) {
        InstalacaoAlugavel instalacao = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instalação não encontrada"));

        if (!(instalacao instanceof SalaoDeConferencia)) {
            throw new ResourceNotFoundException("Instalação não é um Salão de Conferência");
        }

        SalaoDeConferencia salao = (SalaoDeConferencia) instalacao;
        return mapper.toDTO(salao);
    }

    public SalaoResponseDTO atualizar(Long id, SalaoRequestDTO request) {
        InstalacaoAlugavel instalacao = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instalação não encontrada"));

        if (!(instalacao instanceof SalaoDeConferencia)) {
            throw new ResourceNotFoundException("Instalação não é um Salão de Conferência");
        }

        SalaoDeConferencia salao = (SalaoDeConferencia) instalacao;

        // Atualiza os campos comuns da instalação
        InstalacaoRequestDTO base = request.instalacao();
        salao.setNome(base.nome());
        salao.setPrecoBase(base.precoBase());
        salao.setIsDisponivel(base.isDisponivel());
        salao.setDescricao(base.descricao());

        // Atualiza o campo específico do salão
        salao.setTipoSalaConferencia(request.tipoSalaConferencia());

        // Atualiza o hotel
        Hotel hotel = hotelRepository.findById(base.id_hotel())
                .orElseThrow(() -> new ResourceNotFoundException("Hotel não encontrado"));
        salao.setHotel(hotel);

        InstalacaoAlugavel atualizado = repository.save(salao);
        return mapper.toDTO((SalaoDeConferencia) atualizado);
    }

    public SalaoCustoResponseDTO calcularCusto(Long id, int horas) {
        InstalacaoAlugavel instalacao = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instalação não encontrada"));

        if (!(instalacao instanceof SalaoDeConferencia)) {
            throw new ResourceNotFoundException("Instalação não é um Salão de Conferência");
        }

        SalaoDeConferencia salao = (SalaoDeConferencia) instalacao;
        BigDecimal custo = salao.calcularCustoTotal(horas);

        return new SalaoCustoResponseDTO(
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