package com.pi.senac.Hotel4ma.repository;

import com.pi.senac.Hotel4ma.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    //Permite verificar colisão sem bloquear quando o valor é do próprio cliente.
    boolean existsByEmailAndIdNot(String email, Long id);


    boolean existsByEmail(String email);

    Optional<Cliente> findByEmail(String email);

    @Query("SELECT c FROM Cliente c WHERE c.ativo = true")
    List<Cliente> findAllAtivos();

    @Query("SELECT c FROM Cliente c WHERE c.id = :id AND c.ativo = true")
    Optional<Cliente> findAtivoById(Long id);

    @Query("SELECT c FROM Cliente c WHERE c.ativo = false")
    List<Cliente> findAllInativos();

    @Query("SELECT c FROM Cliente c WHERE c.id = :id AND c.ativo = false")
    Optional<Cliente> findInativoById(@Param("id") Long id);


    //Logins só buscam contas ativa, pois não faz sentido logar com conta inativa
    @Query("SELECT c FROM Cliente c WHERE c.email = :email AND c.ativo = true")
    Optional<Cliente> findByEmailAndAtivoTrue(@Param("email") String email);


}
