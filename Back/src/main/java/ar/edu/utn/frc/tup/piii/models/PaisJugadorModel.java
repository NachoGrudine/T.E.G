package ar.edu.utn.frc.tup.piii.models;

import ar.edu.utn.frc.tup.piii.entities.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaisJugadorModel {
    private Integer id;
    private Integer idPartida;
    private Integer fichas;
    private Integer idJugador;
    private Integer idPais;

    public void mapPaisJugadorModel(PaisesJugadores paisJugador) {//MODELADO
        this.id = paisJugador.getId();
        this.idPartida = paisJugador.getPartida().getId();
        this.fichas = paisJugador.getFichas();
        this.idJugador = paisJugador.getJugador().getId();
        this.idPais = paisJugador.getPais().getId();
    }
}
