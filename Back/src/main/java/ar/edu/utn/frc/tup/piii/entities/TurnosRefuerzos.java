package ar.edu.utn.frc.tup.piii.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "turnos_refuerzos")
public class TurnosRefuerzos {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "turnos_refuerzos_id_gen")
    @SequenceGenerator(name = "turnos_refuerzos_id_gen", sequenceName = "turnos_refuerzos_id_turno_refuerzo_seq", allocationSize = 1)
    @Column(name = "id_turno_refuerzo", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jugador", nullable = false)
    private Jugadores jugador;

    @Size(max = 25)
    @Column(name = "estado", length = 25)
    private String estado;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_ronda", nullable = false)
    private Rondas ronda;


}