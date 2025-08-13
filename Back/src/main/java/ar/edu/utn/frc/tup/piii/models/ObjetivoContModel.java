package ar.edu.utn.frc.tup.piii.models;

import ar.edu.utn.frc.tup.piii.entities.ObjetivosConts;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObjetivoContModel {
    private Integer id;
    private Integer idObjetivo;
    private ContinenteModel continenteModel;

    public void mapObjetivoContModel(ObjetivosConts objetivosConts) {//MODELADO
        this.id = objetivosConts.getId();
        this.idObjetivo = objetivosConts.getObjetivo().getId();
        this.continenteModel = new ContinenteModel();
        this.continenteModel.mapContinenteModel(objetivosConts.getContinente());
    }
}
