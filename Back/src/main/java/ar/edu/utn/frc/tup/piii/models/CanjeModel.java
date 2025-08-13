package ar.edu.utn.frc.tup.piii.models;

import ar.edu.utn.frc.tup.piii.entities.Canjes;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CanjeModel {
    private Integer id;
    private Integer idJugador;
    private Integer idTurnoRefuerzo;
    private Integer idtarjeta1;
    private Integer idtarjeta2;
    private Integer idtarjeta3;
    private Integer cantidadFichas;

    public void mapCanjeModel(Canjes canje) { //MODELADO
        this.id = canje.getId();
        this.idJugador = canje.getJugador().getId();
        this.idTurnoRefuerzo = canje.getTurnoRefuerzo().getId();
        this.idtarjeta1 =canje.getTarjeta1().getId();
        this.idtarjeta2=canje.getTarjeta2().getId();
        this.idtarjeta3=canje.getTarjeta3().getId();
        this.cantidadFichas = canje.getCantidadFichas();
    }
}
