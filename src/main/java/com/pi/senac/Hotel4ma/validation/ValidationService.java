package com.pi.senac.Hotel4ma.validation;

import com.pi.senac.Hotel4ma.exceptions.DuplicateCnpjException;
import com.pi.senac.Hotel4ma.exceptions.DuplicateCpfException;
import com.pi.senac.Hotel4ma.exceptions.DuplicateEmailException;
import com.pi.senac.Hotel4ma.model.Cliente;
import com.pi.senac.Hotel4ma.model.ClienteFisico;
import com.pi.senac.Hotel4ma.model.ClienteJuridico;
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


    //Classe criada por causa do trade-off da generalização dos tipos de usuarios

    //Metodo específico para validar um novo ClienteFisico
    public void validateNewClienteFisico(String cpf, String email) {
        if (clienteFisicoRepository.existsByCpf(cpf)) {
            throw new DuplicateCpfException("CPF já existente, tente novamente");
        }

        if (clienteRepository.existsByEmail(email)) {
            throw new DuplicateEmailException("Email já existente, tente novamente");
        }
    }

    public void validateNewFuncionario(String cpf, String email) {
        if (funcionarioRepository.existsByCpf(cpf)) {
            throw new DuplicateCpfException("CPF já existente, tente novamente");
        }
        if (funcionarioRepository.existsByEmail(email)) {
            throw new DuplicateEmailException("Email já existente, tente novamente");
        }
    }

    //Metodo específico para validar um novo administrador
    public void validateNewAdministrador(String cpf, String email) {
        if (administradorRepository.existsByCpf(cpf)) {
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
                && clienteRepository.existsByEmailAndIdNot(newEmail, existingClient.getId())) {
            throw new DuplicateEmailException("Email já existente, tente novamente");
        }
    }

    public void validateCpfOnUpdateClienteFisico(String newCpf, ClienteFisico existingClient) {
        // Valida se o CPF foi fornecido, se é diferente do atual, e se já existe em outro cliente
        if (newCpf != null && !newCpf.equalsIgnoreCase(existingClient.getCpf())
                && clienteFisicoRepository.existsByCpfAndIdNot(newCpf, existingClient.getId())) {
            throw new DuplicateCpfException("CPF já existente, tente novamente");
        }
    }

    public void validateCnpjOnUpdateClienteJuridico(String newCnpj, ClienteJuridico existingClient) {
        // Valida se o Cnpj foi fornecido, se é diferente do atual, e se já existe em outro cliente
        if (newCnpj != null && !newCnpj.equalsIgnoreCase(existingClient.getCnpj())
                && clienteJuridicoRepository.existsByCnpjAndIdNot(newCnpj, existingClient.getId())) {
            throw new DuplicateCnpjException("Cnpj já existente, tente novamente");
        }
    }

    public void validateCpfOnUpdateFuncionario(String newCpf, Funcionario existingFuncionario) {
        // Valida se o CPF foi fornecido, se é diferente do atual, e se já existe em outro cliente
        if (newCpf != null && !newCpf.equalsIgnoreCase(existingFuncionario.getCpf())
                && funcionarioRepository.existsByCpfAndIdNot(newCpf, existingFuncionario.getId())) {
            throw new DuplicateCpfException("CPF já existente, tente novamente");
        }
    }


}
