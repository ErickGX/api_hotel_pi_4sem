
package com.pi.senac.Hotel4ma.model;
import com.pi.senac.Hotel4ma.validation.Cnpj;
import com.pi.senac.Hotel4ma.validation.Cpf;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clientes")
public class Cliente extends Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    @Embedded
    @AttributeOverride(name = "numero", column = @Column(name = "cpf", length = 11, unique = true))
    private Cpf cpf;

    @Embedded
    @AttributeOverride(name = "numero", column = @Column(name = "cnpj", length = 14, unique = true))
    private Cnpj cnpj;

    @Column(length = 200, nullable = false)
    private String endereco;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservaHospedagem> reservasHospedagem = new ArrayList<>();

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservaSala> reservasSala = new ArrayList<>();

    public boolean validarDocumento() {
        // Só é válido se tiver CPF OU CNPJ, não os dois nulos
        if (cpf != null) {
            return cpf.validarDoc();
        } else if (cnpj != null) {
            return cnpj.validarDoc();
        }
        return false;
    }
}
