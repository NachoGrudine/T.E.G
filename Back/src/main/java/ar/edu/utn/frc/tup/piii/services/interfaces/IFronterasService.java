package ar.edu.utn.frc.tup.piii.services.interfaces;

import ar.edu.utn.frc.tup.piii.entities.Fronteras;
import ar.edu.utn.frc.tup.piii.models.FronteraModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface IFronterasService {
     List<Integer> findAllByPais1_IdOrPais2_Id(Integer pais);
     Map<Integer, List<Integer>> crearGrafo();
}
