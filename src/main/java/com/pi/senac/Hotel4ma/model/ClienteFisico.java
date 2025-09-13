package com.pi.senac.Hotel4ma.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Getter
@Setter
@DiscriminatorValue("FISICO")
public class ClienteFisico extends Cliente{

//    @Embedded
//    @AttributeOverride(name = "numero", column = @Column(name = "cpf", length = 11, unique = true))
    @CPF
    @Column(length = 11, unique = true, nullable = false)
    private String cpf;

}
