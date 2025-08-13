package ar.edu.utn.frc.tup.piii.repositories;

import ar.edu.utn.frc.tup.piii.entities.Partidas;
import ar.edu.utn.frc.tup.piii.entities.PilasTarjetas;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PilasTarjetasRepository extends JpaRepository<PilasTarjetas, Long> {
    List<PilasTarjetas> getAllByPartida(@NotNull Partidas partida);
}
