package com.pi.senac.Hotel4ma.model;

import com.pi.senac.Hotel4ma.enums.CargoFuncionario;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "funcionarios")
@SQLDelete(sql = "UPDATE {h-schema}funcionario SET ativo = false WHERE id = ?") // 1. Intercepta o DELETE
@SQLRestriction(value = "ativo = true") // 2. Garante que UPDATEs também respeitem o filtro
public class Funcionario extends Usuario {

    @Column(nullable = false, length = 50)
    private CargoFuncionario cargo;

    @Column(unique = true, nullable = false)
    @CPF
    private String cpf;

    @Column(nullable = false)
    private boolean ativo = true;

    // Funcionário precisa obrigatoriamente estar vinculado a um hotel
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_hotel", nullable = false)
    private Hotel hotel;

    //repeticao de codigo que podia usar cliente
    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas;
}

