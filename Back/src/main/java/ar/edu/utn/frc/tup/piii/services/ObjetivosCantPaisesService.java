package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.entities.ObjetivosCantidadPaises;
import ar.edu.utn.frc.tup.piii.entities.Paises;
import ar.edu.utn.frc.tup.piii.models.ObjetivoCantidadPaisesModel;
import ar.edu.utn.frc.tup.piii.models.PaisModel;
import ar.edu.utn.frc.tup.piii.repositories.ObjetivosCantidadPaisesRepository;
import ar.edu.utn.frc.tup.piii.services.interfaces.IObjetivosCantPaisesService;
import ar.edu.utn.frc.tup.piii.services.interfaces.IPaisesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ObjetivosCantPaisesService implements IObjetivosCantPaisesService {

    private final ObjetivosCantidadPaisesRepository objetivosCantidadPaisesRepository;
    private final IPaisesService paisesService;

    @Autowired
    public ObjetivosCantPaisesService(ObjetivosCantidadPaisesRepository objetivosCantidadPaisesRepository, IPaisesService paisesService) {
        this.objetivosCantidadPaisesRepository = objetivosCantidadPaisesRepository;
        this.paisesService = paisesService;
    }

    public List<ObjetivoCantidadPaisesModel> findAllByObjetivo_Id(Integer objetivoId)
    {
        List<ObjetivosCantidadPaises> listaEntity = objetivosCantidadPaisesRepository.findAllByObjetivo_Id(objetivoId);
        List<ObjetivoCantidadPaisesModel> listaModel = new ArrayList<ObjetivoCantidadPaisesModel>();

        for(ObjetivosCantidadPaises obj : listaEntity)
        {
            ObjetivoCantidadPaisesModel model = new ObjetivoCantidadPaisesModel();
            model.mapObjetivoCantidadPaisesModel(obj);
            listaModel.add(model);
        }
        return listaModel;
    }

    public List<Integer> obtenerIdsPaisesDeObjetivo(Integer objetivoId) {
        List<ObjetivoCantidadPaisesModel> objetivosPorContinente = findAllByObjetivo_Id(objetivoId);
        List<Integer> resultado = new ArrayList<>();

        for (ObjetivoCantidadPaisesModel objetivo : objetivosPorContinente) {
            Integer idContinente = objetivo.getContinenteModel().getId();

            List<PaisModel> paisesDelContinente = paisesService.getAllPaisesByContinenteId(idContinente);

            for (PaisModel pais : paisesDelContinente) {
                resultado.add(pais.getId());
            }
        }
        return resultado;
    }
}
