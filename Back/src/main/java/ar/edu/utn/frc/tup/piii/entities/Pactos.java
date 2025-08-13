package ar.edu.utn.frc.tup.piii.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pactos")
public class Pactos {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pactos_id_gen")
    @SequenceGenerator(name = "pactos_id_gen", sequenceName = "pactos_id_pactos_seq", allocationSize = 1)
    @Column(name = "id_pactos", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jugador1", nullable = false)
    private Jugadores jugador1;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jugador2", nullable = false)
    private Jugadores jugador2;

    @Size(max = 25)
    @Column(name = "tipo_pacto", length = 25)
    private String tipoPacto;

    @NotNull
    @Column(name = "activo", nullable = false)
    private Boolean activo = false;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_turno_ref", nullable = false)
    private ar.edu.utn.frc.tup.piii.entities.TurnosRefuerzos turnoRef;
}