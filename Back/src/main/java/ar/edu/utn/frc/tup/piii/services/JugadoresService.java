package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.dtos.common.JugadorDTO;
import ar.edu.utn.frc.tup.piii.entities.*;
import ar.edu.utn.frc.tup.piii.enums.EstadoJugador;
import ar.edu.utn.frc.tup.piii.models.JugadorModel;
import ar.edu.utn.frc.tup.piii.models.PaisJugadorModel;
import ar.edu.utn.frc.tup.piii.models.TarjetaJugadorModel;
import ar.edu.utn.frc.tup.piii.models.TarjetaModel;
import ar.edu.utn.frc.tup.piii.repositories.*;
import ar.edu.utn.frc.tup.piii.services.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JugadoresService implements IJugadoresService {
    //Repo
    private final JugadoresRepository jugadoresRepository;
    //services
    private final IUsuariosService usuariosService;
    private final IColoresService coloresService;
    private final IPaisesJugadoresService paisesJugadoresService;
    private final TarjetasJugadoresService tarjetasJugadoresService;

    @Autowired
    public JugadoresService(JugadoresRepository jugadoresRepository,
                            IUsuariosService usuariosService,
                            IColoresService coloresService,
                            IPaisesJugadoresService paisesJugadoresService,
                             TarjetasJugadoresService tarjetasJugadoresService) {
        this.jugadoresRepository = jugadoresRepository;
        this.usuariosService = usuariosService;
        this.coloresService = coloresService;
        this.paisesJugadoresService = paisesJugadoresService;
        this.tarjetasJugadoresService = tarjetasJugadoresService;

    }

    public List<Jugadores> getAllByPartida(Partidas partida) {
        return jugadoresRepository.getAllByPartidaOrderByIdAsc(partida);
    }

    public void saveAll(List<Jugadores> jugadoresEntity) {
        jugadoresRepository.saveAll(jugadoresEntity);
    }

    //metodo que retorna un jugador entity por id para luego usarlo en un post
    public Jugadores getJugadorByIdEntity(Long aLong) {
        Jugadores entity = jugadoresRepository.getReferenceById(Long.valueOf(aLong));
        return entity;
    }

    public void agregarJugador(Partidas partida, JugadorDTO jugadorDTO) {
        Jugadores jugadorEntity;
        if (jugadorDTO.getIdUsuario() != null) {
            Usuarios usuario = usuariosService.getUsuarioById(Long.valueOf(jugadorDTO.getIdUsuario()));
            Optional<Jugadores> jugador = jugadoresRepository.findByPartidaAndUsuario(partida , usuario);
            if(jugador.isPresent()) {
                jugadorEntity = jugador.get();
            }
            else {
                jugadorEntity = new Jugadores();
            }
            jugadorEntity.setUsuario(usuario);
        } else {
            jugadorEntity = new Jugadores(); //para el botardo
        }

        jugadorEntity.setPartida(partida);
        jugadorEntity.setNombre(jugadorDTO.getNombre());

        Colores color = coloresService.getColorById(Long.valueOf(jugadorDTO.getIdColor()));
        jugadorEntity.setColor(color);

        // validar el enum despues aca
        jugadorEntity.setTipoJugador(jugadorDTO.getTipoJugador());
        jugadorEntity.setEstadoJugador(EstadoJugador.VIVO.toString());
        jugadoresRepository.save(jugadorEntity);
    }

    @Override
    public List<JugadorModel> mapearJugadores(List<Jugadores> jugadoresEntity) {
        List<JugadorModel> jugadoresModel = new ArrayList<>();
        for (Jugadores jugador : jugadoresEntity) {
            jugadoresModel.add(mapearJugador(jugador));
        }

        return jugadoresModel;
    }

    @Override
    public JugadorModel mapearJugador(Jugadores jugador) {
        JugadorModel jugadorModel = new JugadorModel();
        jugadorModel.mapJugadorModel(jugador);

        List<PaisJugadorModel> paises = paisesJugadoresService.getPaisesFromJugadorId(jugador);

        jugadorModel.setPaises(paises);

        List<TarjetaModel> tarjetas = new ArrayList<>();
        List<TarjetaJugadorModel> tarjetasjugador = tarjetasJugadoresService.getTarjetasJugadores(jugador.getId());
        for(TarjetaJugadorModel tarjetaJugador : tarjetasjugador) {
            tarjetas.add(tarjetaJugador.getTarjeta());

        }
        jugadorModel.setTarjetas(tarjetas);
        return jugadorModel;

    }

    public JugadorModel buscarJugador(Long id) {
        JugadorModel jugadorModel = new JugadorModel();
        jugadorModel = mapearJugador(jugadoresRepository.getReferenceById(id));
        return jugadorModel;
    }
}
