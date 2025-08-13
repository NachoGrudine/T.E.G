package ar.edu.utn.frc.tup.piii.repositories;

import ar.edu.utn.frc.tup.piii.entities.ObjetivosCantidadPaises;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObjetivosCantidadPaisesRepository extends JpaRepository<ObjetivosCantidadPaises, Long> {
    List<ObjetivosCantidadPaises> findAllByObjetivo_Id(Integer objetivoId);
}
