package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.entities.Fronteras;
import ar.edu.utn.frc.tup.piii.models.FronteraModel;
import ar.edu.utn.frc.tup.piii.models.PaisJugadorModel;
import ar.edu.utn.frc.tup.piii.repositories.FronterasRepository;
import ar.edu.utn.frc.tup.piii.services.interfaces.IFronterasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FronterasService implements IFronterasService {

    private final FronterasRepository fronterasRepository;

    @Autowired
    public FronterasService(FronterasRepository fronterasRepository) {
        this.fronterasRepository = fronterasRepository;
    }

    public List<Integer> findAllByPais1_IdOrPais2_Id(Integer pais)
    {
      List<Fronteras> listaEntity = fronterasRepository.findAllByPais1_IdOrPais2_Id(pais, pais);
      List<Integer> listaModel = new ArrayList<>();
      for(Fronteras entity : listaEntity)
      {
          FronteraModel fronteraModel = new FronteraModel();
          fronteraModel.mapFronteraModel(entity);
          if(entity.getPais1().getId().equals(pais))
          {
              listaModel.add(entity.getPais2().getId());
          }
          else {
              listaModel.add(entity.getPais1().getId());
          }
      }
      return listaModel;
    }

    @Override
    public Map<Integer, List<Integer>> crearGrafo() {
        Map<Integer, List<Integer>> grafo = new HashMap<>();
        List<Fronteras> listaFronteras = fronterasRepository.findAll();

        for (Fronteras frontera : listaFronteras) {

            Integer pais1 = frontera.getPais1().getId();
            Integer pais2 = frontera.getPais2().getId();

            if(!grafo.containsKey(pais1)){
                grafo.put(pais1, new ArrayList<>());
            }
            grafo.get(pais1).add(pais2);

            if(!grafo.containsKey(pais2)){
                grafo.put(pais2, new ArrayList<>());
            }
            grafo.get(pais2).add(pais1);
        }
        return grafo;
    }
}
