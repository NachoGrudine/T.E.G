package ar.edu.utn.frc.tup.piii.models;

import ar.edu.utn.frc.tup.piii.entities.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PilaTarjetaModel {
    private Integer id;
    private Integer idPartida;
    private TarjetaModel tarjeta;

    public void mapPilaTarjetaModel(PilasTarjetas pilaTarjetas) {//MODELADO
        this.id = pilaTarjetas.getId();
        this.idPartida = pilaTarjetas.getPartida().getId();
        this.tarjeta = new TarjetaModel();
        this.tarjeta.mapTarjetaModel(pilaTarjetas.getTarjeta());
    }
}
