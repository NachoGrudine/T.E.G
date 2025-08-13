package ar.edu.utn.frc.tup.piii.models;

import ar.edu.utn.frc.tup.piii.entities.Refuerzos;
import ar.edu.utn.frc.tup.piii.enums.TipoFicha;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefuerzoModel {
    private Integer id;
    private Integer idTurnoRef;
    private PaisJugadorModel paisJugadorModel;
    private Integer cantidad;
    private TipoFicha tipoFichas;

    public void  mapRefuerzoModel(Refuerzos refuerzo) {//MODELADO
        this.id = refuerzo.getId();
        this.idTurnoRef = refuerzo.getTurnoRef().getId();
        this.paisJugadorModel = new PaisJugadorModel();
        this.paisJugadorModel.mapPaisJugadorModel(refuerzo.getPaisJugador());
        this.cantidad = refuerzo.getCantidad();
        this.tipoFichas= TipoFicha.valueOf(refuerzo.getTipoFichas());
    }
}
