package ar.edu.utn.frc.tup.piii.services;
import ar.edu.utn.frc.tup.piii.entities.*;
import ar.edu.utn.frc.tup.piii.models.PaisJugadorModel;
import ar.edu.utn.frc.tup.piii.repositories.CombatesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CombatesServiceTest {

    @Mock
    private CombatesRepository combatesRepository;

    @Mock
    private PaisesJugadoresService paisesJugadoresService;

    @Mock
    private PaisesService paisesService;

    @Mock
    private JugadoresService jugadoresService;

    @InjectMocks
    private CombatesService combatesService;

    private PaisJugadorModel paisAtacante;
    private PaisJugadorModel paisDefensor;
    private Paises paisAtk;
    private Paises paisDef;
    private Jugadores jugadorAtk;
    private Jugadores jugadorDef;
    private Combates combateGuardado;

    @BeforeEach
    void setUp() {
        // Configurar país atacante
        paisAtacante = new PaisJugadorModel();
        paisAtacante.setId(1);
        paisAtacante.setIdPais(1);
        paisAtacante.setIdJugador(1);
        paisAtacante.setFichas(5);

        // Configurar país defensor
        paisDefensor = new PaisJugadorModel();
        paisDefensor.setId(2);
        paisDefensor.setIdPais(2);
        paisDefensor.setIdJugador(2);
        paisDefensor.setFichas(3);

        // Configurar entidades
        paisAtk = new Paises();
        paisAtk.setId(1);

        paisDef = new Paises();
        paisDef.setId(2);

        jugadorAtk = new Jugadores();
        jugadorAtk.setId(1);

        jugadorDef = new Jugadores();
        jugadorDef.setId(2);

        combateGuardado = new Combates();
        combateGuardado.setId(1);
    }

    @Test
    void hacerCombate_deberiaCrearCombateCompleto() {

        when(paisesService.getPaisByIdEntity(1L)).thenReturn(Optional.of(paisAtk));
        when(paisesService.getPaisByIdEntity(2L)).thenReturn(Optional.of(paisDef));
        when(paisesJugadoresService.getPaisJugadorByPaisId(1, 1)).thenReturn(paisAtacante);
        when(paisesJugadoresService.getPaisJugadorByPaisId(2, 1)).thenReturn(paisDefensor);
        when(jugadoresService.getJugadorByIdEntity(1L)).thenReturn(jugadorAtk);
        when(jugadoresService.getJugadorByIdEntity(2L)).thenReturn(jugadorDef);
        when(combatesRepository.save(any(Combates.class))).thenReturn(combateGuardado);

        Combates resultado = combatesService.hacerCombate(1, 2, 1);


        assertNotNull(resultado);
        verify(paisesService).getPaisByIdEntity(1L);
        verify(paisesService).getPaisByIdEntity(2L);
        verify(paisesJugadoresService).getPaisJugadorByPaisId(1, 1);
        verify(paisesJugadoresService).getPaisJugadorByPaisId(2, 1);
        verify(paisesJugadoresService, times(2)).cambiarFichas(anyInt(), anyLong());
        verify(combatesRepository).save(any(Combates.class));
    }

    @Test
    void hacerCombate_cuandoDefensorQuedaEnCero_deberiaCambiarDuenio() {


        paisDefensor.setFichas(1); // Solo 1 ficha para que quede en 0
        when(paisesService.getPaisByIdEntity(1L)).thenReturn(Optional.of(paisAtk));
        when(paisesService.getPaisByIdEntity(2L)).thenReturn(Optional.of(paisDef));
        when(paisesJugadoresService.getPaisJugadorByPaisId(1, 1)).thenReturn(paisAtacante);
        when(paisesJugadoresService.getPaisJugadorByPaisId(2, 1)).thenReturn(paisDefensor);
        when(jugadoresService.getJugadorByIdEntity(1L)).thenReturn(jugadorAtk);
        when(jugadoresService.getJugadorByIdEntity(2L)).thenReturn(jugadorDef);
        when(combatesRepository.save(any(Combates.class))).thenReturn(combateGuardado);


        CombatesService spyCombatesService = spy(combatesService);
        doReturn(Arrays.asList(6, 0, 0)).when(spyCombatesService).tirarDadosAtacante(any());
        doReturn(Arrays.asList(1, 0, 0)).when(spyCombatesService).tirarDadosDefensor(any());


        spyCombatesService.hacerCombate(1, 2, 1);


        verify(paisesJugadoresService).cambiarDuenio(jugadorAtk, 2L);

    }

    @Test
    void tirarDadosAtacante_conMuchasFichas_deberiaLimitarA3Dados() {

        paisAtacante.setFichas(10);

        List<Integer> dados = combatesService.tirarDadosAtacante(paisAtacante);


        assertEquals(3, dados.size());
        // Verificar que están ordenados de mayor a menor
        assertTrue(dados.get(0) >= dados.get(1));
        assertTrue(dados.get(1) >= dados.get(2));
        // Verificar que los valores están en rango válido
        dados.forEach(dado -> {
            if (dado != 0) {
                assertTrue(dado >= 1 && dado <= 6);
            }
        });
    }

    @Test
    void tirarDadosAtacante_con2Fichas_deberiaTirar1Dado() {

        paisAtacante.setFichas(2); // Solo 2 fichas = 1 dado


        List<Integer> dados = combatesService.tirarDadosAtacante(paisAtacante);


        assertEquals(3, dados.size()); // Siempre devuelve lista de 3
        assertTrue(dados.get(0) >= 1 && dados.get(0) <= 6); // Primer dado válido
        assertEquals(0, dados.get(1)); // Segundo dado en 0
        assertEquals(0, dados.get(2)); // Tercer dado en 0
    }

    @Test
    void tirarDadosDefensor_conMuchasFichas_deberiaLimitarA3Dados() {

        paisDefensor.setFichas(10);


        List<Integer> dados = combatesService.tirarDadosDefensor(paisDefensor);

        // Assert
        assertEquals(3, dados.size());
        assertTrue(dados.get(0) >= dados.get(1));
        assertTrue(dados.get(1) >= dados.get(2));
        dados.forEach(dado -> {
            if (dado != 0) {
                assertTrue(dado >= 1 && dado <= 6);
            }
        });
    }

    @Test
    void tirarDadosDefensor_con1Ficha_deberiaTirar1Dado() {

        paisDefensor.setFichas(1);


        List<Integer> dados = combatesService.tirarDadosDefensor(paisDefensor);


        assertEquals(3, dados.size());
        assertTrue(dados.get(0) >= 1 && dados.get(0) <= 6);
        assertEquals(0, dados.get(1));
        assertEquals(0, dados.get(2));
    }

    @Test
    void tirarDado_deberiaRetornarValorEntre1Y6() {

        for (int i = 0; i < 100; i++) { // Probar múltiples veces
            Integer dado = combatesService.tirarDado();
            assertTrue(dado >= 1 && dado <= 6, "El dado debe estar entre 1 y 6, fue: " + dado);
        }
    }

    @Test
    void calcularFichasAtk_cuandoPierdeComparaciones_deberiaReducirFichas() {

        List<Integer> dadosAtacante = Arrays.asList(6, 5, 4); // Buenos dados
        List<Integer> dadosDefensor = Arrays.asList(6, 6, 5); // Mejores dados
        int fichasIniciales = 5;


        Integer fichasFinales = combatesService.calcularFichasAtk(dadosAtacante, dadosDefensor, fichasIniciales);


        assertTrue(fichasFinales < fichasIniciales, "Las fichas deberían haberse reducido");
        assertTrue(fichasFinales >= 1, "Las fichas no pueden ser menores a 1");
    }

    @Test
    void calcularFichasAtk_cuandoGanaTodasLasComparaciones_noDeberiaPerderFichas() {

        List<Integer> dadosAtacante = Arrays.asList(6, 6, 6);
        List<Integer> dadosDefensor = Arrays.asList(1, 1, 1);
        int fichasIniciales = 5;


        Integer fichasFinales = combatesService.calcularFichasAtk(dadosAtacante, dadosDefensor, fichasIniciales);


        assertEquals(fichasIniciales, fichasFinales, "No debería perder fichas al ganar todas las comparaciones");
    }

    @Test
    void calcularFichasDef_cuandoPierdeComparaciones_deberiaReducirFichas() {

        List<Integer> dadosAtacante = Arrays.asList(6, 6, 6);
        List<Integer> dadosDefensor = Arrays.asList(1, 1, 1);
        int fichasIniciales = 3;


        Integer fichasFinales = combatesService.calcularFichasDef(dadosAtacante, dadosDefensor, fichasIniciales);


        assertTrue(fichasFinales < fichasIniciales, "Las fichas deberían haberse reducido");
        assertTrue(fichasFinales >= 0, "Las fichas pueden llegar a 0");
    }

    @Test
    void calcularFichasDef_cuandoGanaTodasLasComparaciones_noDeberiaPerderFichas() {

        List<Integer> dadosAtacante = Arrays.asList(1, 1, 1);
        List<Integer> dadosDefensor = Arrays.asList(6, 6, 6);
        int fichasIniciales = 3;


        Integer fichasFinales = combatesService.calcularFichasDef(dadosAtacante, dadosDefensor, fichasIniciales);


        assertEquals(fichasIniciales, fichasFinales, "No debería perder fichas al ganar todas las comparaciones");
    }

    @Test
    void calcularFichasAtk_conDadosEnCero_noDeberiaCompararlos() {

        List<Integer> dadosAtacante = Arrays.asList(6, 0, 0); // Solo 1 dado
        List<Integer> dadosDefensor = Arrays.asList(1, 0, 0); // Solo 1 dado
        int fichasIniciales = 2;


        Integer fichasFinales = combatesService.calcularFichasAtk(dadosAtacante, dadosDefensor, fichasIniciales);


        assertEquals(fichasIniciales, fichasFinales, "Solo debería comparar dados no-cero");
    }

    @Test
    void calcularFichasDef_conDadosEnCero_noDeberiaCompararlos() {

        List<Integer> dadosAtacante = Arrays.asList(6, 0, 0);
        List<Integer> dadosDefensor = Arrays.asList(1, 0, 0);
        int fichasIniciales = 1;


        Integer fichasFinales = combatesService.calcularFichasDef(dadosAtacante, dadosDefensor, fichasIniciales);


        assertEquals(0, fichasFinales, "Debería perder la única comparación");
    }
}