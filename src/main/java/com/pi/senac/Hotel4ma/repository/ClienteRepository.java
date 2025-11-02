package com.pi.senac.Hotel4ma.repository;

import com.pi.senac.Hotel4ma.dtos.Cliente.Response.ClienteResponseDTO;
import com.pi.senac.Hotel4ma.dtos.Cliente.Response.ClienteResumoDTO;
import com.pi.senac.Hotel4ma.dtos.Cliente.Response.ClienteResumoProjection;
import com.pi.senac.Hotel4ma.model.Cliente;
import com.pi.senac.Hotel4ma.model.ClienteFisico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    //Permite verificar colisão sem bloquear quando o valor é do próprio cliente.
    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByEmail(String email);
    //boolean existsByCpfAndIdNot(String cpf, Long id);

    Optional<Cliente> findByEmailAndSenha(String email, String senha);
    Optional<Cliente> findByEmail(String email);


    /**
     * Busca TODOS os clientes inativos.
     * Usa nativeQuery = true para ignorar o @SQLRestriction.
     * Nós MANUALMENTE recriamos os JOINs e a coluna 'clazz_' (discriminadora)
     * que o Hibernate precisa para mapear sua herança @Inheritance(strategy = JOINED).
     */
    @Query(value = """
        SELECT 
            c.*,  -- Todos os campos da tabela pai 'clientes' (inclui os campos do Usuario)
            cf.cpf, -- Campo da tabela filha
            cj.cnpj, -- Campo da tabela filha
            
            -- A coluna 'clazz_' que o Hibernate precisa para saber qual objeto criar
            CASE 
                WHEN cf.id IS NOT NULL THEN 'FISICO' 
                WHEN cj.id IS NOT NULL THEN 'JURIDICO'
            END AS clazz_
        FROM 
            clientes c
        LEFT JOIN 
            cliente_fisico cf ON c.id = cf.id
        LEFT JOIN 
            cliente_juridico cj ON c.id = cj.id
        WHERE 
            c.ativo = false
        """, nativeQuery = true)
    List<Cliente> findAllInactive();

    /**
     * Busca um ÚNICO cliente inativo pelo ID.
     */
    @Query(value = """
        SELECT 
            c.*, cf.cpf, cj.cnpj,
            CASE 
                WHEN cf.id IS NOT NULL THEN 'FISICO' 
                WHEN cj.id IS NOT NULL THEN 'JURIDICO'
            END AS clazz_
        FROM 
            clientes c
        LEFT JOIN 
            cliente_fisico cf ON c.id = cf.id
        LEFT JOIN 
            cliente_juridico cj ON c.id = cj.id
        WHERE 
            c.id = :id AND c.ativo = false
        """, nativeQuery = true)
    Optional<ClienteResumoProjection> findInactiveById(@Param("id") Long id);

    @Query(value = "SELECT id, nome, email, ativo FROM clientes WHERE ativo = false", nativeQuery = true)
    List<ClienteResumoProjection> findAllDeletados();


    @Query("SELECT c FROM Cliente c WHERE c.id = :id AND c.ativo = false")
    Optional<Cliente> findInactiveEntityById(@Param("id") Long id);

}
