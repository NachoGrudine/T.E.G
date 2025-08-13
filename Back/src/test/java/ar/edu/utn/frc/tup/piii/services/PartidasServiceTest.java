package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.dtos.common.*;
import ar.edu.utn.frc.tup.piii.entities.*;
import ar.edu.utn.frc.tup.piii.enums.Estado;
import ar.edu.utn.frc.tup.piii.enums.TipoJugador;
import ar.edu.utn.frc.tup.piii.enums.TipoPartida;
import ar.edu.utn.frc.tup.piii.models.*;
import ar.edu.utn.frc.tup.piii.repositories.PartidasRepository;
import ar.edu.utn.frc.tup.piii.services.interfaces.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.*;
import java.util.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PartidasServiceTest {

    @Mock
    private PartidasRepository partidasRepository;
    @Mock
    private IUsuariosService usuariosService;
    @Mock
    private IObjetivosService objetivosService;
    @Mock
    private IJugadoresService jugadoresService;
    @Mock
    private IPaisesService paisesService;
    @Mock
    private IPaisesJugadoresService paisesJugadoresService;
    @Mock
    private IColoresService coloresService;
    @Mock
    private RondasService rondasService;
    @Mock
    private TurnosRefuerzosService turnosRefuerzosService;
    @Mock
    private TurnosAtaquesService turnosAtaquesService;
    @Mock
    private BotService botService;

    @InjectMocks
    private PartidasService partidasService;

    private Partidas partida;
    private Usuarios usuario;
    private Jugadores jugador;
    private JugadorModel jugadorModel;
    private Objetivos objetivo;
    private ColorModel colorModel;

    @BeforeEach
    void setUp() {
        partida = new Partidas();
        partida.setId(1);
        usuario = new Usuarios();
        usuario.setId(1);
        partida.setUsuario(usuario);
        partida.setEstado(Estado.EN_PREPARACION.name());
        partida.setTipo(TipoPartida.PRIVADA.name());
        partida.setCantidadParaGanar(32);

        jugador = new Jugadores();
        jugador.setId(1);
        jugador.setPartida(partida);

        jugadorModel = new JugadorModel();
        jugadorModel.setId(1);
        jugadorModel.setTipoJugador(TipoJugador.HUMANO);

        objetivo = new Objetivos();
        objetivo.setId(1);

        colorModel = new ColorModel();
        colorModel.setId(1);
    }

    @Test
    void crearPartida_creaPartidaCorrectamente() {
        when(usuariosService.getUsuarioById(1L)).thenReturn(usuario);
        when(partidasRepository.save(any(Partidas.class))).thenReturn(partida);

        PartidaModel result = partidasService.crearPartida(1L);

        assertNotNull(result);
        assertEquals(partida.getId(), result.getId());
        verify(partidasRepository).save(any(Partidas.class));
    }

    @Test
    void crearPartida_usuarioNoExiste_lanzaExcepcion() {
        when(usuariosService.getUsuarioById(2L)).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> partidasService.crearPartida(2L));
    }

    @Test
    void getPartidas_devuelvePartidasDelUsuario() {
        when(usuariosService.getUsuarioById(1L)).thenReturn(usuario);
        when(partidasRepository.getAllByUsuario(usuario)).thenReturn(List.of(partida));
        when(jugadoresService.getAllByPartida(partida)).thenReturn(List.of(jugador));
        when(jugadoresService.mapearJugadores(anyList())).thenReturn(List.of(jugadorModel));

        List<PartidaModel> result = partidasService.getPartidas(1L);

        assertEquals(1, result.size());
        assertNull(result.get(0).getJugadores().get(0).getObjetivo());
    }

    @Test
    void buscarPartidaEntity_ok() {
        when(partidasRepository.findById(1L)).thenReturn(Optional.of(partida));
        assertEquals(partida, partidasService.buscarPartidaEntity(1L));
    }

    @Test
    void buscarPartidaEntity_throwSiNoExiste() {
        when(partidasRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> partidasService.buscarPartidaEntity(999L));
    }

    @Test
    void buscarPartida_ok() {
        when(partidasRepository.getReferenceById(1L)).thenReturn(partida);
        when(jugadoresService.getAllByPartida(partida)).thenReturn(List.of(jugador));
        when(jugadoresService.mapearJugadores(anyList())).thenReturn(List.of(jugadorModel));

        PartidaModel result = partidasService.buscarPartida(1L);

        assertNotNull(result);
        assertNull(result.getJugadores().get(0).getObjetivo());
    }

    @Test
    void obtenerPartidasPorTipoYEstado_ok() {
        when(partidasRepository.findAllByTipoAndEstado(anyString(), anyString())).thenReturn(List.of(partida));
        when(jugadoresService.getAllByPartida(partida)).thenReturn(List.of(jugador));
        when(jugadoresService.mapearJugadores(anyList())).thenReturn(List.of(jugadorModel));

        List<PartidaModel> result = partidasService.obtenerPartidasPorTipoYEstado();

        assertEquals(1, result.size());
        assertNull(result.get(0).getJugadores().get(0).getObjetivo());
    }

    @Test
    void empezarPartida_ok() {
        partida.setEstado(Estado.EN_PREPARACION.name());
        Colores color = new Colores();
        color.setId(1);
        jugador.setColor(color);

        RondaModel rondaMock = new RondaModel();
        List<TurnoRefuerzoModel> turnos = new ArrayList<>();
        TurnoRefuerzoModel turnoRefuerzo = new TurnoRefuerzoModel();
        turnoRefuerzo.setIdJugador(1);
        turnos.add(turnoRefuerzo);
        rondaMock.setTurnosRefuerzo(turnos);

        when(partidasRepository.findById(1L)).thenReturn(Optional.of(partida));
        when(jugadoresService.getAllByPartida(partida)).thenReturn(List.of(jugador));
        when(objetivosService.getAllObjetivosEntity()).thenReturn(List.of(objetivo));
        when(paisesService.getAllPaisesEntity()).thenReturn(List.of(new Paises()));
        doNothing().when(paisesJugadoresService).asignarPaisesAJugadores(anyList(), anyList(), eq(partida));
        when(rondasService.crearRonda(any(), eq(partida))).thenReturn(rondaMock);
        doNothing().when(jugadoresService).saveAll(anyList());
        when(partidasRepository.save(partida)).thenReturn(partida);
        when(jugadoresService.buscarJugador(1L)).thenReturn(jugadorModel);

        assertDoesNotThrow(() -> partidasService.empezarPartida(1L));
        verify(partidasRepository).save(partida);
    }

    @Test
    void empezarPartida_noSuficientesObjetivos_lanzaExcepcion() {
        partida.setEstado(Estado.EN_PREPARACION.name());
        when(partidasRepository.findById(1L)).thenReturn(Optional.of(partida));
        when(jugadoresService.getAllByPartida(partida)).thenReturn(List.of(jugador, new Jugadores()));
        when(objetivosService.getAllObjetivosEntity()).thenReturn(List.of(objetivo));

        assertThrows(RuntimeException.class, () -> partidasService.empezarPartida(1L));
    }

    @Test
    void agregarJugador_ok() {
        when(partidasRepository.findById(1L)).thenReturn(Optional.of(partida));
        doNothing().when(jugadoresService).agregarJugador(eq(partida), any(JugadorDTO.class));
        when(jugadoresService.getAllByPartida(partida)).thenReturn(List.of(jugador));
        when(jugadoresService.mapearJugadores(anyList())).thenReturn(List.of(jugadorModel));

        PartidaModel result = partidasService.agregarJugador(1L, new JugadorDTO());
        assertNotNull(result);
        assertEquals(1, result.getJugadores().size());
    }

    @Test
    void buscarColores_ok() {
        when(coloresService.getAllColores()).thenReturn(List.of(colorModel));
        when(partidasRepository.findById(1L)).thenReturn(Optional.of(partida));
        when(jugadoresService.getAllByPartida(partida)).thenReturn(List.of(jugador));
        ColorModel colorJugador = new ColorModel();
        colorJugador.setId(2);
        JugadorModel jugadorConColor = new JugadorModel();
        jugadorConColor.setId(1);
        jugadorConColor.setColor(colorJugador);
        when(jugadoresService.mapearJugadores(anyList())).thenReturn(List.of(jugadorConColor));

        List<ColorModel> result = partidasService.buscarColores(1L);
        assertEquals(1, result.size());
        assertEquals(colorModel.getId(), result.get(0).getId());
    }
    @Test
    void buscarUltimaRonda_ok() {
        RondaModel ronda = new RondaModel();
        JugadorModel jugador1 = new JugadorModel();
        jugador1.setId(1);
        ObjetivoModel objetivoModel = new ObjetivoModel();
        objetivoModel.setIdObjetivo(objetivo.getId());
        jugador1.setObjetivo(objetivoModel);
        JugadorModel jugador2 = new JugadorModel();
        jugador2.setId(2);
        jugador2.setObjetivo(objetivoModel);
        ronda.setJugadores(List.of(jugador1, jugador2));

        when(partidasRepository.findById(1L)).thenReturn(Optional.of(partida));
        when(rondasService.getUltimaRondaPartidaModel(any(), any())).thenReturn(ronda);
        when(objetivosService.getByIdObjetivo(Long.valueOf(objetivoModel.getIdObjetivo()))).thenReturn(objetivoModel);

        RondaModel result = partidasService.buscarUltimaRonda(1L, 1L);

        assertNotNull(result.getJugadores().get(0).getObjetivo());
        assertEquals(objetivo.getId(), result.getJugadores().get(0).getObjetivo().getIdObjetivo());
    }

    @Test
    void obtenerTurnoRefuerzo_ok() {
        TurnoRefuerzoModel turno = new TurnoRefuerzoModel();
        when(turnosRefuerzosService.obtenerTurnoRefuerzo(1L)).thenReturn(turno);
        assertEquals(turno, partidasService.obtenerTurnoRefuerzo(1L));
    }

    @Test
    void obtenerFichas_ok() {
        FichasDTO fichas = new FichasDTO();
        when(turnosRefuerzosService.obtenerFichas(1L)).thenReturn(fichas);
        assertEquals(fichas, partidasService.obtenerFichas(1L));
    }

    @Test
    void guardarRefuerzos_ok() {
        doNothing().when(turnosRefuerzosService).guardarRefuerzos(anyList());
        assertDoesNotThrow(() -> partidasService.guardarRefuerzos(List.of(new RefuerzoPostDTO())));
    }

    @Test
    void terminarTurnoRefuerzo_ok() {
        doNothing().when(turnosRefuerzosService).terminarTurnoRefuerzo(1L);
        PartidaModel partidaModel = new PartidaModel();
        when(partidasRepository.getReferenceById(1L)).thenReturn(partida);
        when(jugadoresService.getAllByPartida(partida)).thenReturn(List.of(jugador));
        when(jugadoresService.mapearJugadores(anyList())).thenReturn(List.of(jugadorModel));
        when(rondasService.getNextTurnRefuerzo(anyLong(), any(), anyLong())).thenReturn(null);

        // Mock de ronda con turnosAtaque inicializado
        RondaModel rondaConTurnoAtaque = new RondaModel();
        List<TurnoAtaqueModel> turnosAtaque = new ArrayList<>();
        TurnoAtaqueModel turnoAtaque = new TurnoAtaqueModel();
        turnoAtaque.setIdJugador(1); // o el id que corresponda
        turnosAtaque.add(turnoAtaque);
        rondaConTurnoAtaque.setTurnosAtaque(turnosAtaque);

        when(rondasService.getUltimaRondaPartidaModel(any(), any())).thenReturn(rondaConTurnoAtaque);
        when(partidasRepository.findById(1L)).thenReturn(Optional.of(partida));
        when(jugadoresService.buscarJugador(anyLong())).thenReturn(jugadorModel);

        assertDoesNotThrow(() -> partidasService.terminarTurnoRefuerzo(1L, 1L, 1L));
    }

    @Test
    void obtenerTurnoAtaque_ok() {
        TurnoAtaqueModel turno = new TurnoAtaqueModel();
        when(turnosAtaquesService.obtenerTurnoAtaque(1L)).thenReturn(turno);
        assertEquals(turno, partidasService.obtenerTurnoAtaque(1L));
    }

    @Test
    void getPaisesFromPartida_ok() {
        when(partidasRepository.findById(1L)).thenReturn(Optional.of(partida));
        List<PaisJugadorModel> paises = List.of(new PaisJugadorModel());
        when(paisesJugadoresService.getPaisesFromPartidaId(partida)).thenReturn(paises);

        assertEquals(paises, partidasService.getPaisesFromPartida(1L));
    }

    @Test
    void configurarPartida_ok() {
        when(partidasRepository.findById(1L)).thenReturn(Optional.of(partida));
        when(partidasRepository.save(partida)).thenReturn(partida);

        partidasService.configurarPartida(1L, 5, "PUBLICA");

        assertEquals(5, partida.getCantidadParaGanar());
        assertEquals("PUBLICA", partida.getTipo());
        verify(partidasRepository).save(partida);
    }

    @Test
    void hacerAtaque_ok() {
        AccionCombatePostDTO dto = new AccionCombatePostDTO();
        AccionModel accion = new AccionModel();
        when(turnosAtaquesService.hacerAtaque(dto)).thenReturn(accion);

        assertEquals(accion, partidasService.hacerAtaque(dto));
    }

    @Test
    void hacerReagrupacion_ok() {
        AccionReagrupacionPostDTO dto = new AccionReagrupacionPostDTO();
        AccionModel accion = new AccionModel();
        when(turnosAtaquesService.hacerReagrupacion(dto)).thenReturn(accion);

        assertEquals(accion, partidasService.hacerReagrupacion(dto));
    }


    @Test
    void terminarTurnoAtaque_ok() {
        doNothing().when(turnosAtaquesService).terminarTurnoAtaque(1L);
        PartidaModel partidaModel = new PartidaModel();
        when(partidasRepository.getReferenceById(1L)).thenReturn(partida);
        when(partidasRepository.findById(1L)).thenReturn(Optional.of(partida));
        when(jugadoresService.getAllByPartida(partida)).thenReturn(List.of(jugador));
        when(jugadoresService.mapearJugadores(anyList())).thenReturn(List.of(jugadorModel));
        when(rondasService.getNextTurnAtaque(anyLong(), any(), anyLong())).thenReturn(null);

        RondaModel rondaConTurnoRefuerzo = new RondaModel();
        List<TurnoRefuerzoModel> turnosRefuerzo = new ArrayList<>();
        TurnoRefuerzoModel turnoRefuerzo = new TurnoRefuerzoModel();
        turnoRefuerzo.setIdJugador(1);
        turnosRefuerzo.add(turnoRefuerzo);
        rondaConTurnoRefuerzo.setTurnosRefuerzo(turnosRefuerzo);

        when(rondasService.crearRonda(any(), any())).thenReturn(rondaConTurnoRefuerzo);
        when(jugadoresService.buscarJugador(anyLong())).thenReturn(jugadorModel);

        assertDoesNotThrow(() -> partidasService.terminarTurnoAtaque(1L, 1L, 1L));
    }

    @Test
    void chekearGanador_ganaJugador() {
        jugador.setId(1);
        Partidas partida = new Partidas();
        partida.setCantidadParaGanar(1);
        jugador.setPartida(partida);
        when(jugadoresService.getJugadorByIdEntity(1L)).thenReturn(jugador);
        when(paisesJugadoresService.getPaisesFromJugadorId(jugador)).thenReturn(List.of(new PaisJugadorModel()));

        GanadorDTO ganador = partidasService.chekearGanador(1);

        assertEquals(1, ganador.getIdJugador());
    }

    @Test
    void chekearGanador_noGanaJugador() {
        jugador.setId(1);
        Partidas partida = new Partidas();
        partida.setCantidadParaGanar(2);
        jugador.setPartida(partida);
        when(jugadoresService.getJugadorByIdEntity(1L)).thenReturn(jugador);
        when(paisesJugadoresService.getPaisesFromJugadorId(jugador)).thenReturn(List.of(new PaisJugadorModel()));

        GanadorDTO ganador = partidasService.chekearGanador(1);

        assertEquals(0, ganador.getIdJugador());
    }
}