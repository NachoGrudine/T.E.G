package ar.edu.utn.frc.tup.piii.services.interfaces;

import ar.edu.utn.frc.tup.piii.dtos.common.FichasDTO;
import ar.edu.utn.frc.tup.piii.dtos.common.RefuerzoPostDTO;
import ar.edu.utn.frc.tup.piii.models.JugadorModel;
import ar.edu.utn.frc.tup.piii.models.TurnoRefuerzoModel;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ITurnosRefuerzosService {
    TurnoRefuerzoModel obtenerTurnoRefuerzo(Long id);
    FichasDTO obtenerFichas(Long idTurno);

    void guardarRefuerzos(List<RefuerzoPostDTO> refuerzos);

    void terminarTurnoRefuerzo(Long id);
}
