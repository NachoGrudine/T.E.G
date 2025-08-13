package ar.edu.utn.frc.tup.piii.models;

import ar.edu.utn.frc.tup.piii.entities.Tarjetas;
import ar.edu.utn.frc.tup.piii.enums.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TarjetaModel {
    private Integer id;
    private String nombre;
    private Simbolo simbolo;
    private String estado;

    public void mapTarjetaModel(Tarjetas tarjeta) {//MODELADO
        this.id = tarjeta.getId();
        this.nombre = tarjeta.getPais().getNombre();
        this.simbolo = Simbolo.valueOf(tarjeta.getSimbolo()) ;
        this.estado = tarjeta.getEstadoUso();
    }
}
