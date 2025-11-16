package com.pi.senac.Hotel4ma.service;

import com.pi.senac.Hotel4ma.config.PasswordEncoderConfig;
import com.pi.senac.Hotel4ma.dtos.Administrador.Request.AdminRequestDTO;
import com.pi.senac.Hotel4ma.enums.Role;
import com.pi.senac.Hotel4ma.mappers.AdministradorMapper;
import com.pi.senac.Hotel4ma.model.Administrador;
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
    private final PasswordEncoderConfig passwordEncoderConfig;

    @Transactional
    public void create(AdminRequestDTO dto) {
        validationService.validateNewAdministrador(dto.cpf(), dto.email());
        String senhaCriptografada = passwordEncoderConfig.bCryptPasswordEncoder().encode(dto.senha());
        Administrador admin = mapper.toEntity(dto);
        admin.setSenha(senhaCriptografada);
        admin.setRole(Role.ADMIN);
        repository.save(admin);
    }


    @Transactional
    public void createSeeder(Administrador admin) {
        validationService.validateNewAdministrador(admin.getCpf(), admin.getEmail());
        String senhaCriptografada = passwordEncoderConfig.bCryptPasswordEncoder().encode(admin.getSenha());
        admin.setSenha(senhaCriptografada);
        admin.setRole(Role.ADMIN);
        repository.save(admin);
    }

    public boolean existsData() {
        return repository.count() > 0;
    }
}
