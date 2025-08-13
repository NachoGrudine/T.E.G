package ar.edu.utn.frc.tup.piii.repositories;

import ar.edu.utn.frc.tup.piii.entities.Paises;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaisesRepository extends JpaRepository<Paises, Long> {
    List<Paises> findAllByContinente_Id(Integer continenteId);
}
