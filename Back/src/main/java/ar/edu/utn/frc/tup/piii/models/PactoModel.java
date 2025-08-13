package ar.edu.utn.frc.tup.piii.models;

import ar.edu.utn.frc.tup.piii.entities.*;
import ar.edu.utn.frc.tup.piii.enums.TipoPacto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PactoModel {
    private Integer id;
    private Integer idJugador1;
    private Integer idJugador2;
    private TipoPacto tipoPacto;
    private Boolean activo;
    private Integer idTurnoRef;

    public void mapPactoModel (Pactos pacto) {//MODELADO
        this.id = pacto.getId();
        this.idJugador1 = pacto.getJugador1().getId();
        this.idJugador2 = pacto.getJugador2().getId();
        this.tipoPacto = TipoPacto.valueOf(pacto.getTipoPacto()) ;

        this.activo = pacto.getActivo();
        this.idTurnoRef = pacto.getTurnoRef().getId();
    }
    //NO CREO QUE NECESITE LISTAS ADICIONALES
}
