package com.pi.senac.Hotel4ma.repository;

import com.pi.senac.Hotel4ma.enums.StatusReserva;
import com.pi.senac.Hotel4ma.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

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

    /**
     * Busca reservas que estão ATIVAS mas cujo check-out já passou,
     * para que possam ser marcadas como FINALIZADAS.
     * Também respeita o soft-delete (ativo = true).
     */
    @Query("SELECT r FROM Reserva r WHERE r.statusReserva = :status AND r.checkOut <= :agora AND r.ativo = true")
    List<Reserva> findReservasAtivasParaFinalizar(
            @Param("status") StatusReserva status,
            @Param("agora") LocalDateTime agora
    );



}
