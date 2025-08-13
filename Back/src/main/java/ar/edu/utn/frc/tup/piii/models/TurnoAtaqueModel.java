package ar.edu.utn.frc.tup.piii.models;

import ar.edu.utn.frc.tup.piii.entities.TurnosAtaques;
import ar.edu.utn.frc.tup.piii.enums.Estado;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TurnoAtaqueModel {
    private Integer id;
    private Integer idJugador;
    private Integer idTarjeta;
    private Estado estado;
    List<AccionModel> acciones;

    public void  mapTurnoAtaqueModel(TurnosAtaques turno) {//MODELADO
        this.id = turno.getId();
        this.idJugador = turno.getJugador().getId();
        if(turno.getTarjeta() != null) {
            this.idTarjeta = turno.getTarjeta().getId();
        }
        else{
            this.idTarjeta = null;
        }
        this.estado = Estado.valueOf(turno.getEstado());
    }
}
