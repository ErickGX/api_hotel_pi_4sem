package com.pi.senac.Hotel4ma.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;

@Entity
@Getter
@Setter
@DiscriminatorValue("JURIDICO")
public class ClienteJuridico extends Cliente  {

//    @Embedded
//    @AttributeOverride(name = "numeros", column = @Column(name = "cnpj", length = 14, unique = true))
    @Column(nullable = false, length = 14, unique = true)
    @CNPJ
    private String cnpj;

}
