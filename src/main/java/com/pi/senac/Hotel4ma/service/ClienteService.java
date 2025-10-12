package com.pi.senac.Hotel4ma.service;

import com.pi.senac.Hotel4ma.dtos.Cliente.Request.ClienteFisicoRequest;
import com.pi.senac.Hotel4ma.dtos.Cliente.Request.ClienteJuridicoRequest;
import com.pi.senac.Hotel4ma.dtos.Cliente.Request.ClienteUpdateRequest;
import com.pi.senac.Hotel4ma.dtos.Cliente.Response.ClienteResponseDTO;
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
import com.pi.senac.Hotel4ma.viacep.ViaCepService;
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



    //futuramente possivel refatorção das exceptions de codigo 409 em uma
    //validação de email e cpf existentes
    @Transactional
    public Long createFisico(ClienteFisicoRequest dto) {
        //verificação completa para cpf e email duplicados
        validationService.validateNewClienteFisico(dto.cpf(), dto.email());

        //Fiz uma refatoração deste codigo no createJuridico para 1 linha (Bom) ?
        //return mapper.toDTO(repository.save(mapper.toEntity(dto)));
        return repository.save(mapper.toEntity(dto)).getId();
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

        return repository.save(mapper.toEntity(dto)).getId();
    }


    @Transactional
    public ClienteResponseDTO updateClienteFisico(ClienteUpdateRequest dto, Long idCliente) {
        //verifico se o func Existe
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

        ClienteJuridico cliente = juridicoRepository.findById(idCliente)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente Juridico não encontrado com este ID"));

        //encapsulamento em metodo generelatista
        validationService.validateEmailOnUpdateCliente(dto.email(), cliente);

        //Mescla os novos valores automaticamente
        mapper.updateEntidadeFromDto(dto, cliente);

        //Persiste e retorna a resposta
        return mapper.toDTO(repository.save(cliente));
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listAll() {
        List<Cliente> clientes = repository.findAll();
        return mapper.toList(clientes);
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO findById(Long id) {
        return mapper.toDTO(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado com o ID: " + id)));

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
    public Cliente getClienteById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o ID: " + id));
    }

}
