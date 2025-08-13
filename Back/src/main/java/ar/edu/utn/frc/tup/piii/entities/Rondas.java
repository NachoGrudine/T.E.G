package ar.edu.utn.frc.tup.piii.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "rondas")
public class Rondas {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rondas_id_gen")
    @SequenceGenerator(name = "rondas_id_gen", sequenceName = "rondas_id_ronda_seq", allocationSize = 1)
    @Column(name = "id_ronda", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_partida", nullable = false)
    private Partidas partida;

    @NotNull
    @Column(name = "numero", nullable = false)
    private Integer numero;

    @Size(max = 25)
    @Column(name = "estado", length = 25)
    private String estado;

}