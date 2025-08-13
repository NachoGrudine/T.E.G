package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.entities.ObjetivosConts;
import ar.edu.utn.frc.tup.piii.models.ObjetivoContModel;
import ar.edu.utn.frc.tup.piii.models.ObjetivoModel;
import ar.edu.utn.frc.tup.piii.repositories.FronterasRepository;
import ar.edu.utn.frc.tup.piii.repositories.ObjetivosContsRepository;
import ar.edu.utn.frc.tup.piii.services.interfaces.IObjetivosContsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ObjetivosContsService implements IObjetivosContsService{

    private final ObjetivosContsRepository objetivosContsRepository;

    @Autowired
    public ObjetivosContsService(ObjetivosContsRepository objetivosContsRepository) {
        this.objetivosContsRepository = objetivosContsRepository;
    }

    public List<ObjetivoContModel> findAllByObjetivo_Id(Integer objetivoId)
    {
        List<ObjetivosConts> listaEntity = objetivosContsRepository.findAllByObjetivo_Id(objetivoId);
        List<ObjetivoContModel> objetivosContModel = new ArrayList<>();
        for(ObjetivosConts objetivosConts : listaEntity)
        {
            ObjetivoContModel objetivoContModel = new ObjetivoContModel();
            objetivoContModel.mapObjetivoContModel(objetivosConts);
            objetivosContModel.add(objetivoContModel);
        }
        return objetivosContModel;
    }
}
