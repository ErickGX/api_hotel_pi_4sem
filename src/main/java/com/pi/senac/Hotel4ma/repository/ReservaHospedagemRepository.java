package com.pi.senac.Hotel4ma.repository;

import com.pi.senac.Hotel4ma.model.ReservaHospedagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservaHospedagemRepository extends JpaRepository<ReservaHospedagem, Long> {

    @Query("SELECT r FROM ReservaHospedagem r " +
            "WHERE r.quarto.idQuarto = :idQuarto " +
            "AND r.status <> 'Cancelada' " +
            "AND ( :dataCheckin < r.dataCheckout AND :dataCheckout > r.dataCheckin )")
    List<ReservaHospedagem> findReservasConflitantes(
            @Param("idQuarto") Long idQuarto,
            @Param("dataCheckin") LocalDate dataCheckin,
            @Param("dataCheckout") LocalDate dataCheckout
    );
}
