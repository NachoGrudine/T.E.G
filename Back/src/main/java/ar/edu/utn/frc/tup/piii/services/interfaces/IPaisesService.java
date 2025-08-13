package ar.edu.utn.frc.tup.piii.services.interfaces;

import ar.edu.utn.frc.tup.piii.entities.Paises;
import ar.edu.utn.frc.tup.piii.models.PaisModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface IPaisesService {
    List<PaisModel> getAllPaises();
    List<Paises> getAllPaisesEntity();
    List<PaisModel> getAllPaisesByContinenteId(Integer continenteId);
    Optional<Paises> getPaisByIdEntity(Long idPais);
    PaisModel getPaisById(Integer idPais);
}
