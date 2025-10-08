package com.pi.senac.Hotel4ma.repository;

import com.pi.senac.Hotel4ma.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ReservaRepository extends JpaRepository<Reserva,Long> {
    @Query("""
        SELECT COUNT(r) > 0
        FROM Reserva r
        WHERE r.instalacaoAlugavel.id = :instalacaoId
          AND (r.checkIn < :checkOut AND r.checkOut > :checkIn)
          AND (:reservaId IS NULL OR r.id <> :reservaId)
    """)
    boolean existsReservaConflitante(
            @Param("instalacaoId") Long instalacaoId,
            @Param("checkIn") LocalDateTime checkIn,
            @Param("checkOut") LocalDateTime checkOut,
            @Param("reservaId") Long reservaId
    );
}
