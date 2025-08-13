package ar.edu.utn.frc.tup.piii.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "partidas")
public class Partidas {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partidas_id_gen")
    @SequenceGenerator(name = "partidas_id_gen", sequenceName = "partidas_id_partida_seq", allocationSize = 1)
    @Column(name = "id_partida", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private ar.edu.utn.frc.tup.piii.entities.Usuarios usuario;

    @Size(max = 25)
    @Column(name = "estado", length = 25)
    private String estado;

    @Size(max = 25)
    @Column(name = "tipo", length = 25)
    private String tipo;

    @Column(name = "cantidad_para_ganar")
    private Integer cantidadParaGanar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_jugador_ganador")
    private Jugadores jugadorGanador;
}