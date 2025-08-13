package ar.edu.utn.frc.tup.piii.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "acciones")
public class Acciones {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acciones_id_gen")
    @SequenceGenerator(name = "acciones_id_gen", sequenceName = "acciones_id_accion_seq", allocationSize = 1)
    @Column(name = "id_accion", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_turno_ataque", nullable = false)
    private ar.edu.utn.frc.tup.piii.entities.TurnosAtaques turnoAtaque;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reagrupacion")
    private ar.edu.utn.frc.tup.piii.entities.Reagrupaciones reagrupacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_combate")
    private ar.edu.utn.frc.tup.piii.entities.Combates combate;
}