package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.dtos.common.AccionCombatePostDTO;
import ar.edu.utn.frc.tup.piii.dtos.common.AccionReagrupacionPostDTO;
import ar.edu.utn.frc.tup.piii.entities.Acciones;
import ar.edu.utn.frc.tup.piii.entities.Rondas;
import ar.edu.utn.frc.tup.piii.entities.TurnosAtaques;
import ar.edu.utn.frc.tup.piii.models.AccionModel;
import ar.edu.utn.frc.tup.piii.repositories.AccionesRepository;
import ar.edu.utn.frc.tup.piii.repositories.CombatesRepository;
import ar.edu.utn.frc.tup.piii.repositories.PartidasRepository;
import ar.edu.utn.frc.tup.piii.services.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccionesService {
    private final AccionesRepository accionesRepository;
    private final CombatesService combatesService;
    private final ReagrupacionService reagrupacionService;

    public AccionesService(AccionesRepository accionesRepository, CombatesService combatesService, ReagrupacionService reagrupacionService)
    {
        this.accionesRepository = accionesRepository;
        this.combatesService = combatesService;
        this.reagrupacionService = reagrupacionService;
    }

    public List<AccionModel> buscarAccionesByTurno(TurnosAtaques turno){
        List<Acciones> accionesEntity = accionesRepository.getAllByTurnoAtaque(turno);
        List<AccionModel> accionesModels = new ArrayList<>();
        for(Acciones accion : accionesEntity){
            AccionModel accionModel = new AccionModel();
            accionModel.mapAccionModel(accion);
            accionesModels.add(accionModel);
        }
        return accionesModels;
    }

    public AccionModel hacerAtaque(AccionCombatePostDTO accionCombatePostDTO, TurnosAtaques turno) {
        Acciones accion = new Acciones();
        accion.setTurnoAtaque(turno);
        accion.setCombate(combatesService.hacerCombate(accionCombatePostDTO.getIdPaisAtk(),
                accionCombatePostDTO.getIdPaisDef(),
                turno.getRonda().getPartida().getId()));
        AccionModel accionModel = new AccionModel();
        accionModel.mapAccionModel(accionesRepository.save(accion));
        return accionModel;
    }

    public AccionModel hacerReagrupacion(AccionReagrupacionPostDTO accionReagrupacionPostDTO, TurnosAtaques turno) {
        Acciones accion = new Acciones();
        accion.setTurnoAtaque(turno);
        accion.setReagrupacion(reagrupacionService.hacerReagrupacion(accionReagrupacionPostDTO.getIdPaisOrigen(),
                accionReagrupacionPostDTO.getIdPaisDestino(),
                accionReagrupacionPostDTO.getCantidad(),
                turno.getRonda().getPartida().getId()));
        AccionModel accionModel = new AccionModel();
        accionModel.mapAccionModel(accionesRepository.save(accion));
        return accionModel;
    }
}
