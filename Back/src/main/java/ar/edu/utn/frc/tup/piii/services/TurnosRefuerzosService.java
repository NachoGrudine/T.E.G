package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.dtos.common.FichasDTO;
import ar.edu.utn.frc.tup.piii.dtos.common.RefuerzoDTO;
import ar.edu.utn.frc.tup.piii.dtos.common.RefuerzoPostDTO;
import ar.edu.utn.frc.tup.piii.entities.*;
import ar.edu.utn.frc.tup.piii.enums.Estado;
import ar.edu.utn.frc.tup.piii.enums.TipoFicha;
import ar.edu.utn.frc.tup.piii.enums.TipoJugador;
import ar.edu.utn.frc.tup.piii.repositories.CanjesRepository;
import ar.edu.utn.frc.tup.piii.repositories.RefuerzosRepository;
import ar.edu.utn.frc.tup.piii.services.interfaces.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.utn.frc.tup.piii.models.*;
import ar.edu.utn.frc.tup.piii.repositories.TurnosRefuerzosRepository;

import java.util.*;

@Service
public class TurnosRefuerzosService implements ITurnosRefuerzosService {
    private final TurnosRefuerzosRepository turnosRefuerzosRepository;
    private final IRefuerzosService refuerzosService;
    private final ICanjesService canjesService;
    private final IJugadoresService jugadoresService;
    private final IPaisesJugadoresService paisesJugadoresService;
    private final PaisesService paisesService;

    @Autowired
    public TurnosRefuerzosService(TurnosRefuerzosRepository turnosRefuerzosRepository,
                                  IRefuerzosService refuerzosService,
                                  ICanjesService canjesService, IJugadoresService jugadoresService, IPaisesJugadoresService paisesJugadoresService, PaisesService paisesService) {
        this.turnosRefuerzosRepository = turnosRefuerzosRepository;
        this.refuerzosService = refuerzosService;
        this.canjesService = canjesService;
        this.jugadoresService = jugadoresService;


        this.paisesJugadoresService = paisesJugadoresService;
        this.paisesService = paisesService;
    }

    public void crearTurnoRefuerzo (Rondas ronda, JugadorModel jugadorModel){
        TurnosRefuerzos turnosRefuerzos = new TurnosRefuerzos ();
        turnosRefuerzos .setEstado(Estado.SIN_JUGAR.toString());
        turnosRefuerzos .setRonda(ronda);
        turnosRefuerzos .setJugador(jugadoresService.getJugadorByIdEntity(Long.valueOf(jugadorModel.getId())));

        turnosRefuerzosRepository.save(turnosRefuerzos );
    }

    public TurnoRefuerzoModel obtenerTurnoRefuerzo(Long id) {
        if (id == null) {
            return null;
        }
        else{
            TurnosRefuerzos entity = turnosRefuerzosRepository.getReferenceById(id);
            return mapearTurnoRefuerzo(entity);
        }
    }

    public TurnoRefuerzoModel mapearTurnoRefuerzo(TurnosRefuerzos entity) {

        TurnoRefuerzoModel turno = new TurnoRefuerzoModel();
        turno.mapTurnoRefuerzoModel(entity);

        List<Refuerzos> refuerzosEntity = refuerzosService.getAllByTurnoRef(entity);
        List<RefuerzoModel> refuerzoModels = new ArrayList<>();

        for (Refuerzos ref : refuerzosEntity) {
            RefuerzoModel model = new RefuerzoModel();
            model.mapRefuerzoModel(ref);
            refuerzoModels.add(model);

        }
        turno.setRefuerzoModels(refuerzoModels);

        List<Canjes> canjesEntity = canjesService.getAllByTurnoRefuerzo(entity);
        List<CanjeModel> canjeModels = new ArrayList<>();

        for (Canjes can : canjesEntity) {
            CanjeModel model = new CanjeModel();
            model.mapCanjeModel(can);
            canjeModels.add(model);
        }
        turno.setCanjeModels(canjeModels);

        return turno;
    }

   public List<TurnoRefuerzoModel> buscarTurnosByRonda(Rondas ronda){
       List<TurnosRefuerzos> turnosEntity = turnosRefuerzosRepository.getAllByRondaOrderByIdAsc(ronda);
       List<TurnoRefuerzoModel> turnosRefuerzoModels = new ArrayList<>();

       for (TurnosRefuerzos entity : turnosEntity) {
           turnosRefuerzoModels.add(mapearTurnoRefuerzo(entity));
       }
       return turnosRefuerzoModels;
   }

    public FichasDTO obtenerFichas(Long idTurno) {
         TurnosRefuerzos entity = turnosRefuerzosRepository.getReferenceById(idTurno);
         FichasDTO fichasDTO = new FichasDTO();
         int contadorTurno = 0;

         if(entity.getRonda().getNumero() == 1){
             List<TurnoRefuerzoModel> turnosRefuerzoModels = buscarTurnosByRonda(entity.getRonda());
             for(TurnoRefuerzoModel turnoRefuerzoModel : turnosRefuerzoModels){
                 if(Objects.equals(entity.getJugador().getId(), turnoRefuerzoModel.getIdJugador())){
                     contadorTurno++;
                     if(Objects.equals(entity.getId(), turnoRefuerzoModel.getId())){
                         break;
                     }
                 }
             }
             if(contadorTurno == 1){

                 fichasDTO.setFichasPais(3);
                 return fichasDTO;
             }
             else{
                 fichasDTO.setFichasPais(5);
                 return fichasDTO;
             }


         }

         JugadorModel jugador =jugadoresService.buscarJugador(Long.valueOf(entity.getJugador().getId()) );

         fichasDTO.setFichasPais(jugador.getPaises().size() / 2);
        return fichasDTO;
    }

    @Override
    public void guardarRefuerzos(List<RefuerzoPostDTO> refuerzos) {
        Optional<TurnosRefuerzos> turno = turnosRefuerzosRepository.findById(Long.valueOf(refuerzos.get(0).getIdTurnoRef()));


        for(RefuerzoPostDTO refuerzoPostDTO : refuerzos){

            Refuerzos refuerzoEntity = new Refuerzos();
            PaisesJugadores pais = paisesJugadoresService.buscarFromJugadorId(turno.get().getJugador(), refuerzoPostDTO.getIdPais());
            refuerzoEntity.setPaisJugador(pais);
            refuerzoEntity.setTurnoRef(turno.get());
            refuerzoEntity.setCantidad(refuerzoPostDTO.getCantidad());
            refuerzoEntity.setTipoFichas((refuerzoPostDTO.getTipoFicha()) );

            refuerzosService.saveOne(refuerzoEntity);
            pais.setFichas(pais.getFichas() + refuerzoPostDTO.getCantidad());
            paisesJugadoresService.save(pais);

        }
    }

    @Override
    public void terminarTurnoRefuerzo(Long id) {
        TurnosRefuerzos entity = turnosRefuerzosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Turno no encontrado con id " + id));

        entity.setEstado(Estado.FINALIZADO.toString());

        turnosRefuerzosRepository.save(entity);
    }
}
