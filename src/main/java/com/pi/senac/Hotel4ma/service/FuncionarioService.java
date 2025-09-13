package com.pi.senac.Hotel4ma.service;

import com.pi.senac.Hotel4ma.model.Funcionario;
import com.pi.senac.Hotel4ma.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

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
//
//    @Transactional
//    public void deletar(Long id) {
//        if (!funcionarioRepository.existsById(id)) {
//            throw new IllegalArgumentException("Funcionário não encontrado para exclusão.");
//        }
//        funcionarioRepository.deleteById(id);
//    }
}
