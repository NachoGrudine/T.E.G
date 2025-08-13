package ar.edu.utn.frc.tup.piii.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "reagrupaciones")
public class Reagrupaciones {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reagrupaciones_id_gen")
    @SequenceGenerator(name = "reagrupaciones_id_gen", sequenceName = "reagrupaciones_id_reagrupacion_seq", allocationSize = 1)
    @Column(name = "id_reagrupacion", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_paisorigen", nullable = false)
    private Paises paisorigen;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_paisdestino", nullable = false)
    private Paises paisdestino;

    @NotNull
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

}