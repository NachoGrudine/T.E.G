package ar.edu.utn.frc.tup.piii.dtos.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FronteraJugadorDto {

    String nombreFrontera;
    int fichas;
    String duenio;
    Integer idDuenio;
    Integer idPais;
    String color;
}
