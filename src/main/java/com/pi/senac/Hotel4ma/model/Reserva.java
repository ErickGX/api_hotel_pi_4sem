package com.pi.senac.Hotel4ma.model;

public interface Reserva {
    String getStatus();
    Double getValorTotal();
    Boolean verificarDisponibilidade();
}
