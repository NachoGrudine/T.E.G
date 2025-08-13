package ar.edu.utn.frc.tup.piii.repositories;

import ar.edu.utn.frc.tup.piii.entities.Partidas;
import ar.edu.utn.frc.tup.piii.entities.Rondas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RondasRepository extends JpaRepository<Rondas, Long> {
    Rondas findTopByPartidaOrderByIdDesc(Partidas partida);
}
