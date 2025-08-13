package ar.edu.utn.frc.tup.piii.repositories;

import ar.edu.utn.frc.tup.piii.entities.TarjetasJugadores;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TarjetasJugadoresRepository extends JpaRepository<TarjetasJugadores, Long> {
    List<TarjetasJugadores> findAllByJugador_Id(Integer jugador_id);
}
