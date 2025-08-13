package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.entities.Partidas;
import ar.edu.utn.frc.tup.piii.entities.Rondas;
import ar.edu.utn.frc.tup.piii.enums.Estado;
import ar.edu.utn.frc.tup.piii.enums.EstadoJugador;
import ar.edu.utn.frc.tup.piii.models.*;
import ar.edu.utn.frc.tup.piii.repositories.*;
import ar.edu.utn.frc.tup.piii.services.interfaces.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RondasService implements IRondasService{


    private final RondasRepository rondaRepository;

    private final TurnosAtaquesService turnosAtaquesService;
    private final TurnosRefuerzosService turnosRefuerzosService;

    @Autowired
    public RondasService(RondasRepository rondaRepository, TurnosAtaquesService turnosAtaquesService, TurnosRefuerzosService turnosRefuerzosService) {
        this.rondaRepository = rondaRepository;

        this.turnosAtaquesService = turnosAtaquesService;
        this.turnosRefuerzosService = turnosRefuerzosService;
    }

    public RondaModel crearRonda(PartidaModel partida, Partidas partidaEntity) {

        Rondas ronda = new Rondas();
        ronda.setPartida(partidaEntity);
        Rondas ultimaRonda = getUltimaRondaPartida(partidaEntity);
        if(ultimaRonda == null){
            ronda.setNumero(1);
        }
        else{
            ronda.setNumero(ultimaRonda.getNumero()+1);
        }
        ronda.setEstado(Estado.EN_CURSO.toString());
        Rondas rondaEntity = rondaRepository.save(ronda);

        List<JugadorModel> jugadoresModel = partida.getJugadores();
        int indexOrdenRonda = ronda.getNumero() - 1;
        int cantidadJugadores = jugadoresModel.size();

        while(indexOrdenRonda >= cantidadJugadores){
            indexOrdenRonda = indexOrdenRonda - cantidadJugadores;
        }

        for(int i = 0; i < cantidadJugadores; i++){
            if(jugadoresModel.get(indexOrdenRonda).getEstadoJugador() != EstadoJugador.MUERTO){
                turnosRefuerzosService.crearTurnoRefuerzo(ronda, jugadoresModel.get(indexOrdenRonda));
                turnosAtaquesService.crearTurnoAtaque(ronda, jugadoresModel.get(indexOrdenRonda));
            }

             indexOrdenRonda++;
             if(indexOrdenRonda >= cantidadJugadores){
                 indexOrdenRonda = 0;
             }
        }

        //Lo hace otra vez porque en la ronda 1 hay 2 turnos de refuerzo por jugador y 1 solo ataque
        if(ronda.getNumero()==1){
            for(int i = 0; i < cantidadJugadores; i++){
                if(jugadoresModel.get(indexOrdenRonda).getEstadoJugador() != EstadoJugador.MUERTO){
                    turnosRefuerzosService.crearTurnoRefuerzo(ronda, jugadoresModel.get(indexOrdenRonda));

                }
                indexOrdenRonda++;
                if(indexOrdenRonda >= cantidadJugadores){
                    indexOrdenRonda = 0;
                }
            }
        }

    return mapearRonda(rondaEntity, partida);

    }

    @Override
    public TurnoAtaqueModel getNextTurnAtaque(Long idRonda, PartidaModel partidaModel, Long idTurno) {
        Optional<Rondas> entity = rondaRepository.findById(idRonda);
        if(entity.isPresent()){
            RondaModel rondaModel = mapearRonda(entity.get(), partidaModel);
            boolean siguiente = false;
            for(TurnoAtaqueModel turno : rondaModel.getTurnosAtaque() ){
                if (siguiente){
                    return turno;
                }
                if(idTurno.intValue() == turno.getId()){
                    siguiente = true;
                }

            }
        }
        return null;
    }


    public Rondas getUltimaRondaPartida(Partidas partida) {
        return rondaRepository.findTopByPartidaOrderByIdDesc(partida);
    }

    public RondaModel mapearRonda(Rondas ronda, PartidaModel partida){
        RondaModel rondaModel = new RondaModel();
        rondaModel.mapRondaModel(ronda);



        List<JugadorModel> jugadoresModel = partida.getJugadores();
        int indexOrdenRonda = ronda.getNumero() - 1;
        int cantidadJugadores = jugadoresModel.size();
        //creo que sacando el resto entre indexRonda/cantidadJugadores hace lo mismo
        while(indexOrdenRonda >= cantidadJugadores){
            indexOrdenRonda = indexOrdenRonda - cantidadJugadores;
        }

        //jugadores
        List<JugadorModel> jugadoresRonda = new ArrayList<>();
        for(int i = 0; i < cantidadJugadores; i++){
            jugadoresRonda.add(jugadoresModel.get(indexOrdenRonda));
            indexOrdenRonda++;
            if(indexOrdenRonda >= cantidadJugadores){
                indexOrdenRonda = 0;
            }
        }
        rondaModel.setJugadores(jugadoresRonda);

        //TurnosRefuerzo, si es la primera ronda, simplemente cuando se cree al ronda va a meter el doble de turnosRef
        List<TurnoRefuerzoModel> turnosRefuerzo = turnosRefuerzosService.buscarTurnosByRonda(ronda);
        rondaModel.setTurnosRefuerzo(turnosRefuerzo);

        //Turnos ataques
        List<TurnoAtaqueModel> turnosAtaque = turnosAtaquesService.buscarTurnosByRonda(ronda);
        rondaModel.setTurnosAtaque(turnosAtaque);

        return rondaModel;
    }

    public RondaModel getUltimaRondaPartidaModel(Partidas partida, PartidaModel partidaModel) {
        return mapearRonda(rondaRepository.findTopByPartidaOrderByIdDesc(partida), partidaModel);


    }


    public TurnoRefuerzoModel getNextTurnRefuerzo (Long idRonda, PartidaModel partidaModel, Long idTurno) {
        Optional<Rondas> entity = rondaRepository.findById(idRonda);
        if(entity.isPresent()){
            RondaModel rondaModel = mapearRonda(entity.get(), partidaModel);
            boolean siguiente = false;
            for(TurnoRefuerzoModel turno : rondaModel.getTurnosRefuerzo() ){
                if (siguiente){
                    return turno;
                }
                if(idTurno.intValue() == turno.getId()){
                    siguiente = true;
                }

            }
        }
        return null;

    }

}
