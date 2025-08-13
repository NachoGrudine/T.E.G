package ar.edu.utn.frc.tup.piii.repositories;

import ar.edu.utn.frc.tup.piii.entities.Colores;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColoresRepository extends JpaRepository<Colores, Long> {
    Long id(Integer id);
}
