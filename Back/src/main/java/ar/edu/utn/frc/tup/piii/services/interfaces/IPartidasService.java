package ar.edu.utn.frc.tup.piii.services.interfaces;

import ar.edu.utn.frc.tup.piii.dtos.common.*;
import ar.edu.utn.frc.tup.piii.models.*;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IPartidasService {

    List<PartidaModel> getPartidas(Long idUsuario);

    PartidaModel crearPartida(Long idUsuario);

    PartidaModel buscarPartida(Long idPartida);

    List<PartidaModel>obtenerPartidasPorTipoYEstado();

    void empezarPartida(Long idPartida);

    PartidaModel agregarJugador(Long idPartida, JugadorDTO jugadorDTO);

    List<ColorModel> buscarColores(Long idPartida);

    RondaModel buscarUltimaRonda(Long idPartida, Long idJugador );
    TurnoRefuerzoModel obtenerTurnoRefuerzo(Long id);
    public FichasDTO obtenerFichas(Long id);

    void guardarRefuerzos(List<RefuerzoPostDTO> refuerzos);

    void terminarTurnoRefuerzo(Long idPartida, Long idRonda, Long idTurno);

    TurnoAtaqueModel obtenerTurnoAtaque(Long id);

    List<PaisJugadorModel> getPaisesFromPartida(Long id);

    void configurarPartida(Long id, Integer objetivo, String tipo);

    AccionModel hacerAtaque(AccionCombatePostDTO accionCombatePostDTO);

    AccionModel hacerReagrupacion(AccionReagrupacionPostDTO accionReagrupacionPostDTO);

    void terminarTurnoAtaque(Long idPartida, Long idRonda, Long idTurno);
    GanadorDTO chekearGanador(Integer idJugador);
}
