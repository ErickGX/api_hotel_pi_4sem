package com.pi.senac.Hotel4ma.repository;

import com.pi.senac.Hotel4ma.model.ReservaSala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservaSalaRepository extends JpaRepository<ReservaSala, Long> {

    @Query("SELECT r FROM ReservaSala r " +
            "WHERE r.sala.idSala = :idSala " +
            "AND r.dataReserva = :dataReserva " +
            "AND r.status <> 'Cancelada' " +
            "AND ((:horaInicio < r.horaFim) AND (:horaFim > r.horaInicio))")
    List<ReservaSala> findReservasConflitantes(
            @Param("idSala") Long idSala,
            @Param("dataReserva") LocalDate dataReserva,
            @Param("horaInicio") LocalTime horaInicio,
            @Param("horaFim") LocalTime horaFim
    );
}
