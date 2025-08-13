package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.entities.Tarjetas;
import ar.edu.utn.frc.tup.piii.models.TarjetaModel;
import ar.edu.utn.frc.tup.piii.repositories.TarjetasRepository;
import ar.edu.utn.frc.tup.piii.services.interfaces.ITarjetasService;
import ar.edu.utn.frc.tup.piii.services.interfaces.ITurnosRefuerzosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TarjetasService implements ITarjetasService {

    private final TarjetasRepository tarjetasRepository;

    @Autowired
    public TarjetasService(TarjetasRepository tarjetasRepository) {
        this.tarjetasRepository = tarjetasRepository;
    }


    public List<TarjetaModel> getAllTarjetas()
    {
        List<Tarjetas> tarjetasEntity = tarjetasRepository.findAll();

        List<TarjetaModel> tarjetasModel = new ArrayList<TarjetaModel>();
        for(Tarjetas tarjEntity : tarjetasEntity)
        {
            TarjetaModel model = new TarjetaModel();
            model.mapTarjetaModel(tarjEntity);
            tarjetasModel.add(model);

        }
        return tarjetasModel;
    }


    public TarjetaModel getTarjeta(Long id){
        Tarjetas tarjEntity = tarjetasRepository.findById(id).get();
        TarjetaModel model = new TarjetaModel();
         model.mapTarjetaModel(tarjEntity);
        return model;
    }

    public Tarjetas getTarjetaEntity(){
        List<Tarjetas> tarjetasEntity = tarjetasRepository.findAll();
        Collections.shuffle(tarjetasEntity);
        return  tarjetasEntity.get(0);
    }
}
