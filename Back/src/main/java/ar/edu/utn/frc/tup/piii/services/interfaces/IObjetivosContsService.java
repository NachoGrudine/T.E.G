package ar.edu.utn.frc.tup.piii.services.interfaces;

import ar.edu.utn.frc.tup.piii.models.ObjetivoContModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IObjetivosContsService {
    List<ObjetivoContModel> findAllByObjetivo_Id(Integer objetivoId);
}
