package com.pi.senac.Hotel4ma.model;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Getter
public class Administrador extends Usuario{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @CPF
    private String cpf;

}
