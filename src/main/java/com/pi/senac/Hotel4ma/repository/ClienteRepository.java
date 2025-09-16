package com.pi.senac.Hotel4ma.repository;

import com.pi.senac.Hotel4ma.model.Cliente;
import com.pi.senac.Hotel4ma.model.ClienteFisico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    //Permite verificar colisão sem bloquear quando o valor é do próprio cliente.
    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByEmail(String email);
    //boolean existsByCpfAndIdNot(String cpf, Long id);


}
