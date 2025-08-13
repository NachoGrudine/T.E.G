package ar.edu.utn.frc.tup.piii.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "continentes")
public class Continentes {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "continentes_id_gen")
    @SequenceGenerator(name = "continentes_id_gen", sequenceName = "continentes_id_continente_seq", allocationSize = 1)
    @Column(name = "id_continente", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "premio")
    private Integer premio;
}