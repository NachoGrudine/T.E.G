package ar.edu.utn.frc.tup.piii.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tarjetas_jugadores")
public class TarjetasJugadores {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tarjetas_jugadores_id_gen")
    @SequenceGenerator(name = "tarjetas_jugadores_id_gen", sequenceName = "tarjetas_jugadores_id_tarjetas_jugador_seq", allocationSize = 1)
    @Column(name = "id_tarjetas_jugador", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_tarjeta", nullable = false)
    private Tarjetas tarjeta;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jugador", nullable = false)
    private Jugadores jugador;

}