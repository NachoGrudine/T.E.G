package ar.edu.utn.frc.tup.piii.models;

import ar.edu.utn.frc.tup.piii.entities.Mensajes;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MensajeModel {
    private Integer id;
    private Integer idJugador;
    private String mensaje;
    private Instant fechaHora;

    public void mapMensajeModel(Mensajes mensaje) {//MODELADO
        this.id = mensaje.getId();
        this.mensaje = mensaje.getMensaje();
        this.fechaHora = Instant.now();
        this.idJugador = mensaje.getJugador().getId();
    }
}
