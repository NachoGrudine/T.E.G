package ar.edu.utn.frc.tup.piii.models;

import ar.edu.utn.frc.tup.piii.entities.Paises;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaisModel {
    private Integer id;
    private String nombre;
    private ContinenteModel continente;
    List<Integer> idPaisesFronteras;

    public void mapPaisModel(Paises pais) {//MODELADO
        this.id = pais.getId();
        this.nombre = pais.getNombre();
        this.continente = new ContinenteModel();
        this.continente.mapContinenteModel(pais.getContinente());
    }
}
