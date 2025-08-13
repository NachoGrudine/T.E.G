package ar.edu.utn.frc.tup.piii.services.interfaces;

import ar.edu.utn.frc.tup.piii.models.ObjetivoCantidadPaisesModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IObjetivosCantPaisesService  {

    List<ObjetivoCantidadPaisesModel> findAllByObjetivo_Id(Integer objetivoId);
}
