package ar.edu.utn.frc.tup.piii.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "mensajes")
public class Mensajes {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mensajes_id_gen")
    @SequenceGenerator(name = "mensajes_id_gen", sequenceName = "mensajes_id_mensaje_seq", allocationSize = 1)
    @Column(name = "id_mensaje", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jugador", nullable = false)
    private Jugadores jugador;

    @NotNull
    @Column(name = "mensaje", nullable = false, length = Integer.MAX_VALUE)
    private String mensaje;

    @NotNull
    @Column(name = "fecha_hora", nullable = false)
    private Instant fechaHora;
}