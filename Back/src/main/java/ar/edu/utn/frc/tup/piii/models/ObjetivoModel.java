package ar.edu.utn.frc.tup.piii.models;

import ar.edu.utn.frc.tup.piii.entities.Objetivos;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ObjetivoModel {
    private Integer idObjetivo;
    private ColorModel colorModel;
    private List<ObjetivoContModel> objetivoContModels;
    private List<ObjetivoCantidadPaisesModel> objetivoCantidadPaisesModels;

    public void mapObjetivoModel(Objetivos objetivos) {//MODELADO
        this.idObjetivo = objetivos.getId();
        if(objetivos.getColor() != null)
        {
            this.colorModel = new ColorModel();
            this.colorModel.mapColorModel(objetivos.getColor());
        }
        else {
            this.colorModel = null;
        }
    }

    public long getId() {
        return idObjetivo;
    }
}
