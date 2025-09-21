package com.pi.senac.Hotel4ma.repository;

import com.pi.senac.Hotel4ma.model.InstalacaoAlugavel;
import com.pi.senac.Hotel4ma.model.SalaoDeConferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstalacaoRepository extends JpaRepository<InstalacaoAlugavel, Long> {
    List<InstalacaoAlugavel> findByIsDisponivelTrue();
}