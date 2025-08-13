package ar.edu.utn.frc.tup.piii.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "paises")
public class Paises {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paises_id_gen")
    @SequenceGenerator(name = "paises_id_gen", sequenceName = "paises_id_pais_seq", allocationSize = 1)
    @Column(name = "id_pais", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_continente", nullable = false)
    private Continentes continente;
}