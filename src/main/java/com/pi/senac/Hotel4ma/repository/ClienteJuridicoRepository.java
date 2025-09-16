package com.pi.senac.Hotel4ma.repository;

import com.pi.senac.Hotel4ma.model.ClienteJuridico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteJuridicoRepository extends JpaRepository<ClienteJuridico, Long> {
        boolean existsByCnpj(String cnpj);
}
