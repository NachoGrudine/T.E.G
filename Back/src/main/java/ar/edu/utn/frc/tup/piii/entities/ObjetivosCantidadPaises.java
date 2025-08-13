package ar.edu.utn.frc.tup.piii.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "objetivos_cantidad_paises")
public class ObjetivosCantidadPaises {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "objetivos_cantidad_paises_id_gen")
    @SequenceGenerator(name = "objetivos_cantidad_paises_id_gen", sequenceName = "objetivos_cantidad_paises_id_obj_pais_seq", allocationSize = 1)
    @Column(name = "id_obj_pais", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_objetivo", nullable = false)
    private Objetivos objetivo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_continente", nullable = false)
    private Continentes continente;

    @NotNull
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;
}