package ar.edu.utn.frc.tup.piii.repositories;

import ar.edu.utn.frc.tup.piii.entities.Jugadores;
import ar.edu.utn.frc.tup.piii.entities.Partidas;
import ar.edu.utn.frc.tup.piii.entities.Usuarios;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JugadoresRepository extends JpaRepository<Jugadores, Long> {
    List<Jugadores> getAllByPartidaOrderByIdAsc(@NotNull Partidas partidas);
    Optional<Jugadores> findByPartidaAndUsuario(Partidas partida, Usuarios usuario);
}
