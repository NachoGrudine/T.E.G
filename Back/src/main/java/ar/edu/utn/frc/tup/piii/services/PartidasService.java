package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.dtos.common.*;
import ar.edu.utn.frc.tup.piii.entities.*;
import ar.edu.utn.frc.tup.piii.enums.Estado;
import ar.edu.utn.frc.tup.piii.enums.TipoJugador;
import ar.edu.utn.frc.tup.piii.enums.TipoPartida;
import ar.edu.utn.frc.tup.piii.models.*;
import ar.edu.utn.frc.tup.piii.repositories.*;
import ar.edu.utn.frc.tup.piii.services.interfaces.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.util.*;


@Service
public class PartidasService implements IPartidasService {
    //Repo
    private final PartidasRepository partidasRepository;

    //Services
    private final IUsuariosService usarioServeice;
    private final IObjetivosService objetivosService;
    private final IJugadoresService jugadoresService;
    private final IPaisesService paisesServices;
    private final IPaisesJugadoresService paisesJugadoresService;
    private final IColoresService coloresService;
    private final RondasService rondasService;
    private final TurnosRefuerzosService turnosRefuerzosService;
    private final TurnosAtaquesService turnosAtaquesService;
    private final BotService botService;


    @Autowired
    public PartidasService(
            PartidasRepository partidasRepository,
            IUsuariosService suarioServeice,
            IObjetivosService objetivosService,
            IJugadoresService jugadoresService,
            IPaisesService paisesService,
            IPaisesJugadoresService paisesJugadoresService,
            IColoresService coloresService, RondasService rondasService, TurnosRefuerzosService turnosRefuerzosService, TurnosAtaquesService turnosAtaquesService, BotService botService) {
        this.partidasRepository = partidasRepository;
        this.coloresService = coloresService;
        this.usarioServeice = suarioServeice;
        this.objetivosService = objetivosService;
        this.jugadoresService = jugadoresService;
        this.paisesServices = paisesService;
        this.paisesJugadoresService = paisesJugadoresService;
        this.rondasService = rondasService;
        this.turnosRefuerzosService = turnosRefuerzosService;
        this.turnosAtaquesService = turnosAtaquesService;
        this.botService = botService;
    }

    public List<PartidaModel> getPartidas(Long idUsuario) {
        Usuarios usuario = usarioServeice.getUsuarioById(idUsuario);
        List<Partidas> partidasEntity = partidasRepository.getAllByUsuario(usuario);

        List<PartidaModel> partidasModel = new ArrayList<>();
        for (Partidas partidas : partidasEntity) {
            PartidaModel partidaMapeada = mapearPartida(partidas);
            List<JugadorModel> jugadores = partidaMapeada.getJugadores();
            //le meto null asi no se puede ver el objetivo de los demasn jugadores
            for(JugadorModel jugador : jugadores) {
                jugador.setObjetivo(null);
            }
            partidaMapeada.setJugadores(jugadores);

            partidasModel.add(partidaMapeada);
        }

        return partidasModel;
    }

    public PartidaModel crearPartida(Long idUsuario) {

        Partidas partidaEntity = new Partidas();

        Optional<Usuarios> user = Optional.ofNullable(usarioServeice.getUsuarioById(idUsuario));
        if(user.isEmpty()) {
            throw new EntityNotFoundException();
        }
        partidaEntity.setUsuario(user.get());
        partidaEntity.setCantidadParaGanar(32);
        partidaEntity.setEstado(Estado.EN_PREPARACION.toString());
        partidaEntity.setTipo(TipoPartida.PRIVADA.toString());


        Partidas partidaGuardada = partidasRepository.save(partidaEntity);

        return mapearPartida(partidaGuardada);

    }

    public Partidas buscarPartidaEntity(Long idPartida) {
        return partidasRepository.findById(idPartida).orElseThrow(() -> new EntityNotFoundException("Partida no encontrada"));
    }

    public PartidaModel buscarPartida(Long idPartida) {
        Partidas partida = partidasRepository.getReferenceById(idPartida);

        PartidaModel partidaMapeada = mapearPartida(partida);
        List<JugadorModel> jugadores = partidaMapeada.getJugadores();
        for(JugadorModel jugador : jugadores) {
            jugador.setObjetivo(null);
        }
        partidaMapeada.setJugadores(jugadores);

        return partidaMapeada;
    }

    @Override
    public List<PartidaModel> obtenerPartidasPorTipoYEstado() {
        List<Partidas> entities = partidasRepository.findAllByTipoAndEstado(TipoPartida.PUBLICA.toString(), Estado.EN_PREPARACION.toString());

        List<PartidaModel> partidasModel = new ArrayList<>();

        for (Partidas partidas : entities) {
            PartidaModel partidaMapeada = mapearPartida(partidas);
            List<JugadorModel> jugadores = partidaMapeada.getJugadores();
            for(JugadorModel jugador : jugadores) {
                jugador.setObjetivo(null);
            }
            partidaMapeada.setJugadores(jugadores);

            partidasModel.add(partidaMapeada);
        }
        return partidasModel;
    }

    public void empezarPartida(Long idPartida) {


        Partidas partida = partidasRepository.findById(idPartida)
                .orElseThrow(() -> new EntityNotFoundException("Partida no encontrada"));


        List<Jugadores> jugadoresEntity = jugadoresService.getAllByPartida(partida);

        List<Objetivos> objetivosEntity = objetivosService.getAllObjetivosEntity();

        List<Objetivos> objetivosFiltrados = new ArrayList<>();

        for (Objetivos objetivo : objetivosEntity) {
            if (objetivo.getColor() == null) {

                objetivosFiltrados.add(objetivo);
            } else {

                boolean encontrado = false;
                for (Jugadores jugador : jugadoresEntity) {
                    if (jugador.getColor() != null && jugador.getColor().equals(objetivo.getColor())) {
                        encontrado = true;
                        break;
                    }
                }
                if (encontrado) {
                    objetivosFiltrados.add(objetivo);
                }
            }
        }

        Collections.shuffle(objetivosFiltrados);
        int i = 0;
        for (Jugadores jugador : jugadoresEntity) {
            if (i < objetivosFiltrados.size()) {
                if(jugador.getColor().equals(objetivosFiltrados.get(i).getColor())) {
                    i++;
                }
                jugador.setObjetivo(objetivosFiltrados.get(i));
                i++;
            } else {
                throw new RuntimeException("No hay suficientes objetivos para todos los jugadores.");
            }
        }


        List<Paises> paisesEntity = paisesServices.getAllPaisesEntity();
        paisesJugadoresService.asignarPaisesAJugadores(paisesEntity, jugadoresEntity, partida);


        jugadoresService.saveAll(jugadoresEntity);
        partida.setEstado(Estado.EN_CURSO.toString());
        RondaModel rondaModel = rondasService.crearRonda(mapearPartida(partida), partida);
        partidasRepository.save(partida);


        TurnoRefuerzoModel turno = rondaModel.getTurnosRefuerzo().get(0);

        if (turno != null) {
            JugadorModel jugador = jugadoresService.buscarJugador(Long.valueOf(turno.getIdJugador()));
            if(jugador.getTipoJugador() != TipoJugador.HUMANO ){
                botService.jugarRefuerzo(idPartida, Long.valueOf(rondaModel.getId()), turno, jugador);
                terminarTurnoRefuerzo(idPartida, Long.valueOf(rondaModel.getId()), Long.valueOf(turno.getId()));
            }
        }
    }

    private PartidaModel mapearPartida(Partidas partida){
        PartidaModel partidaModel = new PartidaModel();
        partidaModel.mapPartidaModel(partida);
        List<JugadorModel> jugadoresModel = jugadoresService.mapearJugadores(jugadoresService.getAllByPartida(partida));
        partidaModel.setJugadores(jugadoresModel);
        return partidaModel;
    }

    public PartidaModel agregarJugador(Long idPartida, JugadorDTO jugadorDTO) {
        Partidas partida = partidasRepository.findById(idPartida)
                .orElseThrow(() -> new EntityNotFoundException("Partida no encontrada"));
        jugadoresService.agregarJugador(partida, jugadorDTO);
        return mapearPartida(partida);
    }

    @Override
    public List<ColorModel> buscarColores(Long idPartida) {
        List<ColorModel> colores = coloresService.getAllColores();
        Partidas partida = partidasRepository.findById(idPartida).orElseThrow();
        List<JugadorModel> jugadoresModel = jugadoresService.mapearJugadores(jugadoresService.getAllByPartida(partida));


        return colores.stream()
                .filter(color -> jugadoresModel.stream()
                        .noneMatch(jugador -> jugador.getColor().getId().equals(color.getId())))
                .toList();


    }


    public RondaModel buscarUltimaRonda(Long idPartida, Long idJugador ) {
        Partidas partida = partidasRepository.findById(idPartida)
                .orElseThrow(() -> new EntityNotFoundException("Partida no encontrada"));

        RondaModel ronda  = rondasService.getUltimaRondaPartidaModel(partidasRepository.findById(idPartida).orElseThrow(),mapearPartida(partida));

        for (JugadorModel jugador : ronda.getJugadores()) {
            if (jugador.getId() != idJugador.intValue()) {
                jugador.setObjetivo(null);
            }
            else {
                jugador.setObjetivo(objetivosService.getByIdObjetivo(jugador.getObjetivo().getId()));
            }
        }
        return ronda;
    }


    public TurnoRefuerzoModel obtenerTurnoRefuerzo( Long id) {
        return turnosRefuerzosService.obtenerTurnoRefuerzo(id);

    }


    public FichasDTO obtenerFichas( Long id) {
        return turnosRefuerzosService.obtenerFichas(id);

    }

    @Override
    public void guardarRefuerzos(List<RefuerzoPostDTO> refuerzos) {
        turnosRefuerzosService.guardarRefuerzos(refuerzos);
    }

    @Override
    public void terminarTurnoRefuerzo(Long idPartida, Long idRonda, Long idTurno) {
        turnosRefuerzosService.terminarTurnoRefuerzo(idTurno);
        PartidaModel partidaModel = buscarPartida(idPartida);
        TurnoRefuerzoModel turno = rondasService.getNextTurnRefuerzo(idRonda, partidaModel,  idTurno);

        if (turno != null) {
            JugadorModel jugador = jugadoresService.buscarJugador(Long.valueOf(turno.getIdJugador()));
            if(jugador.getTipoJugador() != TipoJugador.HUMANO ){
                botService.jugarRefuerzo(idPartida, idRonda, turno, jugador);
                terminarTurnoRefuerzo(idPartida, idRonda, Long.valueOf(turno.getId()));
            }
        }
        else{
            RondaModel rondaModel = rondasService.getUltimaRondaPartidaModel(buscarPartidaEntity(idPartida), partidaModel);
            TurnoAtaqueModel turnoAtk = rondaModel.getTurnosAtaque().get(0);
            if (turnoAtk != null) {
                JugadorModel jugador = jugadoresService.buscarJugador(Long.valueOf(turnoAtk.getIdJugador()));
                if(jugador.getTipoJugador() != TipoJugador.HUMANO ){
                    botService.jugarAtaque(idPartida, Long.valueOf(rondaModel.getId()), turnoAtk, jugador);
                    terminarTurnoAtaque(idPartida, Long.valueOf(rondaModel.getId()), Long.valueOf(turnoAtk.getId()));
                }
            }


        }
    }

    @Override
    public TurnoAtaqueModel obtenerTurnoAtaque(Long id) {
        return turnosAtaquesService.obtenerTurnoAtaque(id);
    }

    @Override
    public List<PaisJugadorModel> getPaisesFromPartida(Long id){
        Partidas partida = buscarPartidaEntity(id);
        return paisesJugadoresService.getPaisesFromPartidaId(partida);
    }

    @Override
    public void configurarPartida(Long id, Integer objetivo, String tipo) {
        Partidas partida = partidasRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Partida no encontrada"));
        partida.setTipo(tipo);
        partida.setCantidadParaGanar(objetivo);

        partidasRepository.save(partida);
    }

    @Override
    public AccionModel hacerAtaque(AccionCombatePostDTO accionCombatePostDTO) {
        return turnosAtaquesService.hacerAtaque(accionCombatePostDTO);
    }

    @Override
    public AccionModel hacerReagrupacion(AccionReagrupacionPostDTO accionReagrupacionPostDTO) {
        return turnosAtaquesService.hacerReagrupacion(accionReagrupacionPostDTO);
    }

    @Override
    public void terminarTurnoAtaque(Long idPartida, Long idRonda, Long idTurno) {
        turnosAtaquesService.terminarTurnoAtaque(idTurno);
        PartidaModel partidaModel = buscarPartida(idPartida);
        TurnoAtaqueModel turno = rondasService.getNextTurnAtaque(idRonda, partidaModel,  idTurno);

        if (turno != null) {
            JugadorModel jugador = jugadoresService.buscarJugador(Long.valueOf(turno.getIdJugador()));
            if(jugador.getTipoJugador() != TipoJugador.HUMANO ){
                botService.jugarAtaque(idPartida, idRonda, turno, jugador);
                terminarTurnoAtaque(idPartida, idRonda, Long.valueOf(turno.getId()));
            }
        }
        else{
             RondaModel rondaModel =  rondasService.crearRonda(partidaModel, buscarPartidaEntity(idPartida));

             TurnoRefuerzoModel turnoRef = rondaModel.getTurnosRefuerzo().get(0);

            if (turnoRef != null) {
                JugadorModel jugador = jugadoresService.buscarJugador(Long.valueOf(turnoRef.getIdJugador()));
                if(jugador.getTipoJugador() != TipoJugador.HUMANO ){
                    botService.jugarRefuerzo(idPartida, Long.valueOf(rondaModel.getId()), turnoRef, jugador);
                    terminarTurnoRefuerzo(idPartida, Long.valueOf(rondaModel.getId()), Long.valueOf(turnoRef.getId()));
                }
            }


        }
    }

    @Override
    public GanadorDTO chekearGanador(Integer idJugador){
        Jugadores jugador = jugadoresService.getJugadorByIdEntity(Long.valueOf(idJugador));
        List<PaisJugadorModel> paisesJugador = paisesJugadoresService.getPaisesFromJugadorId(jugador);
        GanadorDTO ganadorDTO = new GanadorDTO();
        if(paisesJugador.size() >= jugador.getPartida().getCantidadParaGanar()){
            ganadorDTO.setIdJugador(jugador.getId());
        }
        else{
            ganadorDTO.setIdJugador(0);
        }

        return ganadorDTO;
    }


}
