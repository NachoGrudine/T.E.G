package ar.edu.utn.frc.tup.piii.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "jugadores")
public class Jugadores {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jugadores_id_gen")
    @SequenceGenerator(name = "jugadores_id_gen", sequenceName = "jugadores_id_jugador_seq", allocationSize = 1)
    @Column(name = "id_jugador", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_partida", nullable = false)
    private ar.edu.utn.frc.tup.piii.entities.Partidas partida;

    @Size(max = 25)
    @Column(name = "tipo_jugador", length = 25)
    private String tipoJugador;

    @Size(max = 100)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_color", nullable = false)
    private Colores color;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_objetivo")
    private ar.edu.utn.frc.tup.piii.entities.Objetivos objetivo;

    @Size(max = 25)
    @Column(name = "estado_jugador", length = 25)
    private String estadoJugador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private ar.edu.utn.frc.tup.piii.entities.Usuarios usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_jugador_asesino")
    private Jugadores jugadorAsesino;
}