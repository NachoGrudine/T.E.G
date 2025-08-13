package ar.edu.utn.frc.tup.piii.repositories;

import ar.edu.utn.frc.tup.piii.entities.ObjetivosCantidadPaises;
import ar.edu.utn.frc.tup.piii.entities.ObjetivosConts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObjetivosContsRepository extends JpaRepository<ObjetivosConts, Long> {
    List<ObjetivosConts> findAllByObjetivo_Id(Integer objetivoId);
}
