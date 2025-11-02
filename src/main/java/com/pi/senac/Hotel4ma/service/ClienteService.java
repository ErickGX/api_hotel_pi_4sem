package com.pi.senac.Hotel4ma.service;

import com.pi.senac.Hotel4ma.config.PasswordEncoderConfig;
import com.pi.senac.Hotel4ma.dtos.Cliente.Request.ClienteFisicoRequest;
import com.pi.senac.Hotel4ma.dtos.Cliente.Request.ClienteJuridicoRequest;
import com.pi.senac.Hotel4ma.dtos.Cliente.Request.ClienteUpdateRequest;
import com.pi.senac.Hotel4ma.dtos.Cliente.Response.ClienteResponseDTO;
import com.pi.senac.Hotel4ma.dtos.Cliente.Response.ClienteResumoProjection;
import com.pi.senac.Hotel4ma.enums.Role;
import com.pi.senac.Hotel4ma.exceptions.DuplicateCpfException;
import com.pi.senac.Hotel4ma.exceptions.DuplicateEmailException;
import com.pi.senac.Hotel4ma.exceptions.ResourceNotFoundException;
import com.pi.senac.Hotel4ma.mappers.ClienteMapper;
import com.pi.senac.Hotel4ma.model.Cliente;
import com.pi.senac.Hotel4ma.model.ClienteFisico;
import com.pi.senac.Hotel4ma.model.ClienteJuridico;
import com.pi.senac.Hotel4ma.repository.ClienteFisicoRepository;
import com.pi.senac.Hotel4ma.repository.ClienteJuridicoRepository;
import com.pi.senac.Hotel4ma.repository.ClienteRepository;
import com.pi.senac.Hotel4ma.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository repository;
    private final ClienteFisicoRepository fisicoRepository;
    private final ClienteJuridicoRepository juridicoRepository;
    private final ClienteMapper mapper;
    private final ValidationService validationService;
    private final PasswordEncoderConfig passwordEncoderConfig;



    //futuramente possivel refatorção das exceptions de codigo 409 em uma
    //validação de email e cpf existentes
    @Transactional
    public Long createFisico(ClienteFisicoRequest dto) {
        //verificação completa para cpf e email duplicados
        validationService.validateNewClienteFisico(dto.cpf(), dto.email());

        //return mapper.toDTO(repository.save(mapper.toEntity(dto)));
        ClienteFisico entity = mapper.toEntity(dto);
        String senhaCriptografada = passwordEncoderConfig.bCryptPasswordEncoder().encode(dto.senha());
        entity.setSenha(senhaCriptografada);
        entity.setRole(Role.CLIENTE);
        return repository.save(entity).getId();
    }

    //validação de email e cnpj existentes
    @Transactional
    public Long createJuridico(ClienteJuridicoRequest dto) {
        //Verifica se o email já esta cadastrado - failfast
        if (repository.existsByEmail(dto.email())) {
            throw new DuplicateEmailException("Email já existente, tente novamente");
        }
        //Verifica se o cnpj já esta cadastrado
        if (juridicoRepository.existsByCnpj(dto.cnpj())) {
            throw new DuplicateCpfException("CNPJ já existente, tente novamente");
        }
        ClienteJuridico entity = mapper.toEntity(dto);
        String senhaCriptografada = passwordEncoderConfig.bCryptPasswordEncoder().encode(dto.senha());
        entity.setSenha(senhaCriptografada);
        entity.setRole(Role.CLIENTE);
        return repository.save(entity).getId();
    }


    @Transactional
    public ClienteResponseDTO updateClienteFisico(ClienteUpdateRequest dto, Long idCliente) {
        //Uso de repositorio especifico para garantir o tipo correto
        ClienteFisico cliente = fisicoRepository.findById(idCliente)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente Fisico não encontrado com este ID"));
        //encapsulamento em metodo generelatista
        validationService.validateEmailOnUpdateCliente(dto.email(), cliente);
        //Mescla os novos valores automaticamente
        mapper.updateEntidadeFromDto(dto, cliente);
        //Persiste e retorna a resposta
        return mapper.toDTO(repository.save(cliente));
    }

    @Transactional
    public ClienteResponseDTO updateClienteJuridico(ClienteUpdateRequest dto, Long idCliente) {
        //Uso de repositorio especifico para garantir o tipo correto
        ClienteJuridico cliente = juridicoRepository.findById(idCliente)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente Juridico não encontrado com este ID"));
        //encapsulamento em metodo generelatista
        validationService.validateEmailOnUpdateCliente(dto.email(), cliente);
        //Mescla os novos valores automaticamente
        mapper.updateEntidadeFromDto(dto, cliente);
        //Persiste e retorna a resposta
        return mapper.toDTO(repository.save(cliente));
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
    public List<ClienteResponseDTO> listAll() {
        List<Cliente> clientes = repository.findAll();
        return mapper.toList(clientes);
    }

    /**
     * Busca um Cliente pelo ID e o converte para um DTO de resposta.
     * Ideal para ser usado pela camada de Controller.
     */
    @Transactional(readOnly = true)
    public ClienteResponseDTO findDtoById(Long id) {
        return mapper.toDTO(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado com o ID: " + id)));
    }

    /**
     * Busca a entidade Cliente pelo ID.
     * Ideal para ser usado por outros serviços que precisam do objeto de domínio.
     * O nome "get" sugere que ele lança uma exceção se o recurso não for encontrado.
     */
    @Transactional(readOnly = true)
    public Cliente getClienteById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o ID: " + id));
    }

    @Transactional(readOnly = true)
    public ClienteResumoProjection getInativosById(Long id) {
        return repository.findInactiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente inativo não encontrado com o ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<ClienteResumoProjection> getInativos() {
        return repository.findAllDeletados();
    }

    @Transactional
    public void reativarRegistro(Long id){
        Cliente clienteInativo = repository.findInactiveEntityById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente inativo não encontrado com o ID: " + id));

        clienteInativo.setAtivo(true);
        repository.save(clienteInativo);
    }

}
