package com.pi.senac.Hotel4ma.repository;

import com.pi.senac.Hotel4ma.model.Quarto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuartoRepository extends JpaRepository<Quarto, Long> {
    List<Quarto> findByIsDisponivelTrue();
    List<Quarto> findByIsDisponivelFalse();
}
