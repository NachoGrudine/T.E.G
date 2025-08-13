package ar.edu.utn.frc.tup.piii.services.interfaces;

import ar.edu.utn.frc.tup.piii.dtos.common.PaisJugadorDto;
import ar.edu.utn.frc.tup.piii.entities.*;
import ar.edu.utn.frc.tup.piii.models.JugadorModel;
import ar.edu.utn.frc.tup.piii.models.PaisJugadorModel;
import ar.edu.utn.frc.tup.piii.models.PaisModel;

import java.util.List;

public interface IPaisesJugadoresService {

    void asignarPaisesAJugadores(List<Paises> paises, List<Jugadores> jugadores, Partidas partida);
    List<PaisJugadorModel> getPaisesFromJugadorId(Jugadores jugador);

    PaisJugadorModel getPaisJugadorByPaisId(Integer idPais, Integer idPartida);

    PaisesJugadores buscarFromJugadorId(Jugadores jugador, Integer idPais);

    Boolean esPropio(List<PaisJugadorModel> paisesPropios, Integer id);
    List<Integer> getIdsPaisesByPlayer(Integer id);

    PaisJugadorDto getPaisJugadorByIdPartida_Pais(Integer idPartida, Integer idPais);


    void save(PaisesJugadores pais);

    List<PaisJugadorModel> getPaisesFromPartidaId(Partidas partida);

    List<PaisJugadorModel> getPaisesFromContinenteId(Integer continenteId);
    List<PaisJugadorModel> getPaisesFromJugadorAndFichas(Integer id, Integer cantidad);
}
