package ar.edu.utn.frc.tup.piii.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "objetivos")
public class Objetivos {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "objetivos_id_gen")
    @SequenceGenerator(name = "objetivos_id_gen", sequenceName = "objetivos_id_objetivo_seq", allocationSize = 1)
    @Column(name = "id_objetivo", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_color")
    private Colores color;
}