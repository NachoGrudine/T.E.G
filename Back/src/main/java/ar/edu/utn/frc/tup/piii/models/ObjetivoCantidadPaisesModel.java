package ar.edu.utn.frc.tup.piii.models;

import ar.edu.utn.frc.tup.piii.entities.ObjetivosCantidadPaises;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObjetivoCantidadPaisesModel {
    private Integer id;
    private Integer idObjetivo;
    private ContinenteModel continenteModel;
    private Integer cantidad;

    public void mapObjetivoCantidadPaisesModel(ObjetivosCantidadPaises objetivosCantidadPaises) {//MODELADO
        this.id = objetivosCantidadPaises.getId();
        this.idObjetivo = objetivosCantidadPaises.getObjetivo().getId();
        this.continenteModel = new ContinenteModel();
        this.continenteModel.mapContinenteModel(objetivosCantidadPaises.getContinente());
        this.cantidad = objetivosCantidadPaises.getCantidad();

    }
}
