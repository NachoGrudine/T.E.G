package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.dtos.common.JugadorDTO;
import ar.edu.utn.frc.tup.piii.entities.*;
import ar.edu.utn.frc.tup.piii.models.JugadorModel;
import ar.edu.utn.frc.tup.piii.repositories.JugadoresRepository;
import ar.edu.utn.frc.tup.piii.services.interfaces.IColoresService;
import ar.edu.utn.frc.tup.piii.services.interfaces.IPaisesJugadoresService;
import ar.edu.utn.frc.tup.piii.services.interfaces.IPaisesService;
import ar.edu.utn.frc.tup.piii.services.interfaces.IUsuariosService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JugadoresServiceTest {

    @Mock
    JugadoresRepository jugadoresRepository;

    @InjectMocks
    JugadoresService jugadoresService;
    @Mock
    UsuariosService usuariosService;
    @Mock
    ColoresService coloresService;
    @Mock
    PaisesJugadoresService paisesJugadoresService;
    @Mock
    TarjetasJugadoresService tarjetasJugadoresService;


    @Test
    void getAllByPartida() {
        Partidas partida = new Partidas();
        Jugadores jugador1 = new Jugadores();

        List<Jugadores> jugadoresList = List.of(jugador1);

        when(jugadoresRepository.getAllByPartidaOrderByIdAsc(partida)).thenReturn(jugadoresList);

        List<Jugadores> result = jugadoresService.getAllByPartida(partida);
        assertNotNull(result);
        assertEquals(jugadoresList, result);
        assertEquals(1, result.size());
        verify(jugadoresRepository).getAllByPartidaOrderByIdAsc(partida);
    }

    @Test
    void saveAll() {
        Jugadores jugador1 = new Jugadores();
        Jugadores jugador2 = new Jugadores();
        List<Jugadores> jugadoresList = List.of(jugador1, jugador2);

        jugadoresService.saveAll(jugadoresList);

        verify(jugadoresRepository).saveAll(jugadoresList);
    }

    @Test
    void getJugadorByIdEntity() {
        Long id = 1L;
        Jugadores jugador = new Jugadores();
        jugador.setId(Math.toIntExact(id));

        when(jugadoresRepository.getReferenceById(id)).thenReturn(jugador);

        Jugadores result = jugadoresService.getJugadorByIdEntity(id);
        assertNotNull(result);
        assertEquals(jugador, result);
        verify(jugadoresRepository).getReferenceById(id);
    }

    @Test
    void agregarJugador() {
        Partidas partidas = new Partidas();
        JugadorDTO jugadorDTO = new JugadorDTO();
        jugadorDTO.setNombre("Leonel Messi");
        jugadorDTO.setIdUsuario(1);
        jugadorDTO.setIdColor(1);
        jugadorDTO.setTipoJugador("HUMANO");

        Usuarios usuario = new Usuarios();
        usuario.setId(1);

        Colores colores = new Colores();
        colores.setId(1);

        Jugadores jugadorEntity = new Jugadores();

        when(usuariosService.getUsuarioById(1L)).thenReturn(usuario);
        when(coloresService.getColorById(1L)).thenReturn(colores);
        when(jugadoresRepository.findByPartidaAndUsuario(partidas, usuario)).thenReturn(java.util.Optional.empty());

       jugadoresService.agregarJugador(partidas, jugadorDTO);
       verify(usuariosService).getUsuarioById(1L);
       verify(coloresService).getColorById(1L);

       verify(jugadoresRepository).save(any(Jugadores.class));
    }

    @Test
    void mapearJugadores() {

        Jugadores jugador = new Jugadores();
        jugador.setId(1);
        jugador.setNombre("Leonel Messi");
        jugador.setTipoJugador("HUMANO");
        jugador.setEstadoJugador("VIVO");

        // Setear usuario
        Usuarios usuario = new Usuarios();
        usuario.setId(1);
        usuario.setUsername("Neymar jr");
        jugador.setUsuario(usuario);

        // Setear color
        Colores colores = new Colores();
        colores.setId(1);
        colores.setColor("Azul");
        jugador.setColor(colores);

        // Setear partida
        Partidas partidas = new Partidas();
        partidas.setId(1);
        jugador.setPartida(partidas);

        // Mock de dependencias
        when(paisesJugadoresService.getPaisesFromJugadorId(jugador)).thenReturn(List.of());
        when(tarjetasJugadoresService.getTarjetasJugadores(jugador.getId())).thenReturn(List.of());

        List<Jugadores> jugadoresList = List.of(jugador);
        List<JugadorModel> result = jugadoresService.mapearJugadores(jugadoresList);

        assertNotNull(result);
        assertEquals(1, result.size());
        JugadorModel model = result.get(0);
        assertEquals(jugador.getId(), model.getId());
        assertEquals(jugador.getNombre(), model.getNombre());
        assertEquals(jugador.getTipoJugador(), model.getTipoJugador().name());
        assertEquals(jugador.getEstadoJugador(), model.getEstadoJugador().name());
        assertEquals(jugador.getUsuario().getId(), model.getIdUsuario());
        assertEquals(jugador.getColor().getColor(), model.getColor().getColor());
        assertEquals(jugador.getPartida().getId(), model.getIdPartida());

        verify(paisesJugadoresService).getPaisesFromJugadorId(jugador);
        verify(tarjetasJugadoresService).getTarjetasJugadores(jugador.getId());

    }

    @Test
    void mapearJugador() {
        Jugadores jugador = new Jugadores();
        jugador.setId(1);
        jugador.setNombre("Cristiano Ronaldo");
        jugador.setTipoJugador("HUMANO");
        jugador.setEstadoJugador("VIVO");

        // Setear usuario
        Usuarios usuario = new Usuarios();
        usuario.setId(2);
        usuario.setUsername("CR7");
        jugador.setUsuario(usuario);

        // Setear color
        Colores colores = new Colores();
        colores.setId(2);
        colores.setColor("Rojo");
        jugador.setColor(colores);

        // Setear partida
        Partidas partidas = new Partidas();
        partidas.setId(2);
        jugador.setPartida(partidas);

        // Mock de dependencias
        when(paisesJugadoresService.getPaisesFromJugadorId(jugador)).thenReturn(List.of());
        when(tarjetasJugadoresService.getTarjetasJugadores(jugador.getId())).thenReturn(List.of());

        JugadorModel result = jugadoresService.mapearJugador(jugador);

        assertNotNull(result);
        assertEquals(jugador.getId(), result.getId());
        assertEquals(jugador.getNombre(), result.getNombre());
        assertEquals(jugador.getTipoJugador(), result.getTipoJugador().name());
        assertEquals(jugador.getEstadoJugador(), result.getEstadoJugador().name());
        assertEquals(jugador.getUsuario().getId(), result.getIdUsuario());
        assertEquals(jugador.getColor().getColor(), result.getColor().getColor());
        assertEquals(jugador.getPartida().getId(), result.getIdPartida());

        verify(paisesJugadoresService).getPaisesFromJugadorId(jugador);
        verify(tarjetasJugadoresService).getTarjetasJugadores(jugador.getId());

    }

    @Test
    void buscarJugador() {

        Jugadores jugadorEntity = new Jugadores();
        jugadorEntity.setId(1);
        jugadorEntity.setNombre("Diego Maradona");
        jugadorEntity.setTipoJugador("HUMANO");
        jugadorEntity.setEstadoJugador("VIVO");

        Partidas partida = new Partidas();
        partida.setId(1);
        jugadorEntity.setPartida(partida);

        Colores color = new Colores();
        color.setId(1);
        color.setColor("Celeste");
        jugadorEntity.setColor(color);

        Usuarios usuario = new Usuarios();
        usuario.setId(1);
        usuario.setUsername("dmaradona");
        jugadorEntity.setUsuario(usuario);

        when(jugadoresRepository.getReferenceById(1L)).thenReturn(jugadorEntity);
        when(paisesJugadoresService.getPaisesFromJugadorId(jugadorEntity)).thenReturn(List.of());
        when(tarjetasJugadoresService.getTarjetasJugadores(jugadorEntity.getId())).thenReturn(List.of());

        JugadorModel result = jugadoresService.buscarJugador(1L);
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Diego Maradona", result.getNombre());
    }
    }

