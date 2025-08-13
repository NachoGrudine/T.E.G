package ar.edu.utn.frc.tup.piii.models;
import ar.edu.utn.frc.tup.piii.enums.*;

import ar.edu.utn.frc.tup.piii.entities.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PartidaModel {
    private Integer id;
    private Estado estado;
    private Integer idUsuario;
    private Integer cantidadParaGanar;
    private TipoPartida tipoPartida;
    private List<JugadorModel> jugadores;

    public void mapPartidaModel(Partidas partida) {//MODELADO
        this.id = partida.getId();
        this.estado = Estado.valueOf(partida.getEstado());
        this.idUsuario = partida.getUsuario().getId();
        this.cantidadParaGanar = partida.getCantidadParaGanar();
        if (partida.getTipo() != null) {
            this.tipoPartida = TipoPartida.valueOf(partida.getTipo());
        } else {
            this.tipoPartida = null;
        }
    }
}
