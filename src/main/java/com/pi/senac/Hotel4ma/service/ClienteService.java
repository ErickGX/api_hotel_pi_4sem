package com.pi.senac.Hotel4ma.service;

import com.pi.senac.Hotel4ma.config.PasswordEncoderConfig;
import com.pi.senac.Hotel4ma.dtos.Cliente.Request.ClienteFisicoRequest;
import com.pi.senac.Hotel4ma.dtos.Cliente.Request.ClienteJuridicoRequest;
import com.pi.senac.Hotel4ma.dtos.Cliente.Request.ClienteUpdateRequest;
import com.pi.senac.Hotel4ma.dtos.Cliente.Response.ClienteResponseDTO;
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


    /**
     *Criação verifica emails e cpfs duplicados em registros ativos e inativos
     * Evitar duplicidade completa por causa do Soft delete
     * @param dto
     * @return ID do Cliente Fisico criado
     */
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


    /**
     *Criação verifica emails e cpfs duplicados em registros ativos e inativos
     * Evitar duplicidade completa por causa do Soft delete
     * @param dto
     * @return ID do Cliente Juridico criado
     */
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


    /**
     * Atualiza os dados de um Cliente Fisico existente.
     * Busca o email nos registros ativos e inativos para evitar duplicidade
     * @param dto
     * @param idCliente
     * @return ClienteResponseDTO
     */
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


    /**
     * Atualiza os dados de um Cliente Jurídico existente.
     * Busca o email nos registros ativos e inativos para evitar duplicidade
     * @param dto
     * @param idCliente
     * @return ClienteResponseDTO
     */
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


//    @Transactional //Hard Delete desativado por requisito de projeto de soft delete
//    public void deleteById(Long id) {
//        // Verifica se o recurso existe.
//        if (!repository.existsById(id)) {
//            throw new ResourceNotFoundException("Recurso não encontrado com o ID: " + id);
//        }
//        // Passo 2: Se existe, manda deletar.
//        repository.deleteById(id);
//    }


    /**
     * Desativa um Cliente pelo ID, marcando-o como inativo.
     * @param id
     */
    public void desativarById(Long id) {
      Cliente cliente = repository.findAtivoById(id)
              .orElseThrow(() -> new ResourceNotFoundException("Recurso ativo não encontrado com o ID: " + id));
      cliente.setAtivo(false);
      repository.save(cliente);
    }


    /**
     * Lista todos os Clientes ativos e os converte para DTOs de resposta.
     * @return Lista de ClienteResponseDTO
     */
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listAll() {
        List<Cliente> clientes = repository.findAllAtivos();
        return mapper.toList(clientes);
    }

    /**
     * Busca um Cliente ativo pelo ID e o converte para um DTO de resposta.
     * Ideal para ser usado pela camada de Controller.
     */
    @Transactional(readOnly = true)
    public ClienteResponseDTO findDtoById(Long id) {
        return mapper.toDTO(repository.findAtivoById(id) //modificado para buscar apenas ativos
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado com o ID: " + id)));
    }

    /**
     * Busca a entidade Cliente ativos pelo ID.
     * Ideal para ser usado por outros serviços que precisam do objeto de domínio.
     * O nome "get" sugere que ele lança uma exceção se o recurso não for encontrado.
     */
    @Transactional(readOnly = true)
    public Cliente getClienteById(Long id) {
        return repository.findAtivoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o ID: " + id));
    }


    /**
     * Busca um Cliente inativo pelo ID e o converte para um DTO de resposta.
     * @param id
     * @return ClienteResponseDTO
     */
    @Transactional(readOnly = true)
    public ClienteResponseDTO getInativosById(Long id) {
        return mapper.toDTO(repository.findInativoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente inativo não encontrado com o ID: " + id)));
    }

    /**
     * Lista todos os Clientes inativos e os converte para DTOs de resposta.
     * @return Lista de ClienteResponseDTO
     */
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> getInativos() {
        return mapper.toList(repository.findAllInativos());
    }


    /**
     * Reativa um Cliente inativo pelo ID, marcando-o como ativo.
     * @param id
     * @return void
     */
    @Transactional
    public void reativarRegistro(Long id){
      Cliente cliente = repository.findInativoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente inativo não encontrado com o ID: " + id));

      cliente.setAtivo(true);
      repository.save(cliente);
    }
}
