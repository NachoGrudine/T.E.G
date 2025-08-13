package ar.edu.utn.frc.tup.piii.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "paises_jugadores")
public class PaisesJugadores {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paises_jugadores_id_gen")
    @SequenceGenerator(name = "paises_jugadores_id_gen", sequenceName = "paises_jugadores_id_paises_jugador_seq", allocationSize = 1)
    @Column(name = "id_paises_jugador", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_partida", nullable = false)
    private ar.edu.utn.frc.tup.piii.entities.Partidas partida;

    @NotNull
    @Column(name = "fichas", nullable = false)
    private Integer fichas;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jugador", nullable = false)
    private Jugadores jugador;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_pais", nullable = false)
    private Paises pais;
}