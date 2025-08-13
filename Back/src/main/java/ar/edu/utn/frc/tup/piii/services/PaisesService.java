package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.entities.Fronteras;
import ar.edu.utn.frc.tup.piii.entities.Paises;
import ar.edu.utn.frc.tup.piii.models.PaisModel;
import ar.edu.utn.frc.tup.piii.repositories.PaisesRepository;
import ar.edu.utn.frc.tup.piii.services.interfaces.IFronterasService;
import ar.edu.utn.frc.tup.piii.services.interfaces.IPaisesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaisesService implements IPaisesService {

    private final PaisesRepository paisesRepository;
    private final IFronterasService fronterasService;

    @Autowired
    public PaisesService(PaisesRepository paisesRepository, IFronterasService fronterasService) {
        this.paisesRepository = paisesRepository;
        this.fronterasService = fronterasService;
    }

    public List<PaisModel> getAllPaises()
    {
        List<PaisModel> paises = new ArrayList<>();

        var paisesEntity = paisesRepository.findAll();

        for(Paises paisEntity : paisesEntity) //recorremos los paises entities
        {
            PaisModel paisModel = new PaisModel();// creamos CADA pais model
            paisModel.mapPaisModel(paisEntity);

            List<Integer> fronterasModels = fronterasService.findAllByPais1_IdOrPais2_Id(paisEntity.getId());

            paisModel.setIdPaisesFronteras(fronterasModels); //seteamos la lista de enteros de fronteras al pais model
            paises.add(paisModel); //agreamos a la lista
        }
        return paises;
    }

    public List<PaisModel> getAllPaisesByContinenteId(Integer continenteId){
        List<Paises> entitys = paisesRepository.findAllByContinente_Id(continenteId);
        List<PaisModel> paises = new ArrayList<>();

        for (Paises paisEntity : entitys) {
            PaisModel paisModel = new PaisModel();
            paisModel.mapPaisModel(paisEntity);
            paises.add(paisModel);
        }
        return paises;
    }

    @Override
    public PaisModel getPaisById(Integer idPais) {
        PaisModel paisModel = new PaisModel();
        Paises entity = paisesRepository.findById(Long.valueOf(idPais)).orElse(null);
        paisModel.mapPaisModel(entity);

        List<Integer> fronterasModels = fronterasService.findAllByPais1_IdOrPais2_Id(entity.getId());

        paisModel.setIdPaisesFronteras(fronterasModels);

        return paisModel;
    }
    @Override
    public Optional<Paises> getPaisByIdEntity(Long idPais) {
        return paisesRepository.findById(idPais);
    }

    @Override
    public List<Paises> getAllPaisesEntity() {
        return paisesRepository.findAll();
    }
}
