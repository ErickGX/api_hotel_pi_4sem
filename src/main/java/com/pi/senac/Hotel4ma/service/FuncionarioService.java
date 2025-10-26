package com.pi.senac.Hotel4ma.service;

import com.pi.senac.Hotel4ma.config.PasswordEncoderConfig;
import com.pi.senac.Hotel4ma.dtos.Funcionario.Request.FuncionarioRequest;
import com.pi.senac.Hotel4ma.dtos.Funcionario.Request.FuncionarioUpdateRequest;
import com.pi.senac.Hotel4ma.dtos.Funcionario.Response.FuncionarioResponseDTO;
import com.pi.senac.Hotel4ma.enums.Role;
import com.pi.senac.Hotel4ma.exceptions.ResourceNotFoundException;
import com.pi.senac.Hotel4ma.mappers.FuncionarioMapper;
import com.pi.senac.Hotel4ma.model.Funcionario;
import com.pi.senac.Hotel4ma.model.Hotel;
import com.pi.senac.Hotel4ma.repository.FuncionarioRepository;
import com.pi.senac.Hotel4ma.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository repository;
    private final FuncionarioMapper mapper;
    private final ValidationService validationService;
    private final HotelService hotelService;
    private final PasswordEncoderConfig passwordEncoderConfig;


    @Transactional
    public Long saveFuncionario(FuncionarioRequest dto) {

       Hotel hotel =  hotelService.getHotelById(dto.id_hotel());
        //Valida se o cpf e email já estão cadastrados
        validationService.validateNewFuncionario(dto.cpf(), dto.email());

        Funcionario funcionario = mapper.toEntity(dto, hotel);
        String senhaCriptografada = passwordEncoderConfig.bCryptPasswordEncoder().encode(dto.senha());
        funcionario.setSenha(senhaCriptografada);
        funcionario.setRole(Role.FUNCIONARIO);

        Funcionario salvo = repository.save(funcionario);
        return salvo.getId();
    }


    @Transactional
    public FuncionarioResponseDTO update(FuncionarioUpdateRequest dto, Long id) {

        Funcionario funcionario = getFuncionarioByid(id);
        validationService.validateEmailOnUpdateFuncionario(dto.email(), funcionario);
        //Mescla os novos valores
        mapper.updateEntidadeFromDto(dto, funcionario);
        //Persiste e retorna a resposta
        return mapper.toDTO(repository.save(funcionario));
    }

    @Transactional
    public void deleteById(Long id) {

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado com o ID: " + id);
        }

        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<FuncionarioResponseDTO> listAll() {
        List<Funcionario> funcionarios = repository.findAll();
        return mapper.toList(funcionarios);
    }

    @Transactional(readOnly = true)
    public FuncionarioResponseDTO findById(Long id) {
        return mapper.toDTO(getFuncionarioByid(id));
    }

    @Transactional(readOnly = true)
    public Funcionario getFuncionarioByid(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionario não encontrado com o ID: " + id));
    }



}
