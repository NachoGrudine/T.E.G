package ar.edu.utn.frc.tup.piii.models;

import ar.edu.utn.frc.tup.piii.entities.Acciones;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AccionModel {
    private Integer id;
    private Integer idTurnoAtaque;
    private ReagrupacionModel reagrupacion;
    private CombateModel combate;

    public void mapAccionModel(Acciones accion) { //MODELADO
        this.id = accion.getId();
        this.idTurnoAtaque = accion.getTurnoAtaque().getId();

        if(accion.getReagrupacion() != null){
            this.reagrupacion = new ReagrupacionModel();
            this.reagrupacion.mapReagrupacionModel(accion.getReagrupacion());
        }
        else{
            this.reagrupacion = null;
        }
        if(accion.getCombate() != null){
            this.combate = new CombateModel();
            this.combate.mapCombateModel(accion.getCombate());
        }
        else{
            this.combate = null;
        }
    }
}
