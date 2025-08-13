package ar.edu.utn.frc.tup.piii.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "combates")
public class Combates {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "combates_id_gen")
    @SequenceGenerator(name = "combates_id_gen", sequenceName = "combates_id_combate_seq", allocationSize = 1)
    @Column(name = "id_combate", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jugador_ataque", nullable = false)
    private ar.edu.utn.frc.tup.piii.entities.Jugadores jugadorAtaque;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_pais_atk", nullable = false)
    private ar.edu.utn.frc.tup.piii.entities.Paises paisAtk;

    @NotNull
    @Column(name = "fichas_atk", nullable = false)
    private Integer fichasAtk;

    @NotNull
    @Column(name = "atk_dado_1", nullable = false)
    private Integer atkDado1;

    @Column(name = "atk_dado_2")
    private Integer atkDado2;

    @Column(name = "atk_dado_3")
    private Integer atkDado3;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jugador_defensa", nullable = false)
    private ar.edu.utn.frc.tup.piii.entities.Jugadores jugadorDefensa;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_pais_def", nullable = false)
    private ar.edu.utn.frc.tup.piii.entities.Paises paisDef;

    @NotNull
    @Column(name = "fichas_def", nullable = false)
    private Integer fichasDef;

    @NotNull
    @Column(name = "def_dado_1", nullable = false)
    private Integer defDado1;

    @Column(name = "def_dado_2")
    private Integer defDado2;

    @Column(name = "def_dado_3")
    private Integer defDado3;
}