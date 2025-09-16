package com.pi.senac.Hotel4ma.repository;

import com.pi.senac.Hotel4ma.model.ClienteFisico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteFisicoRepository extends JpaRepository<ClienteFisico, Long> {
    boolean existsByCpf(String cpf);
}
