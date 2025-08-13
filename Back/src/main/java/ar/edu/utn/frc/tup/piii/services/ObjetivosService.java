package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.entities.Objetivos;
import ar.edu.utn.frc.tup.piii.entities.ObjetivosCantidadPaises;
import ar.edu.utn.frc.tup.piii.entities.ObjetivosConts;
import ar.edu.utn.frc.tup.piii.models.ObjetivoCantidadPaisesModel;
import ar.edu.utn.frc.tup.piii.models.ObjetivoContModel;
import ar.edu.utn.frc.tup.piii.models.ObjetivoModel;
import ar.edu.utn.frc.tup.piii.repositories.ObjetivosCantidadPaisesRepository;
import ar.edu.utn.frc.tup.piii.repositories.ObjetivosContsRepository;
import ar.edu.utn.frc.tup.piii.repositories.ObjetivosRepository;
import ar.edu.utn.frc.tup.piii.services.interfaces.IObjetivosCantPaisesService;
import ar.edu.utn.frc.tup.piii.services.interfaces.IObjetivosContsService;
import ar.edu.utn.frc.tup.piii.services.interfaces.IObjetivosService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class ObjetivosService implements IObjetivosService {

    private final ObjetivosRepository objetivosRepository;
    private final IObjetivosContsService objetivosContsService;
    private final IObjetivosCantPaisesService objetivosCantPaisesService;

    @Autowired
    public ObjetivosService(ObjetivosRepository objetivosRepository,
                            IObjetivosContsService objetivosContsService,
                            IObjetivosCantPaisesService objetivosCantPaisesService) {
        this.objetivosRepository = objetivosRepository;
        this.objetivosContsService = objetivosContsService;
        this.objetivosCantPaisesService = objetivosCantPaisesService;
    }

    public List<ObjetivoModel> getAllObjetivos()
    {
        var objetivosEntity = objetivosRepository.findAll(); // objetivos

        List<ObjetivoModel> objetivosModels = new ArrayList<>(); // hago el model de objetivos

        for(Objetivos objetivoEntity : objetivosEntity){ // por cada entity
            ObjetivoModel objetivoModel = new ObjetivoModel();
            objetivoModel.mapObjetivoModel(objetivoEntity);

            List<ObjetivoContModel> objetivoContModels = objetivosContsService.findAllByObjetivo_Id(objetivoEntity.getId());
            List<ObjetivoCantidadPaisesModel> objetivoCantidadPaisesModels = objetivosCantPaisesService.findAllByObjetivo_Id(objetivoEntity.getId());


            objetivoModel.setObjetivoContModels(objetivoContModels);
            objetivoModel.setObjetivoCantidadPaisesModels(objetivoCantidadPaisesModels);

            objetivosModels.add(objetivoModel);

        }
        return objetivosModels;

    }

    @Override
    public List<Objetivos> getAllObjetivosEntity() {
        return objetivosRepository.findAll();
    }



    public ObjetivoModel getByIdObjetivo(Long id) {
        Objetivos entity = objetivosRepository.findById(id).get();
        ObjetivoModel objetivoModel = new ObjetivoModel();

        List<ObjetivoContModel> objetivoContModels = objetivosContsService.findAllByObjetivo_Id(entity.getId());
        List<ObjetivoCantidadPaisesModel> objetivoCantidadPaisesModels = objetivosCantPaisesService.findAllByObjetivo_Id(entity.getId());

        objetivoModel.mapObjetivoModel(entity);
        objetivoModel.setObjetivoContModels(objetivoContModels);
        objetivoModel.setObjetivoCantidadPaisesModels(objetivoCantidadPaisesModels);

        return objetivoModel;
    }

    @Override
    public Objetivos getObjetivoJugadorById(long id) {
        return objetivosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Objetivo no encontrado con id: " + id));
    }


}
