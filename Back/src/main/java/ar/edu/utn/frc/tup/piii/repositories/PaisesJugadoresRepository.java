package ar.edu.utn.frc.tup.piii.repositories;

import ar.edu.utn.frc.tup.piii.entities.Jugadores;
import ar.edu.utn.frc.tup.piii.entities.PaisesJugadores;
import ar.edu.utn.frc.tup.piii.entities.Partidas;
import ar.edu.utn.frc.tup.piii.models.PaisJugadorModel;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaisesJugadoresRepository  extends JpaRepository<PaisesJugadores, Integer> {
    List<PaisesJugadores> getAllByJugador(@NotNull Jugadores jugador);

    List<PaisesJugadores> getAllByJugadorId(@NotNull Integer idJugador);

    PaisesJugadores findByPartida_IdAndPais_Id(Integer idPartida, Integer idPais);

    List<PaisesJugadores> getAllByPartida(Partidas partida);

    List<PaisesJugadores> getAllByPais_Continente_Id(Integer paisContinenteId);

    List<PaisesJugadores> getAllByJugador_IdAndFichasGreaterThan(Integer jugadorId, @NotNull Integer fichasIsGreaterThan);
}
