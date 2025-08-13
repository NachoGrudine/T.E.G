package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.dtos.common.*;
import ar.edu.utn.frc.tup.piii.enums.*;
import ar.edu.utn.frc.tup.piii.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BotServiceTest {

    @Mock
    private PaisesJugadoresService paisesJugadoresService;

    @Mock
    private TurnosRefuerzosService turnosRefuerzosService;

    @Mock
    private FronterasService fronterasService;

    @Mock
    private ObjetivosCantPaisesService objetivosCantPaisesService;

    @Mock
    private TurnosAtaquesService turnosAtaquesService;

    @InjectMocks
    private BotService botService;

    private JugadorModel jugadorNovato;
    private JugadorModel jugadorBalanceado;
    private JugadorModel jugadorExperto;
    private TurnoRefuerzoModel turnoRefuerzo;
    private TurnoAtaqueModel turnoAtaque;
    private FichasDTO fichasDTO;

    @BeforeEach
    void setUp() {
        // Configurar jugador novato
        jugadorNovato = new JugadorModel();
        jugadorNovato.setId(1);
        jugadorNovato.setTipoJugador(TipoJugador.BOT_NOVATO);
        jugadorNovato.setPaises(crearListaPaises());

        // Configurar jugador balanceado
        jugadorBalanceado = new JugadorModel();
        jugadorBalanceado.setId(2);
        jugadorBalanceado.setTipoJugador(TipoJugador.BOT_BALANCEADO);
        ObjetivoModel objetivo = new ObjetivoModel();
        objetivo.setIdObjetivo(1);
        jugadorBalanceado.setObjetivo(objetivo);
        jugadorBalanceado.setPaises(crearListaPaises());

        // Configurar jugador experto
        jugadorExperto = new JugadorModel();
        jugadorExperto.setId(3);
        jugadorExperto.setTipoJugador(TipoJugador.BOT_EXPERTO);
        jugadorExperto.setObjetivo(objetivo);
        jugadorExperto.setPaises(crearListaPaises());

        // Configurar turno refuerzo
        turnoRefuerzo = new TurnoRefuerzoModel();
        turnoRefuerzo.setId(1);

        // Configurar turno ataque
        turnoAtaque = new TurnoAtaqueModel();
        turnoAtaque.setId(1);

        // Configurar fichas
        fichasDTO = new FichasDTO();
        fichasDTO.setFichasPais(5);
    }

    @Test
    void jugarRefuerzo_conBotNovato_deberiaLlamarMetodoNovato() {

        when(turnosRefuerzosService.obtenerFichas(1L)).thenReturn(fichasDTO);


        botService.jugarRefuerzo(1L, 1L, turnoRefuerzo, jugadorNovato);


        verify(turnosRefuerzosService).obtenerFichas(1L);
        verify(turnosRefuerzosService).guardarRefuerzos(any());
    }

    @Test
    void jugarRefuerzo_conBotBalanceado_deberiaLlamarMetodoBalanceado() {

        when(turnosRefuerzosService.obtenerFichas(1L)).thenReturn(fichasDTO);
        when(objetivosCantPaisesService.obtenerIdsPaisesDeObjetivo(1)).thenReturn(Arrays.asList(1, 2));
        when(fronterasService.findAllByPais1_IdOrPais2_Id(anyInt())).thenReturn(Arrays.asList(3, 4));
        when(paisesJugadoresService.esPropio(any(), anyInt())).thenReturn(false);


        botService.jugarRefuerzo(1L, 1L, turnoRefuerzo, jugadorBalanceado);

        verify(turnosRefuerzosService).obtenerFichas(1L);
        verify(objetivosCantPaisesService, atLeastOnce()).obtenerIdsPaisesDeObjetivo(1);
        verify(turnosRefuerzosService).guardarRefuerzos(any());
    }

    @Test
    void jugarRefuerzo_conBotExperto_deberiaLlamarMetodoExperto() {

        when(turnosRefuerzosService.obtenerFichas(1L)).thenReturn(fichasDTO);
        when(objetivosCantPaisesService.obtenerIdsPaisesDeObjetivo(1)).thenReturn(Arrays.asList(1, 2));


        botService.jugarRefuerzo(1L, 1L, turnoRefuerzo, jugadorExperto);


        verify(turnosRefuerzosService).obtenerFichas(1L);
        verify(objetivosCantPaisesService, atLeastOnce()).obtenerIdsPaisesDeObjetivo(1);
        verify(turnosRefuerzosService).guardarRefuerzos(any());
    }

    @Test
    void ponerFichasNovato_deberiaDistribuirFichasAleatoriamente() {

        List<PaisJugadorModel> paises = crearListaPaises();


        botService.ponerFichasNovato(paises, 3, 1, TipoFicha.DE_PAISES);


        verify(turnosRefuerzosService).guardarRefuerzos(argThat(lista -> {
            int totalFichas = lista.stream().mapToInt(RefuerzoPostDTO::getCantidad).sum();
            return totalFichas == 3 && !lista.isEmpty();
        }));
    }

    @Test
    void ponerFichasBalanceado_deberiaPriorizarObjetivos() {

        List<PaisJugadorModel> paises = crearListaPaises();
        when(objetivosCantPaisesService.obtenerIdsPaisesDeObjetivo(1)).thenReturn(Arrays.asList(1, 2));
        when(fronterasService.findAllByPais1_IdOrPais2_Id(anyInt())).thenReturn(Arrays.asList(3, 4));
        when(paisesJugadoresService.esPropio(any(), anyInt())).thenReturn(false);


        botService.ponerFichasBalanceado(paises, 3, 1, TipoFicha.DE_PAISES, jugadorBalanceado);


        verify(objetivosCantPaisesService, atLeastOnce()).obtenerIdsPaisesDeObjetivo(1);
        verify(turnosRefuerzosService).guardarRefuerzos(any());
    }

    @Test
    void ponerFichasExperto_deberiaPriorizarSoloObjetivos() {

        List<PaisJugadorModel> paises = crearListaPaises();
        when(objetivosCantPaisesService.obtenerIdsPaisesDeObjetivo(1)).thenReturn(Arrays.asList(1, 2));


        botService.ponerFichasExperto(paises, 3, 1, TipoFicha.DE_PAISES, jugadorExperto);


        verify(objetivosCantPaisesService, atLeastOnce()).obtenerIdsPaisesDeObjetivo(1);
        verify(turnosRefuerzosService).guardarRefuerzos(any());
    }

    @Test
    void jugarAtaque_conBotNovato_deberiaAtacar() {

        List<PaisJugadorModel> paisesConFichas = List.of(crearPaisJugador(1, 3));
        when(paisesJugadoresService.getPaisesFromJugadorAndFichas(1, 1)).thenReturn(paisesConFichas);
        when(fronterasService.findAllByPais1_IdOrPais2_Id(1)).thenReturn(Arrays.asList(2));

        // Mock que simula reducción de fichas después del ataque
        PaisJugadorModel paisAtacante1 = crearPaisJugador(1, 3); // Estado inicial
        PaisJugadorModel paisAtacante2 = crearPaisJugador(1, 1); // Después del ataque, pocas fichas
        PaisJugadorModel paisEnemigo = crearPaisJugador(2, 2);
        paisEnemigo.setIdJugador(2);

        when(paisesJugadoresService.getPaisJugadorByPaisId(1, 1))
                .thenReturn(paisAtacante1)  // Primera llamada
                .thenReturn(paisAtacante2); // Segunda llamada con menos fichas

        when(paisesJugadoresService.getPaisJugadorByPaisId(2, 1)).thenReturn(paisEnemigo);

        AccionModel accion = new AccionModel();
        CombateModel combate = new CombateModel();
        combate.setFichasDef(1); // No conquistado
        accion.setCombate(combate);
        when(turnosAtaquesService.hacerAtaque(any())).thenReturn(accion);


        botService.jugarAtaque(1L, 1L, turnoAtaque, jugadorNovato);


        verify(paisesJugadoresService).getPaisesFromJugadorAndFichas(1, 1);
        verify(turnosAtaquesService, atLeastOnce()).hacerAtaque(any());
    }

    @Test
    void jugarAtaque_conBotBalanceado_deberiaLimitarAtaques() {

        List<PaisJugadorModel> paisesConFichas = List.of(crearPaisJugador(1, 6));
        when(paisesJugadoresService.getPaisesFromJugadorAndFichas(2, 1)).thenReturn(paisesConFichas);
        when(fronterasService.findAllByPais1_IdOrPais2_Id(1)).thenReturn(Arrays.asList(2));
        when(objetivosCantPaisesService.obtenerIdsPaisesDeObjetivo(1)).thenReturn(Arrays.asList(2));

        // Mock que evita bucle infinito reduciendo fichas gradualmente
        PaisJugadorModel paisAtacante1 = crearPaisJugador(1, 6);
        PaisJugadorModel paisAtacante2 = crearPaisJugador(1, 1); // Muy pocas fichas
        PaisJugadorModel paisEnemigo = crearPaisJugador(2, 3);
        paisEnemigo.setIdJugador(3);

        when(paisesJugadoresService.getPaisJugadorByPaisId(1, 1))
                .thenReturn(paisAtacante1)
                .thenReturn(paisAtacante2);

        when(paisesJugadoresService.getPaisJugadorByPaisId(2, 1)).thenReturn(paisEnemigo);

        AccionModel accion = new AccionModel();
        CombateModel combate = new CombateModel();
        combate.setFichasDef(2); // No conquistado
        accion.setCombate(combate);
        when(turnosAtaquesService.hacerAtaque(any())).thenReturn(accion);


        botService.jugarAtaque(1L, 1L, turnoAtaque, jugadorBalanceado);


        verify(turnosAtaquesService, atLeastOnce()).hacerAtaque(any());
    }

    @Test
    void jugarAtaque_conBotExperto_deberiaUsarLogicaNovato() {

        List<PaisJugadorModel> paisesConFichas = List.of(crearPaisJugador(1, 4));
        when(paisesJugadoresService.getPaisesFromJugadorAndFichas(3, 1)).thenReturn(paisesConFichas);
        when(fronterasService.findAllByPais1_IdOrPais2_Id(1)).thenReturn(List.of(2));

        // Mock que termina el bucle
        PaisJugadorModel paisAtacante1 = crearPaisJugador(1, 4);
        PaisJugadorModel paisAtacante2 = crearPaisJugador(1, 1); // Pocas fichas
        PaisJugadorModel paisEnemigo = crearPaisJugador(2, 1);
        paisEnemigo.setIdJugador(4);

        when(paisesJugadoresService.getPaisJugadorByPaisId(1, 1))
                .thenReturn(paisAtacante1)
                .thenReturn(paisAtacante2);

        when(paisesJugadoresService.getPaisJugadorByPaisId(2, 1)).thenReturn(paisEnemigo);

        AccionModel accion = new AccionModel();
        CombateModel combate = new CombateModel();
        combate.setFichasDef(0); // País conquistado
        accion.setCombate(combate);
        when(turnosAtaquesService.hacerAtaque(any())).thenReturn(accion);


        botService.jugarAtaque(1L, 1L, turnoAtaque, jugadorExperto);


        verify(paisesJugadoresService).getPaisesFromJugadorAndFichas(3, 1);
        verify(turnosAtaquesService, atLeastOnce()).hacerAtaque(any());
        verify(turnosAtaquesService, atLeastOnce()).hacerReagrupacion(any());
    }

    @Test
    void jugarRefuerzo_conFichasContinente_deberiaDistribuirlas() {

        Map<Integer, Integer> fichasContinente = new HashMap<>();
        fichasContinente.put(1, 3);
        fichasDTO.setFichasContinente(fichasContinente);

        when(turnosRefuerzosService.obtenerFichas(1L)).thenReturn(fichasDTO);
        when(paisesJugadoresService.getPaisesFromContinenteId(1)).thenReturn(crearListaPaises());


        botService.jugarRefuerzo(1L, 1L, turnoRefuerzo, jugadorNovato);


        verify(paisesJugadoresService).getPaisesFromContinenteId(1);
        verify(turnosRefuerzosService, times(2)).guardarRefuerzos(any()); // Una para países, otra para continente
    }

    private List<PaisJugadorModel> crearListaPaises() {
        return Arrays.asList(
                crearPaisJugador(1, 2),
                crearPaisJugador(2, 3),
                crearPaisJugador(3, 1)
        );
    }

    private PaisJugadorModel crearPaisJugador(Integer idPais, Integer fichas) {
        PaisJugadorModel pais = new PaisJugadorModel();
        pais.setIdPais(idPais);
        pais.setFichas(fichas);
        pais.setIdJugador(1);
        return pais;
    }
}