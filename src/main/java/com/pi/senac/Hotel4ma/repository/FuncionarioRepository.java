package com.pi.senac.Hotel4ma.repository;

import com.pi.senac.Hotel4ma.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);
    boolean existsByCpfAndIdNot(String cpf, Long id);


    Optional<Funcionario> findByEmail(String email);

    @Query(value = "select * from funcionarios where ativo = false", nativeQuery = true)
    List<Funcionario> findAllInativos();

    @Query(value = "select * from funcionarios where id = :id and ativo = false", nativeQuery = true)
    Optional<Funcionario> findInativoById(@Param("id") Long id);

    @Query(value = "select * from funcionarios where ativo = true", nativeQuery = true)
    List<Funcionario> findAllAtivos();

    @Query(value= "select * from funcionarios where id = :id and ativo = true", nativeQuery = true)
    Optional<Funcionario> findAtivosById(Long id);


    //Logins só buscam contas ativa, pois não faz sentido logar com conta inativa
    @Query("SELECT f FROM Funcionario f WHERE f.email = :email AND f.ativo = true")
    Optional<Funcionario> findByEmailAndAtivoTrue(@Param("email") String email);
}
