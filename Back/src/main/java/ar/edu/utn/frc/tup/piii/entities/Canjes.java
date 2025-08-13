package ar.edu.utn.frc.tup.piii.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "canjes")
public class Canjes {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "canjes_id_gen")
    @SequenceGenerator(name = "canjes_id_gen", sequenceName = "canjes_id_canje_seq", allocationSize = 1)
    @Column(name = "id_canje", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jugador", nullable = false)
    private ar.edu.utn.frc.tup.piii.entities.Jugadores jugador;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_turno_refuerzo", nullable = false)
    private ar.edu.utn.frc.tup.piii.entities.TurnosRefuerzos turnoRefuerzo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_tarjeta_1", nullable = false)
    private ar.edu.utn.frc.tup.piii.entities.Tarjetas tarjeta1;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_tarjeta_2", nullable = false)
    private ar.edu.utn.frc.tup.piii.entities.Tarjetas tarjeta2;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_tarjeta_3", nullable = false)
    private ar.edu.utn.frc.tup.piii.entities.Tarjetas tarjeta3;

    @NotNull
    @Column(name = "cantidad_fichas", nullable = false)
    private Integer cantidadFichas;
}