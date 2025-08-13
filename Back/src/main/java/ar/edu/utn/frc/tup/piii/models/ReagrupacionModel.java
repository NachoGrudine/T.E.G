package ar.edu.utn.frc.tup.piii.models;

import ar.edu.utn.frc.tup.piii.entities.Reagrupaciones;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReagrupacionModel {
    private Integer id;
    private Integer idPaisorigen;
    private Integer idPaisdestino;
    private Integer cantidad;

    public void mapReagrupacionModel(Reagrupaciones reagrupacion) {//MODELADO
        this.id = reagrupacion.getId();
        this.idPaisorigen = reagrupacion.getPaisorigen().getId();
        this.idPaisdestino = reagrupacion.getPaisdestino().getId();
        this.cantidad = reagrupacion.getCantidad();
    }
}
