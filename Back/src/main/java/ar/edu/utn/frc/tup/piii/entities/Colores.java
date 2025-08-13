package ar.edu.utn.frc.tup.piii.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "colores")
public class Colores {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "colores_id_gen")
    @SequenceGenerator(name = "colores_id_gen", sequenceName = "colores_id_color_seq", allocationSize = 1)
    @Column(name = "id_color", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "color", nullable = false, length = 50)
    private String color;
}