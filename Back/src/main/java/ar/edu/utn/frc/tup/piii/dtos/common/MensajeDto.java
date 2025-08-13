package ar.edu.utn.frc.tup.piii.dtos.common;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class MensajeDto {
    private Integer idJugador;
    private String mensaje;

}
