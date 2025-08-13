package ar.edu.utn.frc.tup.piii.models;

import ar.edu.utn.frc.tup.piii.entities.Rondas;
import ar.edu.utn.frc.tup.piii.enums.Estado;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RondaModel {
    private Integer id;
    private Integer idPartida;
    private Integer numero;
    private Estado estado;
    List<TurnoRefuerzoModel> turnosRefuerzo;
    List<TurnoAtaqueModel> turnosAtaque;
    List<JugadorModel> jugadores;

    public void mapRondaModel(Rondas ronda) {//MODELADO
        this.id = ronda.getId();
        this.idPartida = ronda.getPartida().getId();
        this.numero = ronda.getNumero();
        this.estado = Estado.valueOf(ronda.getEstado());
    }
}
