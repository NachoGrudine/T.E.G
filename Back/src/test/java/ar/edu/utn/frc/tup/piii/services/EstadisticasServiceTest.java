package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.entities.*;
import ar.edu.utn.frc.tup.piii.repositories.ColoresRepository;
import ar.edu.utn.frc.tup.piii.repositories.JugadoresRepository;
import ar.edu.utn.frc.tup.piii.repositories.ObjetivosRepository;
import ar.edu.utn.frc.tup.piii.repositories.PartidasRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstadisticasServiceTest {

    @Mock
    private PartidasRepository partidasRepository;

    @Mock
    private JugadoresRepository jugadoresRepository;

    @Mock
    private ColoresRepository coloresRepository;

    @Mock
    private ObjetivosRepository objetivosRepository;

    @InjectMocks
    private EstadisticasService estadisticasService;

    private Usuarios usuario1;
    private Usuarios usuario2;
    private Jugadores jugador1;
    private Jugadores jugador2;
    private Jugadores jugador3;
    private Partidas partida1;
    private Partidas partida2;
    private Partidas partida3;
    private Colores colorRojo;
    private Colores colorAzul;
    private Objetivos objetivo1;
    private Objetivos objetivo2;

    @BeforeEach
    void setUp() {
        // Usuarios
        usuario1 = new Usuarios();
        usuario1.setId(1);
        usuario1.setUsername("usuario1");

        usuario2 = new Usuarios();
        usuario2.setId(2);
        usuario2.setUsername("usuario2");

        // Colores
        colorRojo = new Colores();
        colorRojo.setId(1);
        colorRojo.setColor("Rojo");

        colorAzul = new Colores();
        colorAzul.setId(2);
        colorAzul.setColor("Azul");

        // Objetivos
        objetivo1 = new Objetivos();
        objetivo1.setId(1);

        objetivo2 = new Objetivos();
        objetivo2.setId(2);

        // Jugadores
        jugador1 = new Jugadores();
        jugador1.setId(1);
        jugador1.setUsuario(usuario1);
        jugador1.setColor(colorRojo);
        jugador1.setObjetivo(objetivo1);

        jugador2 = new Jugadores();
        jugador2.setId(2);
        jugador2.setUsuario(usuario2);
        jugador2.setColor(colorAzul);
        jugador2.setObjetivo(objetivo2);

        jugador3 = new Jugadores();
        jugador3.setId(3);
        jugador3.setUsuario(usuario1);
        jugador3.setColor(colorRojo);
        jugador3.setObjetivo(objetivo1);

        // Partidas
        partida1 = new Partidas();
        partida1.setId(1);
        partida1.setUsuario(usuario1);
        partida1.setJugadorGanador(jugador1); // Usuario1 gana

        partida2 = new Partidas();
        partida2.setId(2);
        partida2.setUsuario(usuario1);
        partida2.setJugadorGanador(jugador2); // Usuario1 pierde

        partida3 = new Partidas();
        partida3.setId(3);
        partida3.setUsuario(usuario2);
        partida3.setJugadorGanador(jugador2); // Usuario2 gana
    }

    @Test
    void partidasPorUsuario_deberiaRetornarPartidasJugadasYGanadas() {

        List<Partidas> partidas = Arrays.asList(partida1, partida2, partida3);
        when(partidasRepository.findAll()).thenReturn(partidas);


        Map<String, Object> resultado = estadisticasService.partidasPorUsuario(1);


        assertEquals(2L, resultado.get("jugadas")); // Usuario1 jugó 2 partidas
        assertEquals(1L, resultado.get("ganadas")); // Usuario1 ganó 1 partida
        verify(partidasRepository, times(2)).findAll();
    }

    @Test
    void partidasPorUsuario_sinPartidas_deberiaRetornarCeros() {

        when(partidasRepository.findAll()).thenReturn(Collections.emptyList());


        Map<String, Object> resultado = estadisticasService.partidasPorUsuario(1);


        assertEquals(0L, resultado.get("jugadas"));
        assertEquals(0L, resultado.get("ganadas"));
    }

    @Test
    void partidasPorUsuario_usuarioInexistente_deberiaRetornarCeros() {

        List<Partidas> partidas = Arrays.asList(partida1, partida2, partida3);
        when(partidasRepository.findAll()).thenReturn(partidas);

        Map<String, Object> resultado = estadisticasService.partidasPorUsuario(999);


        assertEquals(0L, resultado.get("jugadas"));
        assertEquals(0L, resultado.get("ganadas"));
    }

    @Test
    void porcentajeVictorias_conVictorias_deberiaCalcularPorcentajeCorrectamente() {

        List<Partidas> partidas = Arrays.asList(partida1, partida2, partida3);
        when(partidasRepository.findAll()).thenReturn(partidas);


        Double porcentaje = estadisticasService.porcentajeVictorias(1);


        assertEquals(50.0, porcentaje); // 1 ganada de 2 jugadas = 50%
    }

    @Test
    void porcentajeVictorias_sinPartidas_deberiaRetornarCero() {

        when(partidasRepository.findAll()).thenReturn(Collections.emptyList());


        Double porcentaje = estadisticasService.porcentajeVictorias(1);


        assertEquals(0.0, porcentaje);
    }

    @Test
    void porcentajeVictorias_todasGanadas_deberiaRetornar100() {

        partida2.setJugadorGanador(jugador1); // Cambiar para que usuario1 gane ambas
        List<Partidas> partidas = Arrays.asList(partida1, partida2);
        when(partidasRepository.findAll()).thenReturn(partidas);


        Double porcentaje = estadisticasService.porcentajeVictorias(1);


        assertEquals(100.0, porcentaje);
    }

    @Test
    void porcentajeVictorias_todasPerdidas_deberiaRetornarCero() {

        partida1.setJugadorGanador(jugador2); // Cambiar para que usuario1 pierda ambas
        List<Partidas> partidas = Arrays.asList(partida1, partida2);
        when(partidasRepository.findAll()).thenReturn(partidas);


        Double porcentaje = estadisticasService.porcentajeVictorias(1);


        assertEquals(0.0, porcentaje);
    }

    @Test
    void coloresMasUsados_deberiaContarColoresCorrectamente() {

        List<Jugadores> jugadores = Arrays.asList(jugador1, jugador2, jugador3);
        when(jugadoresRepository.findAll()).thenReturn(jugadores);


        Map<String, Long> colores = estadisticasService.coloresMasUsados();


        assertEquals(2L, colores.get("Rojo")); // 2 jugadores usan rojo
        assertEquals(1L, colores.get("Azul")); // 1 jugador usa azul
        verify(jugadoresRepository).findAll();
    }

    @Test
    void coloresMasUsados_sinJugadores_deberiaRetornarMapaVacio() {

        when(jugadoresRepository.findAll()).thenReturn(Collections.emptyList());


        Map<String, Long> colores = estadisticasService.coloresMasUsados();


        assertTrue(colores.isEmpty());
    }

    @Test
    void coloresMasUsados_jugadoresSinColor_noDeberiaContarlos() {

        Jugadores jugadorSinColor = new Jugadores();
        jugadorSinColor.setId(4);
        jugadorSinColor.setColor(null);

        List<Jugadores> jugadores = Arrays.asList(jugador1, jugadorSinColor);
        when(jugadoresRepository.findAll()).thenReturn(jugadores);


        Map<String, Long> colores = estadisticasService.coloresMasUsados();


        assertEquals(1L, colores.get("Rojo"));
        assertNull(colores.get(null));
    }

    @Test
    void objetivosCumplidos_deberiaContarObjetivosCorrectamente() {

        List<Partidas> partidas = Arrays.asList(partida1, partida2, partida3);
        when(partidasRepository.findAll()).thenReturn(partidas);
        when(objetivosRepository.findById(1L)).thenReturn(Optional.of(objetivo1));
        when(objetivosRepository.findById(2L)).thenReturn(Optional.of(objetivo2));


        Map<String, Long> objetivos = estadisticasService.objetivosCumplidos();


        assertEquals(1L, objetivos.get("Objetivo 1")); // Objetivo1 cumplido 1 vez
        assertEquals(2L, objetivos.get("Objetivo 2")); // Objetivo2 cumplido 2 veces
        verify(partidasRepository).findAll();
        verify(objetivosRepository).findById(1L);
        verify(objetivosRepository).findById(2L);
    }

    @Test
    void objetivosCumplidos_sinPartidas_deberiaRetornarMapaVacio() {

        when(partidasRepository.findAll()).thenReturn(Collections.emptyList());


        Map<String, Long> objetivos = estadisticasService.objetivosCumplidos();


        assertTrue(objetivos.isEmpty());
    }

    @Test
    void objetivosCumplidos_partidasSinGanador_noDeberiaContarlas() {

        Partidas partidaSinGanador = new Partidas();
        partidaSinGanador.setId(4);
        partidaSinGanador.setJugadorGanador(null);

        List<Partidas> partidas = Arrays.asList(partida1, partidaSinGanador);
        when(partidasRepository.findAll()).thenReturn(partidas);
        when(objetivosRepository.findById(1L)).thenReturn(Optional.of(objetivo1));


        Map<String, Long> objetivos = estadisticasService.objetivosCumplidos();


        assertEquals(1L, objetivos.get("Objetivo 1")); // Solo cuenta partida1
        assertEquals(1, objetivos.size()); // Solo un objetivo
    }

    @Test
    void objetivosCumplidos_objetivoNoEncontrado_deberiaUsarNombrePorDefecto() {

        List<Partidas> partidas = Arrays.asList(partida1);
        when(partidasRepository.findAll()).thenReturn(partidas);
        when(objetivosRepository.findById(1L)).thenReturn(Optional.empty());


        Map<String, Long> objetivos = estadisticasService.objetivosCumplidos();


        assertEquals(1L, objetivos.get("Objetivo 1")); // Usa nombre por defecto
    }

    @Test
    void objetivosCumplidos_jugadorGanadorSinObjetivo_noDeberiaContarlo() {

        jugador1.setObjetivo(null); // Jugador sin objetivo
        List<Partidas> partidas = Arrays.asList(partida1);
        when(partidasRepository.findAll()).thenReturn(partidas);


        Map<String, Long> objetivos = estadisticasService.objetivosCumplidos();


        assertTrue(objetivos.isEmpty());
    }
}