package com.pi.senac.Hotel4ma.service;

import com.pi.senac.Hotel4ma.model.Cliente;
import com.pi.senac.Hotel4ma.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;

    

    public List<Cliente> listarTodos() {
        return (List<Cliente>) clienteRepository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    public Cliente salvar(Cliente cliente) {
        // if (!cliente.validarDocumento()) {
        //       throw new IllegalArgumentException("Documento inválido (CPF ou CNPJ).");
        //  }


        return clienteRepository.save(cliente);
    }

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

    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }

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
