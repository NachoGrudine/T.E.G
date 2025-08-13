package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.dtos.common.AccionCombatePostDTO;
import ar.edu.utn.frc.tup.piii.dtos.common.AccionReagrupacionPostDTO;
import ar.edu.utn.frc.tup.piii.entities.*;
import ar.edu.utn.frc.tup.piii.enums.Estado;
import ar.edu.utn.frc.tup.piii.models.*;
import ar.edu.utn.frc.tup.piii.repositories.CanjesRepository;
import ar.edu.utn.frc.tup.piii.repositories.TurnosAtaquesRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TurnosAtaquesService {

    private final TurnosAtaquesRepository turnosAtaquesRepository;
    private final JugadoresService jugadoresService;
    private final AccionesService accionesService;
    private final TarjetasJugadoresService tarjetasJugadoresService;
    private final TarjetasService tarjetasService;

    @Autowired
    public TurnosAtaquesService(TurnosAtaquesRepository turnosAtaquesRepository,
                                JugadoresService jugadoresService,
                                AccionesService accionesService, TarjetasJugadoresService tarjetasJugadoresService, TarjetasService tarjetasService
    )
    {
        this.turnosAtaquesRepository = turnosAtaquesRepository;
        this.jugadoresService = jugadoresService;
        this.accionesService = accionesService;
        this.tarjetasJugadoresService = tarjetasJugadoresService;
        this.tarjetasService = tarjetasService;
    }

    public void crearTurnoAtaque(Rondas ronda, JugadorModel jugadorModel){
        TurnosAtaques turnosAtaques = new TurnosAtaques();
        turnosAtaques.setEstado(Estado.SIN_JUGAR.toString());
        turnosAtaques.setRonda(ronda);
        turnosAtaques.setJugador(jugadoresService.getJugadorByIdEntity(Long.valueOf(jugadorModel.getId())));

        turnosAtaquesRepository.save(turnosAtaques);
    }


    public TurnoAtaqueModel mapearTurnoAtaque(TurnosAtaques entity) {

        TurnoAtaqueModel turno = new TurnoAtaqueModel();
        turno.mapTurnoAtaqueModel(entity);

        List<AccionModel> acciones = accionesService.buscarAccionesByTurno(entity);
        turno.setAcciones(acciones);
        return turno;


    }

    public List<TurnoAtaqueModel> buscarTurnosByRonda(Rondas ronda){
        List<TurnosAtaques> turnosEntity = turnosAtaquesRepository.getAllByRondaOrderByIdAsc(ronda);
        List<TurnoAtaqueModel> turnosAtaqueModels = new ArrayList<>();

        for (TurnosAtaques entity : turnosEntity) {
            turnosAtaqueModels.add(mapearTurnoAtaque(entity));
        }
        return turnosAtaqueModels;
    }


    public TurnoAtaqueModel obtenerTurnoAtaque(Long id) {
        if (id == null) {
            return null;
        }
        else{
            TurnosAtaques entity = turnosAtaquesRepository.getReferenceById(id);
            return mapearTurnoAtaque(entity);
        }

    }

    public AccionModel hacerAtaque(AccionCombatePostDTO accionCombatePostDTO) {
        return accionesService.hacerAtaque(accionCombatePostDTO,turnosAtaquesRepository.getReferenceById(Long.valueOf(accionCombatePostDTO.getIdTurno()) ));
    }

    public AccionModel hacerReagrupacion(AccionReagrupacionPostDTO accionReagrupacionPostDTO) {
        return accionesService.hacerReagrupacion(accionReagrupacionPostDTO,turnosAtaquesRepository.getReferenceById(Long.valueOf(accionReagrupacionPostDTO.getIdTurno()) ));

    }

    public void terminarTurnoAtaque(Long idTurno) {
        TurnosAtaques entity = turnosAtaquesRepository.findById(idTurno)
                .orElseThrow(() -> new EntityNotFoundException("Turno no encontrado con id " + idTurno));

        entity.setEstado(Estado.FINALIZADO.toString());

        turnosAtaquesRepository.save(entity);


        TurnoAtaqueModel turnoAtaque = mapearTurnoAtaque(entity);
        for (AccionModel accion : turnoAtaque.getAcciones()) {
            if (accion.getCombate() != null) {
                if (accion.getCombate().getFichasDef() == 0) {

                    tarjetasJugadoresService.darTarjeta(entity.getJugador(), tarjetasService.getTarjetaEntity());
                    break;
                }
            }
        }
    }
}
