package ar.edu.utn.frc.tup.piii.dtos.common;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaisJugadorDto {


    String nombrePaisJugador;
    int fichas;
    String duenio;
    Integer idDuenio;
    Integer idPais;

    List<FronteraJugadorDto> fronteras;


}
