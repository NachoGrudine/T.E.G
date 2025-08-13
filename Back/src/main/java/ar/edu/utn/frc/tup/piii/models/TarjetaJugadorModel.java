package ar.edu.utn.frc.tup.piii.models;

import ar.edu.utn.frc.tup.piii.entities.TarjetasJugadores;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TarjetaJugadorModel {
    private Integer id;
    private TarjetaModel tarjeta;
    private Integer idJugador;

    public void mapTarjetaJugadorModel(TarjetasJugadores tarjetasJugadores) {//MODELADO
        this.id = tarjetasJugadores.getId();
        this.tarjeta = new TarjetaModel();
        this.tarjeta.mapTarjetaModel(tarjetasJugadores.getTarjeta());
        this.idJugador = tarjetasJugadores.getJugador().getId();
    }
}
