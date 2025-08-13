package ar.edu.utn.frc.tup.piii.repositories;

import ar.edu.utn.frc.tup.piii.entities.Rondas;
import ar.edu.utn.frc.tup.piii.entities.TurnosAtaques;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurnosAtaquesRepository extends JpaRepository<TurnosAtaques, Long> {


    List<TurnosAtaques> getAllByRondaOrderByIdAsc(Rondas ronda);
}
