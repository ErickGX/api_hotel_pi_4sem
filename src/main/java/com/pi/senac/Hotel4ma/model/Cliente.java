
package com.pi.senac.Hotel4ma.model;

import com.pi.senac.Hotel4ma.enums.TipoCliente;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
//Estrategia para manter clientes em uma unica tabela diferenciando pelo campo tipo_cliente no banco de dados
//necessario mudar porque acabou criando complexidade de validacao
@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "tipo_cliente", length = 10)
@Table(name = "clientes")
//@SQLDelete(sql = "UPDATE {h-schema}clientes SET ativo = false WHERE id = ?") // 1. Intercepta o DELETE
//@SQLRestriction(value = "ativo = true") // 2. Garante que UPDATEs também respeitem o filtro
public abstract class Cliente extends Usuario {


    @Enumerated(EnumType.STRING)
    private TipoCliente tipoCliente;

    @Column(length = 8, nullable = false)
    private String cep;

    @Column(length = 100, nullable = false)
    private String logradouro;

    @Column(length = 100, nullable = false)
    private String numero;

    @Column(length = 100)
    private String complemento;

    @Column(length = 100, nullable = false)
    private String bairro;

    @Column(length = 100, nullable = false)
    private String localidade;

    @Column(length = 2, nullable = false)
    private String uf;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas = new ArrayList<>();

}
