package ar.edu.utn.frc.tup.piii.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "fronteras", uniqueConstraints = {@UniqueConstraint(name = "frontera_uniq", columnNames = {"id_pais1", "id_pais2"})})
public class Fronteras {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fronteras_id_gen")
    @SequenceGenerator(name = "fronteras_id_gen", sequenceName = "fronteras_id_pais_frontera_seq", allocationSize = 1)
    @Column(name = "id_pais_frontera", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_pais1", nullable = false)
    private ar.edu.utn.frc.tup.piii.entities.Paises pais1;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_pais2", nullable = false)
    private ar.edu.utn.frc.tup.piii.entities.Paises pais2;
}