package com.pi.senac.Hotel4ma.repository;

import com.pi.senac.Hotel4ma.model.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdministradorRepository extends JpaRepository<Administrador, Integer> {
    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    Optional<Administrador> findByEmailAndSenha(String email, String senha);
}
