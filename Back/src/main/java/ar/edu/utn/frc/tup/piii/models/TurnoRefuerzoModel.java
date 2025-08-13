package ar.edu.utn.frc.tup.piii.models;


import ar.edu.utn.frc.tup.piii.entities.*;


import ar.edu.utn.frc.tup.piii.enums.Estado;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
public class TurnoRefuerzoModel {
    private Integer id;
    private Integer idJugador;
    private Estado estado;
    private List<RefuerzoModel> refuerzoModels;
    private List<CanjeModel> canjeModels;

    public void  mapTurnoRefuerzoModel(TurnosRefuerzos turno) {
        this.id = turno.getId();
        this.idJugador = turno.getJugador().getId();
        this.estado = Estado.valueOf(turno.getEstado());
    }
}
