package ar.edu.utn.frc.tup.piii.repositories;

import ar.edu.utn.frc.tup.piii.entities.Refuerzos;
import ar.edu.utn.frc.tup.piii.entities.TurnosRefuerzos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefuerzosRepository extends JpaRepository<Refuerzos, Long> {
    List<Refuerzos> getAllByTurnoRef(TurnosRefuerzos turnosRefuerzos);
}
