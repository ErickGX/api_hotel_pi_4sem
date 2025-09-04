package com.pi.senac.Hotel4ma.service;

import com.pi.senac.Hotel4ma.model.Cliente;
import com.pi.senac.Hotel4ma.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listarTodos() {
        return (List<Cliente>) clienteRepository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    public Cliente salvar(Cliente cliente) {
        if (!cliente.validarDocumento()) {
            throw new IllegalArgumentException("Documento inválido (CPF ou CNPJ).");
        }
        return clienteRepository.save(cliente);
    }

    public Cliente atualizar(Long id, Cliente clienteAtualizado) {
        return clienteRepository.findById(id).map(cliente -> {
            cliente.setNome(clienteAtualizado.getNome());
            cliente.setEmail(clienteAtualizado.getEmail());
            cliente.setTelefone(clienteAtualizado.getTelefone());
            cliente.setEndereco(clienteAtualizado.getEndereco());

            // Atualiza CPF e/ou CNPJ, caso sejam informados
            cliente.setCpf(clienteAtualizado.getCpf());
            cliente.setCnpj(clienteAtualizado.getCnpj());

            if (!cliente.validarDocumento()) {
                throw new IllegalArgumentException("Documento inválido (CPF ou CNPJ).");
            }

            // Atualiza reservas de hospedagem
            if (cliente.getReservasHospedagem() != null) {
                cliente.getReservasHospedagem().clear();
            }
            if (clienteAtualizado.getReservasHospedagem() != null) {
                clienteAtualizado.getReservasHospedagem().forEach(reserva -> {
                    reserva.setCliente(cliente);
                    cliente.getReservasHospedagem().add(reserva);
                });
            }

            // Atualiza reservas de sala
            if (cliente.getReservasSala() != null) {
                cliente.getReservasSala().clear();
            }
            if (clienteAtualizado.getReservasSala() != null) {
                clienteAtualizado.getReservasSala().forEach(reserva -> {
                    reserva.setCliente(cliente);
                    cliente.getReservasSala().add(reserva);
                });
            }

            return clienteRepository.save(cliente);
        }).orElse(null);
    }

    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }
}
