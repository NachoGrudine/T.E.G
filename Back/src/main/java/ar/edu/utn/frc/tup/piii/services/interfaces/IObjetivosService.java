package ar.edu.utn.frc.tup.piii.services.interfaces;

import ar.edu.utn.frc.tup.piii.entities.Objetivos;
import ar.edu.utn.frc.tup.piii.models.ObjetivoModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IObjetivosService {
    List<ObjetivoModel> getAllObjetivos();

    ObjetivoModel getByIdObjetivo(Long id);

    List<Objetivos> getAllObjetivosEntity();

    Objetivos getObjetivoJugadorById(long id);
}
