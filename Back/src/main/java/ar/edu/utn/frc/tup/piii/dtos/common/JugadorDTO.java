package ar.edu.utn.frc.tup.piii.dtos.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JugadorDTO {
    private Integer idUsuario;
    private String nombre;
    private Integer idColor;
    private String tipoJugador;
}
