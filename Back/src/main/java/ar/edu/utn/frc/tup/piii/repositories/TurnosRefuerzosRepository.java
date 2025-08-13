package ar.edu.utn.frc.tup.piii.repositories;

import ar.edu.utn.frc.tup.piii.entities.Rondas;
import ar.edu.utn.frc.tup.piii.entities.TurnosRefuerzos;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurnosRefuerzosRepository extends JpaRepository<TurnosRefuerzos, Long> {
    List<TurnosRefuerzos> getAllByRondaOrderByIdAsc(@NotNull Rondas ronda);
}
