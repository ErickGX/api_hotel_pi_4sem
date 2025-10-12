package com.pi.senac.Hotel4ma.service;

import com.pi.senac.Hotel4ma.dtos.Espacos.Request.EspacosRequestDTO;
import com.pi.senac.Hotel4ma.dtos.Espacos.Response.EspacosResponseDTO;
import com.pi.senac.Hotel4ma.exceptions.ResourceNotFoundException;
import com.pi.senac.Hotel4ma.mappers.EspacosMapper;
import com.pi.senac.Hotel4ma.model.Espacos;
import com.pi.senac.Hotel4ma.model.Hotel;
import com.pi.senac.Hotel4ma.repository.EspacosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EspacosService {

    private final EspacosRepository repository;
    private final EspacosMapper mapper;
    private final HotelService hotelService;

    @Transactional
    public Long create(EspacosRequestDTO dto) {
        Hotel hotel = hotelService.getHotelById(dto.id_hotel());
        //retorna apenas o id gerado para inserir no header location
        return repository.save(mapper.toEntity(dto,hotel)).getId();
    }

    @Transactional
    public void deleteById(Long id) {
        // Verifica se o recurso existe.
        if (!repository.existsById(id)) {

            throw new ResourceNotFoundException("Recurso não encontrado com o ID: " + id);
        }
        // Passo 2: Se existe, manda deletar.
        repository.deleteById(id);
    }
    @Transactional(readOnly = true)
    public List<EspacosResponseDTO> findAll() {
        List<Espacos> funcionarios = repository.findAll();
        return mapper.toList(funcionarios);
    }

    @Transactional(readOnly = true)
    public EspacosResponseDTO findById(Long id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado com o ID: " + id)));
    }


//    @Transactional(readOnly = true)
//    public void deleteAll(){
//        repository.deleteAll();
//    }
}
