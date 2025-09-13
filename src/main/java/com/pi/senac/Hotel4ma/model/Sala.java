//package com.pi.senac.Hotel4ma.model;
//
//import jakarta.persistence.*;
//import lombok.*;
//import java.util.List;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "salas")
//public class Sala {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long idSala;
//
//    @ManyToOne(optional = false) // sala precisa de um hotel
//    @JoinColumn(name = "id_hotel", nullable = false)
//    private Hotel hotel;
//
//    @Column(nullable = false, length = 100)
//    private String nome; // Ex: Sala de Conferência, Auditório
//
//    @Column(nullable = false, length = 30)
//    private String tipo; // Ex: Reunião, Evento
//
//    @Column(nullable = false)
//    private Integer capacidade;
//
//    @Column(length = 255)
//    private String descricao;
//
//    @Column(nullable = false)
//    private Double valorHora;
//
//    @Column(nullable = false, length = 20)
//    private String status; // Ex: Disponível, Reservada, Em manutenção
//
//    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<ReservaSala> reservasSala;
//}
