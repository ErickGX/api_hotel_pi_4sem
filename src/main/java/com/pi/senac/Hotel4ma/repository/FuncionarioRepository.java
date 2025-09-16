package com.pi.senac.Hotel4ma.repository;

import com.pi.senac.Hotel4ma.model.Funcionario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    //Permite verificar colisão sem bloquear quando o valor é do próprio cliente.
    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);
}
