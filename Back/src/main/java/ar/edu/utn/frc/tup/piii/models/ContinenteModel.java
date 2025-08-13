package ar.edu.utn.frc.tup.piii.models;
import ar.edu.utn.frc.tup.piii.entities.Continentes;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContinenteModel {
    private Integer id;
    private String nombre;
    private Integer premio;

    public void  mapContinenteModel(Continentes continente) {//MODELADO
        this.id = continente.getId();
        this.nombre = continente.getNombre();
        this.premio = continente.getPremio();
    }
}
