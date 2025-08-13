package ar.edu.utn.frc.tup.piii.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tarjetas")
public class Tarjetas {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tarjetas_id_gen")
    @SequenceGenerator(name = "tarjetas_id_gen", sequenceName = "tarjetas_id_tarjeta_seq", allocationSize = 1)
    @Column(name = "id_tarjeta", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_pais", nullable = false)
    private Paises pais;

    @Size(max = 25)
    @Column(name = "simbolo", length = 25)
    private String simbolo;

    @Size(max = 25)
    @Column(name = "estado_uso", length = 25)
    private String estadoUso;

}