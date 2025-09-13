
package com.pi.senac.Hotel4ma.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
//Estrategia para manter clientes em uma unica tabela diferenciando pelo campo tipo_cliente no banco de dados
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_cliente", length = 10)
@Table(name = "clientes")
public abstract class Cliente extends Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200, nullable = false)
    private String endereco;

//    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<ReservaHospedagem> reservasHospedagem = new ArrayList<>();
//
//    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<ReservaSala> reservasSala = new ArrayList<>();


}
