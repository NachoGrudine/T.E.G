package ar.edu.utn.frc.tup.piii.services.interfaces;

import ar.edu.utn.frc.tup.piii.dtos.common.JugadorDTO;
import ar.edu.utn.frc.tup.piii.entities.Jugadores;
import ar.edu.utn.frc.tup.piii.entities.Partidas;
import ar.edu.utn.frc.tup.piii.models.JugadorModel;

import java.util.List;
import java.util.Optional;

public interface IJugadoresService {

    List<Jugadores> getAllByPartida(Partidas partida);
    void saveAll(List<Jugadores> jugadoresEntity);

    void agregarJugador(Partidas partida, JugadorDTO jugadorDTO);

    List<JugadorModel> mapearJugadores(List<Jugadores> jugadoresEntity);
    JugadorModel mapearJugador(Jugadores jugador);
    Jugadores getJugadorByIdEntity(Long aLong);
    JugadorModel buscarJugador(Long id);
}
