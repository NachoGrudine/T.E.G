package ar.edu.utn.frc.tup.piii.services.interfaces;

import ar.edu.utn.frc.tup.piii.entities.Partidas;
import ar.edu.utn.frc.tup.piii.entities.Rondas;
import ar.edu.utn.frc.tup.piii.models.PartidaModel;
import ar.edu.utn.frc.tup.piii.models.RondaModel;
import ar.edu.utn.frc.tup.piii.models.TurnoAtaqueModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IRondasService {
    Rondas getUltimaRondaPartida(Partidas partida);
    RondaModel crearRonda(PartidaModel partida, Partidas partidaEntity);

    TurnoAtaqueModel getNextTurnAtaque(Long idRonda, PartidaModel partidaModel, Long idTurno);
}
