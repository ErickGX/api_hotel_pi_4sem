package com.pi.senac.Hotel4ma.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "quartos")
public class Quarto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idQuarto;

    @ManyToOne(optional = false) // quarto sempre pertence a um hotel
    @JoinColumn(name = "id_hotel", nullable = false)
    private Hotel hotel;

    @Column(nullable = false, length = 10)
    private String numero;

    @Column(nullable = false, length = 30)
    private String tipo; // Ex: Presidencial, Normal

    @Column(length = 255)
    private String descricao;

    @Column(nullable = false)
    private Double valorDiaria;

    @Column(nullable = false, length = 20)
    private String status; // Ex: Disponível, Ocupado, Em Manutenção

    @OneToMany(mappedBy = "quarto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservaHospedagem> reservasHospedagem;
}





