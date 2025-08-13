package ar.edu.utn.frc.tup.piii.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "turnos_ataques")
public class TurnosAtaques {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "turnos_ataques_id_gen")
    @SequenceGenerator(name = "turnos_ataques_id_gen", sequenceName = "turnos_ataques_id_turno_atq_seq", allocationSize = 1)
    @Column(name = "id_turno_atq", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jugador", nullable = false)
    private Jugadores jugador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tarjeta")
    private Tarjetas tarjeta;

    @Size(max = 25)
    @Column(name = "estado", length = 25)
    private String estado;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_ronda", nullable = false)
    private Rondas ronda;

}