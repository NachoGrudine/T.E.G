package ar.edu.utn.frc.tup.piii.models;


import ar.edu.utn.frc.tup.piii.entities.*;
import ar.edu.utn.frc.tup.piii.enums.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JugadorModel {
    private Integer id;
    private Integer idPartida;
    private TipoJugador tipoJugador;
    private String nombre;
    private ColorModel color;
    private ObjetivoModel objetivo;
    private EstadoJugador estadoJugador;
    private Integer idUsuario;
    private Integer idJugadorAsesino;

    List<TarjetaModel> tarjetas;
    List<PaisJugadorModel> paises;

    public void mapJugadorModel(Jugadores jugador) {//MODELADO
        this.id = jugador.getId();
        this.nombre = jugador.getNombre();
        this.idPartida = jugador.getPartida().getId();
        this.tipoJugador = TipoJugador.valueOf(jugador.getTipoJugador());
        ColorModel colorModel = new ColorModel();
        colorModel.mapColorModel(jugador.getColor());
        this.color = colorModel;
        ObjetivoModel objetivoModel = new ObjetivoModel();
        if(jugador.getObjetivo() != null){
            objetivoModel.mapObjetivoModel(jugador.getObjetivo());
            this.objetivo = objetivoModel;
        }
        else{
            this.objetivo = null;
        }
        this.estadoJugador = EstadoJugador.valueOf(jugador.getEstadoJugador());
        this.idUsuario = jugador.getUsuario() != null ? jugador.getUsuario().getId() : null;
        this.idJugadorAsesino = jugador.getJugadorAsesino() != null ? jugador.getJugadorAsesino().getId() : null;
    }
}
