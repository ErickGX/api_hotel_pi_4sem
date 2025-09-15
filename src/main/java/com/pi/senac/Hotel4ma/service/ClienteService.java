package com.pi.senac.Hotel4ma.service;

import com.pi.senac.Hotel4ma.dtos.Cliente.Request.ClienteFisicoRequest;
import com.pi.senac.Hotel4ma.dtos.Cliente.Request.ClienteJuridicoRequest;
import com.pi.senac.Hotel4ma.dtos.Cliente.Request.ClienteUpdateRequest;
import com.pi.senac.Hotel4ma.dtos.Cliente.Response.ClienteResponseDTO;
import com.pi.senac.Hotel4ma.exceptions.DuplicateEmailException;
import com.pi.senac.Hotel4ma.exceptions.ResourceNotFoundException;
import com.pi.senac.Hotel4ma.mappers.ClienteMapper;
import com.pi.senac.Hotel4ma.model.Cliente;
import com.pi.senac.Hotel4ma.model.ClienteFisico;
import com.pi.senac.Hotel4ma.model.ClienteJuridico;
import com.pi.senac.Hotel4ma.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository repository;
    private final ClienteMapper mapper;


//    public List<Cliente> listarTodos() {
//        return (List<Cliente>) clienteRepository.findAll();
//    }
//
//    public Cliente buscarPorId(Long id) {
//        return clienteRepository.findById(id).orElse(null);
//    }

    public ClienteResponseDTO salvarFisico(ClienteFisicoRequest dto) {

        ClienteFisico cliente = mapper.toEntity(dto);
        //salvo cliente , pego resultado e converto para response
        ClienteFisico clienteSalvo = repository.save(cliente);
        // 3. Entity -> Response DTO
        return mapper.toDTO(clienteSalvo);

    }

    public ClienteResponseDTO salvarJuridico(ClienteJuridicoRequest dto) {

        ClienteJuridico clienteJuridico = mapper.toEntity(dto);
        //salvo cliente , pego resultado e converto para response
        ClienteJuridico clienteSalvo = repository.save(clienteJuridico);
        // 3. Entity -> Response DTO
        return mapper.toDTO(clienteSalvo);
    }

    public ClienteResponseDTO atualizarFisico(ClienteUpdateRequest dto, Long idCliente) {
        //verifico se o cliente Existe
        ClienteFisico cliente = (ClienteFisico) repository.findById(idCliente)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com este ID"));

        //Valida unicidade do novo e-mail (se o DTO trouxe um e-mail diferente)
        if (repository.existsByEmailAndIdNot(dto.email(), idCliente)) {
            throw new DuplicateEmailException("Email já existente, tente novamente");
        }

        //Mescla os novos valores
        mapper.updateEntidadeFromDto(dto, cliente);

        //Persiste e retorna a resposta
        Cliente atualizado = repository.save(cliente);
        return mapper.toUpdatedDto(atualizado);
    }

    public List<ClienteResponseDTO> listAll() {
        List<Cliente> clientes = repository.findAll();
        return clientes.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public ClienteResponseDTO buscarPorId(Long id) {
        return mapper.toDTO(repository.findById(id).orElse(null));
    }

    public Boolean deletarPorId(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }


//    public void deletar(Long id) {
//        clienteRepository.deleteById(id);
//    }

//    public Cliente atualizar(Long id, Cliente clienteAtualizado) {
//        return clienteRepository.findById(id).map(cliente -> {
//            cliente.setNome(clienteAtualizado.getNome());
//            cliente.setEmail(clienteAtualizado.getEmail());
//            cliente.setTelefone(clienteAtualizado.getTelefone());
//            cliente.setEndereco(clienteAtualizado.getEndereco());
//
//
//            //Isso sera decidido pelo campo enviado na DTO pelo frontend
//            //A escolha devera ser feita usando os campos enviados da DTO
//
//            // Atualiza CPF e/ou CNPJ, caso sejam informados
//                   // cliente.setcpf(clienteAtualizado.getCpf());
//                   // cliente.setCnpj(clienteAtualizado.getCnpj());
//
//            //if (!cliente.validarDocumento()) {
//            //    throw new IllegalArgumentException("Documento inválido (CPF ou CNPJ).");
//            // }
//
//            // Atualiza reservas de hospedagem
//            if (cliente.getReservasHospedagem() != null) {
//                cliente.getReservasHospedagem().clear();
//            }
//            if (clienteAtualizado.getReservasHospedagem() != null) {
//                clienteAtualizado.getReservasHospedagem().forEach(reserva -> {
//                    reserva.setCliente(cliente);
//                    cliente.getReservasHospedagem().add(reserva);
//                });
//            }
//
//            // Atualiza reservas de sala
//            if (cliente.getReservasSala() != null) {
//                cliente.getReservasSala().clear();
//            }
//            if (clienteAtualizado.getReservasSala() != null) {
//                clienteAtualizado.getReservasSala().forEach(reserva -> {
//                    reserva.setCliente(cliente);
//                    cliente.getReservasSala().add(reserva);
//                });
//            }
//
//            return clienteRepository.save(cliente);
//        }).orElse(null);
//    }


//    public boolean validarDocumento(Cliente cliente) {

    // teste de outra maneira de verificar os campos para decisao do tipo cliente -- n testado
//    if (cpf != null && cnpj != null) {
//        throw new IllegalStateException("Cliente não pode ter CPF e CNPJ ao mesmo tempo.");
//    }
//        if (cpf == null && cnpj == null) {
//        throw new IllegalStateException("Cliente deve ter CPF ou CNPJ.");


//        // Só é válido se tiver CPF OU CNPJ, não os dois nulos
//        if (cliente.getCpf() != null) {
//            return cliente.getCpf().validarDoc();
//        } else if (cnpj != null) {
//            return cnpj.validarDoc();
//        }
//        return false;
//    }
}
