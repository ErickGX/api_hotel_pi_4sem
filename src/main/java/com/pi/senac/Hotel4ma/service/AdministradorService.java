package com.pi.senac.Hotel4ma.service;

import com.pi.senac.Hotel4ma.dtos.Administrador.Request.AdminRequestDTO;
import com.pi.senac.Hotel4ma.mappers.AdministradorMapper;
import com.pi.senac.Hotel4ma.repository.AdministradorRepository;
import com.pi.senac.Hotel4ma.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdministradorService {
    private final AdministradorRepository repository;
    private final AdministradorMapper mapper;
    private final ValidationService validationService;

    @Transactional
    public void create(AdminRequestDTO dto) {
        validationService.validateNewAdministrador(dto.cpf(), dto.email());
        repository.save(mapper.toEntity(dto));
    }
}
