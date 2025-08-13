package ar.edu.utn.frc.tup.piii.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pilas_tarjetas")
public class PilasTarjetas {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pilas_tarjetas_id_gen")
    @SequenceGenerator(name = "pilas_tarjetas_id_gen", sequenceName = "pilas_tarjetas_id_pila_seq", allocationSize = 1)
    @Column(name = "id_pila", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_partida", nullable = false)
    private Partidas partida;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_tarjeta", nullable = false)
    private ar.edu.utn.frc.tup.piii.entities.Tarjetas tarjeta;
}