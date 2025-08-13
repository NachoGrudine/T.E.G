package ar.edu.utn.frc.tup.piii.repositories;

import ar.edu.utn.frc.tup.piii.entities.Canjes;
import ar.edu.utn.frc.tup.piii.entities.TurnosRefuerzos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CanjesRepository extends JpaRepository<Canjes, Long> {
    List<Canjes> getAllByTurnoRefuerzo(TurnosRefuerzos turnosRefuerzos);
}
