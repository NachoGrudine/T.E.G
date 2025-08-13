package ar.edu.utn.frc.tup.piii.dtos.common;

import ar.edu.utn.frc.tup.piii.enums.TipoFicha;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefuerzoPostDTO {
    private Integer idTurnoRef;
    private Integer idPais;
    private Integer cantidad;
    private String tipoFicha;


}
