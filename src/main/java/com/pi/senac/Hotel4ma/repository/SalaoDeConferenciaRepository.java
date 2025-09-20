package com.pi.senac.Hotel4ma.repository;

import com.pi.senac.Hotel4ma.model.SalaoDeConferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaoDeConferenciaRepository extends JpaRepository<SalaoDeConferencia, Long> {
}