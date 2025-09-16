package com.pi.senac.Hotel4ma.validation;

import com.pi.senac.Hotel4ma.exceptions.DuplicateCpfException;
import com.pi.senac.Hotel4ma.exceptions.DuplicateEmailException;
import com.pi.senac.Hotel4ma.model.Administrador;
import com.pi.senac.Hotel4ma.model.Cliente;
import com.pi.senac.Hotel4ma.model.Funcionario;
import com.pi.senac.Hotel4ma.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidationService {
    private final FuncionarioRepository funcionarioRepository;
    private final ClienteFisicoRepository clienteFisicoRepository;
    private final ClienteJuridicoRepository clienteJuridicoRepository;
    private final ClienteRepository clienteRepository;
    private final AdministradorRepository administradorRepository;

    //Metodo específico para validar um novo ClienteFisico
    public void validateNewClienteFisico(String cpf, String email){
        if (clienteFisicoRepository.existsByCpf(cpf)) {
            throw new DuplicateCpfException("CPF já existente, tente novamente");
        }

        if (clienteRepository.existsByEmail(email)) {
            throw new DuplicateEmailException("Email já existente, tente novamente");
        }
    }


    //Metodo específico para validar um novo Funcionario
    public void validateNewFuncionario(String cpf, String email){
        if (funcionarioRepository.existsByCpf(cpf)) {
            throw new DuplicateCpfException("CPF já existente, tente novamente");
        }

        if (funcionarioRepository.existsByEmail(email)) {
            throw new DuplicateEmailException("Email já existente, tente novamente");
        }
    }

    //Metodo específico para validar um novo administrador
    public void validateNewAdministrador(String cpf, String email){
        if (administradorRepository.existsByCpf(cpf)){
            throw new DuplicateCpfException("CPF já existente, tente novamente");
        }

        if (administradorRepository.existsByEmail(email)) {
            throw new DuplicateEmailException("Email já existente, tente novamente");
        }
    }

    public void validateEmailOnUpdateFuncionario(String newEmail, Funcionario existingFuncionario) {
        // Valida se o e-mail foi fornecido, se é diferente do atual, e se já existe em outro cliente
        if (newEmail != null && !newEmail.equalsIgnoreCase(existingFuncionario.getEmail())
                && funcionarioRepository.existsByEmailAndIdNot(newEmail, existingFuncionario.getId())) {
            throw new DuplicateEmailException("Email já existente, tente novamente");
        }
    }

    //refatorar para Aceitar ClientFisico e juridico, metodo existsByEmailAndIdNot
    //Só existe no repositorio cliente
    public void validateEmailOnUpdateCliente(String newEmail, Cliente existingClient) {
        // Valida se o e-mail foi fornecido, se é diferente do atual, e se já existe em outro cliente
        if (newEmail != null && !newEmail.equalsIgnoreCase(existingClient.getEmail())
                && funcionarioRepository.existsByEmailAndIdNot(newEmail, existingClient.getId())) {
            throw new DuplicateEmailException("Email já existente, tente novamente");
        }
    }


    public void validateEmailOnUpdateAdmin(String newEmail, Administrador existingAdmin) {
        // Valida se o e-mail foi fornecido, se é diferente do atual, e se já existe em outro cliente
        if (newEmail != null && !newEmail.equalsIgnoreCase(existingAdmin.getEmail())
                && funcionarioRepository.existsByEmailAndIdNot(newEmail, existingAdmin.getId())) {
            throw new DuplicateEmailException("Email já existente, tente novamente");
        }
    }





}
