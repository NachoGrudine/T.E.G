package ar.edu.utn.frc.tup.piii.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "refuerzos")
public class Refuerzos {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "refuerzos_id_gen")
    @SequenceGenerator(name = "refuerzos_id_gen", sequenceName = "refuerzos_id_refuerzo_seq", allocationSize = 1)
    @Column(name = "id_refuerzo", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_turno_ref", nullable = false)
    private ar.edu.utn.frc.tup.piii.entities.TurnosRefuerzos turnoRef;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_pais_jugador", nullable = false)
    private PaisesJugadores paisJugador;

    @NotNull
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Size(max = 25)
    @Column(name = "tipo_fichas", length = 25)
    private String tipoFichas;

}