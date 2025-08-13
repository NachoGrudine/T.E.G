package ar.edu.utn.frc.tup.piii.repositories;

import ar.edu.utn.frc.tup.piii.entities.Fronteras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FronterasRepository extends JpaRepository<Fronteras, Long> {
    List<Fronteras> findAllByPais1_IdOrPais2_Id(Integer idPais1, Integer idPais2);

}
