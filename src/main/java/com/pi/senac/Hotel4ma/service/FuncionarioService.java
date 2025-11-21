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
    public Long saveFuncionarioSeeder(Funcionario funcionario) {

        Hotel hotel =  hotelService.getHotelById(funcionario.getHotel().getId());
        //Valida se o cpf e email já estão cadastrados
        validationService.validateNewFuncionario(funcionario.getCpf(), funcionario.getEmail());


        String senhaCriptografada = passwordEncoderConfig.bCryptPasswordEncoder().encode(funcionario.getSenha());
        funcionario.setSenha(senhaCriptografada);
        funcionario.setRole(Role.FUNCIONARIO);

        Funcionario salvo = repository.save(funcionario);
        return salvo.getId();
    }


    @Transactional
    public FuncionarioResponseDTO update(FuncionarioUpdateRequest dto, Long id) {

        Funcionario funcionario = getFuncionarioByid(id);
        validationService.validateEmailOnUpdateFuncionario(dto.email(), funcionario);
        validationService.validateCpfOnUpdateFuncionario(dto.cpf(), funcionario);
        //Mescla os novos valores
        mapper.updateEntidadeFromDto(dto, funcionario);
        //Persiste e retorna a resposta
        return mapper.toDTO(repository.save(funcionario));
    }

//    @Transactional //Desativado por requisito do projeto por Soft Delete
//    public void deleteById(Long id) {
//        if (!repository.existsById(id)) {
//            throw new ResourceNotFoundException("Recurso não encontrado com o ID: " + id);
//        }
//        repository.deleteById(id);
//    }


    /**
     * Desativa um Funcionario pelo ID, marcando-o como inativo.
     * @param id
     * @return void
     */
    @Transactional
    public void desativarById(Long id){
        Funcionario funcionario = getFuncionarioByid(id);
        funcionario.setAtivo(false);
        repository.save(funcionario);
    }

    @Transactional(readOnly = true)
    public List<FuncionarioResponseDTO> listAll() {
        List<Funcionario> funcionarios = repository.findAllAtivos();
        return mapper.toList(funcionarios);
    }



    /**
     * Busca um Funcionario ativo pelo ID e o converte para um DTO de resposta.
     * Ideal para ser usado pela camada de Controller.
     */
    @Transactional(readOnly = true)
    public FuncionarioResponseDTO findDtoById(Long id) {
        return mapper.toDTO(repository.findAtivosById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionario ativo não encontrado com o ID: " + id)));
    }


    /**
     * Busca a entidade Funcionario ativos pelo ID.
     * Ideal para ser usado por outros serviços que precisam do objeto de domínio.
     * O nome "get" sugere que ele lança uma exceção se o recurso não for encontrado.
     */
    @Transactional(readOnly = true)
    public Funcionario getFuncionarioByid(Long id) {
        return repository.findAtivosById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionario ativo não encontrado com o ID: " + id));
    }


    /**
     * Busca um Funcionario inativo pelo ID e o converte para um DTO de resposta.
     * @param id
     * @return FuncionarioResponseDTO
     */
    @Transactional(readOnly = true)
    public FuncionarioResponseDTO getInativosById(Long id) {
        return mapper.toDTO(repository.findInativoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente inativo não encontrado com o ID: " + id)));
    }

    /**
     * Lista todos os Funcionario inativos e os converte para DTOs de resposta.
     * @return Lista de FuncionarioResponseDTO
     */
    @Transactional(readOnly = true)
    public List<FuncionarioResponseDTO> getInativos() {
        return mapper.toList(repository.findAllInativos());
    }


    /**
     * Reativa um Funcionario inativo pelo ID, marcando-o como ativo.
     * @param id
     * @return void
     */
    @Transactional
    public void reativarRegistro(Long id){
        Funcionario funcionario = repository.findInativoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente inativo não encontrado com o ID: " + id));

        funcionario.setAtivo(true);
        repository.save(funcionario);
    }

    public boolean existsData() {
        return repository.count() > 0;
    }



}
