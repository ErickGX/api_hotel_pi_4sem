package com.pi.senac.Hotel4ma.service;

import com.pi.senac.Hotel4ma.dtos.Funcionario.Request.FuncionarioRequest;
import com.pi.senac.Hotel4ma.dtos.Funcionario.Request.FuncionarioUpdateRequest;
import com.pi.senac.Hotel4ma.dtos.Funcionario.Response.FuncionarioResponseDTO;
import com.pi.senac.Hotel4ma.exceptions.ResourceNotFoundException;
import com.pi.senac.Hotel4ma.mappers.FuncionarioMapper;
import com.pi.senac.Hotel4ma.model.Funcionario;
import com.pi.senac.Hotel4ma.model.Hotel;
import com.pi.senac.Hotel4ma.repository.FuncionarioRepository;
import com.pi.senac.Hotel4ma.repository.HotelRepository;
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
    private final HotelRepository hotelRepository;


    @Transactional
    public Long saveFuncionario(FuncionarioRequest dto) {
        //Valida se o cpf e email já estão cadastrados
         Hotel hotel = hotelRepository.findById(dto.id_hotel())
                .orElseThrow(() -> new ResourceNotFoundException("Hotel não encontrado com este ID"));

        Funcionario funcionario = repository.save(mapper.toEntity(dto));
        return funcionario.getId();
    }

    @Transactional
    public FuncionarioResponseDTO update(FuncionarioUpdateRequest dto, Long id) {

        //verifico se o cliente Existe
        Funcionario funcionario = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionario não encontrado com este ID"));

        validationService.validateEmailOnUpdateFuncionario(dto.email(), funcionario);

        //Mescla os novos valores
        mapper.updateEntidadeFromDto(dto, funcionario);

        //Persiste e retorna a resposta
        return mapper.toDTO(repository.save(funcionario));
    }

    @Transactional(readOnly = true)
    public List<FuncionarioResponseDTO> listAll() {
        List<Funcionario> funcionarios = repository.findAll();
        return mapper.toList(funcionarios);
    }

    @Transactional(readOnly = true)
    public FuncionarioResponseDTO findById(Long id) {
        return mapper.toDTO(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado com o ID: " + id)));
    }

    @Transactional(readOnly = true)
    public Funcionario getFuncionarioByid(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionario não encontrado com o ID: " + id));
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


    // METODO PRIVADO REUTILIZADO PARA A VALIDAÇÃO DE E-MAIL
    // POSSIVEL MUDANÇA PARA UMA CLASSE VALIDATOR
//    private void validateEmailOnUpdate(String newEmail, Funcionario existingFuncionario) {
//        // Valida se o e-mail foi fornecido, se é diferente do atual, e se já existe em outro cliente
//        if (newEmail != null && !newEmail.equalsIgnoreCase(existingFuncionario.getEmail())
//                && repository.existsByEmailAndIdNot(newEmail, existingFuncionario.getId())) {
//            throw new DuplicateEmailException("Email já existente, tente novamente");
//        }
//    }
//
//    private void validadeCpfAndEmailOnCreate(String cpf, String email){
//        //Verifica se o cpf já esta cadastrado
//        if (repository.existsByCpf(cpf)) {
//            throw new DuplicateCpfException("CPF já existente, tente novamente");
//        }
//
//        if (repository.existsByEmail(email)) {
//            throw new DuplicateEmailException("Email já existente, tente novamente");
//        }
//    }

//    public List<FuncionarioResponseDTO> listarTodos() {
//        return mapper.toList(repository.findAll());
//    }

//    @Transactional(readOnly = true)
//    public List<Funcionario> listarTodos() {
//        return (List<Funcionario>) funcionarioRepository.findAll();
//    }
//
//    @Transactional(readOnly = true)
//    public Funcionario buscarPorId(Long id) {
//        return funcionarioRepository.findById(id).orElse(null);
//    }
//
//    @Transactional
//    public Funcionario salvar(Funcionario funcionario) {
//        if (funcionario.getHotel() == null) {
//            throw new IllegalArgumentException("Funcionário deve estar vinculado a um hotel.");
//        }
//        return funcionarioRepository.save(funcionario);
//    }
//
//    @Transactional
//    public Funcionario atualizar(Long id, Funcionario funcionarioAtualizado) {
//        return funcionarioRepository.findById(id).map(funcionario -> {
//            // dados herdados de Pessoa
//            funcionario.setNome(funcionarioAtualizado.getNome());
//            funcionario.setEmail(funcionarioAtualizado.getEmail());
//            funcionario.setTelefone(funcionarioAtualizado.getTelefone());
//            funcionario.setSenha(funcionarioAtualizado.getSenha());
//
//            // dados próprios de Funcionario
//            funcionario.setCargo(funcionarioAtualizado.getCargo());
//            funcionario.setHotel(funcionarioAtualizado.getHotel());
//
//            // atualiza reservas de hospedagem
//            funcionario.getReservasHospedagem().clear();
//            if (funcionarioAtualizado.getReservasHospedagem() != null) {
//                funcionarioAtualizado.getReservasHospedagem().forEach(reserva -> {
//                    reserva.setFuncionario(funcionario);
//                    funcionario.getReservasHospedagem().add(reserva);
//                });
//            }
//
//            // atualiza reservas de sala
//            funcionario.getReservasSala().clear();
//            if (funcionarioAtualizado.getReservasSala() != null) {
//                funcionarioAtualizado.getReservasSala().forEach(reserva -> {
//                    reserva.setFuncionario(funcionario);
//                    funcionario.getReservasSala().add(reserva);
//                });
//            }
//
//            return funcionarioRepository.save(funcionario);
//        }).orElse(null);
//    }

}
