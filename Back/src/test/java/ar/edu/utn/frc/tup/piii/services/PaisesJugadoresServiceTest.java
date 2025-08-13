package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.dtos.common.FronteraJugadorDto;
import ar.edu.utn.frc.tup.piii.dtos.common.PaisJugadorDto;
import ar.edu.utn.frc.tup.piii.entities.Jugadores;
import ar.edu.utn.frc.tup.piii.entities.Paises;
import ar.edu.utn.frc.tup.piii.entities.PaisesJugadores;
import ar.edu.utn.frc.tup.piii.entities.Partidas;
import ar.edu.utn.frc.tup.piii.models.PaisJugadorModel;
import ar.edu.utn.frc.tup.piii.models.PaisModel;
import ar.edu.utn.frc.tup.piii.repositories.PaisesJugadoresRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaisesJugadoresServiceTest {
    @Mock
    private PaisesJugadoresRepository paisesJugadoresRepository;
    @Mock
    private PaisesService paisesService;
    @Mock
    private FronterasService fronterasService;

    @InjectMocks
    private PaisesJugadoresService paisesJugadoresService;

    @Test
    void asignarPaisesAJugadores() {
        // ── datos de entrada
        Partidas partida = new Partidas();
        partida.setId(1);

        Jugadores j1 = new Jugadores(); j1.setId(1); j1.setNombre("Ana");
        Jugadores j2 = new Jugadores(); j2.setId(2); j2.setNombre("Benja");
        List<Jugadores> jugadores = Arrays.asList(j1, j2);

        Paises p1 = new Paises(); p1.setId(10); p1.setNombre("Chile");
        Paises p2 = new Paises(); p2.setId(11); p2.setNombre("Paraguay");
        Paises p3 = new Paises(); p3.setId(12); p3.setNombre("Bolivia");
        List<Paises> paises = Arrays.asList(p1, p2, p3);

        // ── llamada
        paisesJugadoresService.asignarPaisesAJugadores(paises, jugadores, partida);

        // ── verificación
        ArgumentCaptor<PaisesJugadores> captor = ArgumentCaptor.forClass(PaisesJugadores.class);
        verify(paisesJugadoresRepository, times(paises.size())).save(captor.capture());

        List<PaisesJugadores> guardados = captor.getAllValues();
        // se guardan exactamente todos los países
        Assertions.assertEquals(3, guardados.size());
        Assertions.assertTrue(
                guardados.stream().map(PaisesJugadores::getPais).allMatch(paises::contains),
                "Todos los países deben quedar asociados"
        );
        // se asignan fichas iniciales = 1
        Assertions.assertTrue(
                guardados.stream().allMatch(pg -> pg.getFichas() == 1),
                "Todas las asignaciones comienzan con 1 ficha"
        );
        // se reparte de forma circular (no importa el orden concreto, sí que todos tengan dueño)
        Assertions.assertTrue(
                guardados.stream().map(PaisesJugadores::getJugador).allMatch(jugadores::contains),
                "Todos los jugadores reciben países"
        );
    }

    @Test
    void getPaisesFromJugadorId() {
        Jugadores jugador = new Jugadores(); jugador.setId(5);

        Paises pais = new Paises(); pais.setId(99); pais.setNombre("México");

        Partidas partida = new Partidas(); partida.setId(1); // <-- ¡Agregado!

        PaisesJugadores pj = new PaisesJugadores();
        pj.setId(777);
        pj.setFichas(3);
        pj.setPais(pais);
        pj.setJugador(jugador);
        pj.setPartida(partida); // <-- ¡Clave!

        when(paisesJugadoresRepository.getAllByJugador(jugador))
                .thenReturn(Collections.singletonList(pj));

        List<PaisJugadorModel> resultado = paisesJugadoresService.getPaisesFromJugadorId(jugador);

        Assertions.assertEquals(1, resultado.size());
        Assertions.assertEquals(99, resultado.get(0).getIdPais());
        Assertions.assertEquals(pj.getPartida().getId(), resultado.get(0).getIdPartida()); // opcional
    }

    @Test
    void buscarFromJugadorId_devuelveNullCuandoNoExiste() {
        Jugadores jugador = new Jugadores(); jugador.setId(2);
        when(paisesJugadoresRepository.getAllByJugador(jugador))
                .thenReturn(Collections.emptyList());

        PaisesJugadores resultado = paisesJugadoresService.buscarFromJugadorId(jugador, 999);

        Assertions.assertNull(resultado);
    }

    @Test
    void buscarFromJugadorId_devuelvePaisCuandoExiste() {
        Jugadores jugador = new Jugadores(); jugador.setId(1);

        Paises paisOk = new Paises(); paisOk.setId(8);
        PaisesJugadores pjOk = new PaisesJugadores(); pjOk.setPais(paisOk);

        when(paisesJugadoresRepository.getAllByJugador(jugador))
                .thenReturn(List.of(pjOk));

        PaisesJugadores encontrado = paisesJugadoresService.buscarFromJugadorId(jugador, 8);

        Assertions.assertNotNull(encontrado);
        Assertions.assertEquals(paisOk.getId(), encontrado.getPais().getId());
    }

    @Test
    void esPropio() {
        PaisJugadorModel modelo = new PaisJugadorModel();
        modelo.setId(42);
        List<PaisJugadorModel> lista = Collections.singletonList(modelo);

        Assertions.assertTrue(paisesJugadoresService.esPropio(lista, 42));
        Assertions.assertFalse(paisesJugadoresService.esPropio(lista, 99));
    }

    @Test
    void getIdsPaisesByPlayer() {
        int idJugador = 3;

        PaisesJugadores pj1 = new PaisesJugadores(); pj1.setId(101);
        PaisesJugadores pj2 = new PaisesJugadores(); pj2.setId(102);

        when(paisesJugadoresRepository.getAllByJugadorId(idJugador))
                .thenReturn(List.of(pj1, pj2));

        List<Integer> ids = paisesJugadoresService.getIdsPaisesByPlayer(idJugador);

        Assertions.assertEquals(List.of(101, 102), ids);
        verify(paisesJugadoresRepository).getAllByJugadorId(idJugador);
    }

    @Test
    void getPaisJugadorByIdPartida_Pais() {
        int idPartida = 1;
        int idPais = 50;

        // ── pais y dueño principal
        PaisModel paisModel = new PaisModel(); paisModel.setId(idPais); paisModel.setNombre("Italia");

        Jugadores dueñoPrincipal = new Jugadores(); dueñoPrincipal.setId(88); dueñoPrincipal.setNombre("Carla");
        Paises paisEntidad = new Paises(); paisEntidad.setId(idPais); paisEntidad.setNombre("Italia");

        // Mock del color del jugador
        ar.edu.utn.frc.tup.piii.entities.Colores color = mock(ar.edu.utn.frc.tup.piii.entities.Colores.class);
        when(color.getColor()).thenReturn("ROJO");
        dueñoPrincipal.setColor(color);

        PaisesJugadores pjPrincipal = new PaisesJugadores();
        pjPrincipal.setPais(paisEntidad);
        pjPrincipal.setJugador(dueñoPrincipal);
        pjPrincipal.setFichas(5);

        // ── fronteras (p.ej. Francia y Suiza)
        int frontera1 = 51, frontera2 = 52;
        when(fronterasService.findAllByPais1_IdOrPais2_Id(idPais))
                .thenReturn(List.of(frontera1, frontera2));

        PaisModel paisF1 = new PaisModel(); paisF1.setId(frontera1); paisF1.setNombre("Francia");
        PaisModel paisF2 = new PaisModel(); paisF2.setId(frontera2); paisF2.setNombre("Suiza");

        when(paisesService.getPaisById(idPais)).thenReturn(paisModel);
        when(paisesService.getPaisById(frontera1)).thenReturn(paisF1);
        when(paisesService.getPaisById(frontera2)).thenReturn(paisF2);

        // Mock para los dueños de las fronteras
        PaisesJugadores pjF1 = new PaisesJugadores();
        pjF1.setPais(new Paises()); pjF1.getPais().setId(frontera1);
        pjF1.setJugador(dueñoPrincipal); pjF1.setFichas(2);

        PaisesJugadores pjF2 = new PaisesJugadores();
        pjF2.setPais(new Paises()); pjF2.getPais().setId(frontera2);
        pjF2.setJugador(dueñoPrincipal); pjF2.setFichas(1);

        when(paisesJugadoresRepository.findByPartida_IdAndPais_Id(idPartida, idPais))
                .thenReturn(pjPrincipal);
        when(paisesJugadoresRepository.findByPartida_IdAndPais_Id(idPartida, frontera1))
                .thenReturn(pjF1);
        when(paisesJugadoresRepository.findByPartida_IdAndPais_Id(idPartida, frontera2))
                .thenReturn(pjF2);

        // ── llamada
        PaisJugadorDto dto = paisesJugadoresService.getPaisJugadorByIdPartida_Pais(idPartida, idPais);

        // ── aserciones mínimas
        Assertions.assertEquals("Italia", dto.getNombrePaisJugador());
        Assertions.assertEquals(5, dto.getFichas());
        Assertions.assertEquals("Carla", dto.getDuenio());

        // fronteras correctas
        Assertions.assertEquals(2, dto.getFronteras().size());
        Optional<FronteraJugadorDto> francia =
                dto.getFronteras().stream().filter(f -> "Francia".equals(f.getNombreFrontera())).findFirst();
        Assertions.assertTrue(francia.isPresent() && francia.get().getFichas() == 2);
    }

    @Test
    void save() {
        PaisesJugadores pj = new PaisesJugadores();
        paisesJugadoresService.save(pj);
        verify(paisesJugadoresRepository).save(pj);
    }

    @Test
    void getPaisesFromPartidaId() {
        Partidas partida = new Partidas(); partida.setId(99);

        Jugadores jugador = new Jugadores();
        jugador.setId(7);
        jugador.setNombre("Jugador de prueba");

        Paises pais = new Paises();
        pais.setId(55);
        pais.setNombre("Chile");

        PaisesJugadores pj = new PaisesJugadores();
        pj.setId(123);
        pj.setPartida(partida);
        pj.setJugador(jugador);
        pj.setPais(pais);

        when(paisesJugadoresRepository.getAllByPartida(partida))
                .thenReturn(Collections.singletonList(pj));

        List<PaisJugadorModel> modelos = paisesJugadoresService.getPaisesFromPartidaId(partida);

        Assertions.assertEquals(1, modelos.size());
        Assertions.assertEquals(pj.getId(), modelos.get(0).getId());
        Assertions.assertEquals(pj.getPais().getId(), modelos.get(0).getIdPais());

        verify(paisesJugadoresRepository).getAllByPartida(partida);
    }

    @Test
    void getPaisesFromContinenteId() {
        int idContinente = 4;

        PaisesJugadores pj = getPaisesJugadores(idContinente);

        when(paisesJugadoresRepository.getAllByPais_Continente_Id(idContinente))
                .thenReturn(List.of(pj));

        List<PaisJugadorModel> res =
                paisesJugadoresService.getPaisesFromContinenteId(idContinente);

        Assertions.assertEquals(1, res.size());
        Assertions.assertEquals(pj.getId(), res.get(0).getId());
        Assertions.assertEquals(pj.getPais().getId(), res.get(0).getIdPais());
        verify(paisesJugadoresRepository).getAllByPais_Continente_Id(idContinente);
    }

    private PaisesJugadores getPaisesJugadores(int idContinente) {
        Jugadores jugador = new Jugadores();
        jugador.setId(50);
        jugador.setNombre("Mock");
        Partidas partida = new Partidas();
        partida.setId(99);

        Paises pais = new Paises();
        pais.setId(70);
        pais.setNombre("Perú");
        ar.edu.utn.frc.tup.piii.entities.Continentes cont = new ar.edu.utn.frc.tup.piii.entities.Continentes();
        cont.setId(idContinente);
        pais.setContinente(cont);

        PaisesJugadores pj = new PaisesJugadores();
        pj.setId(555);
        pj.setFichas(2);
        pj.setPais(pais);
        pj.setJugador(jugador);
        pj.setPartida(partida);
        return pj;
    }

    @Test
    void getPaisesFromJugadorAndFichas_filtraSegunCantidad() {
        int idJugador = 6;
        int minimo = 2;

        Jugadores jugador = new Jugadores(); jugador.setId(idJugador);
        Partidas partida = new Partidas(); partida.setId(1);

        Paises pais1 = new Paises(); pais1.setId(10); pais1.setNombre("Pais‑1");
        Paises pais2 = new Paises(); pais2.setId(11); pais2.setNombre("Pais‑2");

        PaisesJugadores pj1 = new PaisesJugadores();
        pj1.setId(1); pj1.setFichas(3); pj1.setJugador(jugador); pj1.setPais(pais1); pj1.setPartida(partida);

        PaisesJugadores pj2 = new PaisesJugadores();
        pj2.setId(2); pj2.setFichas(4); pj2.setJugador(jugador); pj2.setPais(pais2); pj2.setPartida(partida);

        when(paisesJugadoresRepository.getAllByJugador_IdAndFichasGreaterThan(idJugador, minimo))
                .thenReturn(List.of(pj1, pj2));

        List<PaisJugadorModel> res =
                paisesJugadoresService.getPaisesFromJugadorAndFichas(idJugador, minimo);

        Assertions.assertEquals(2, res.size());
        Assertions.assertEquals(List.of(1, 2),
                res.stream().map(PaisJugadorModel::getId).toList());
        verify(paisesJugadoresRepository)
                .getAllByJugador_IdAndFichasGreaterThan(idJugador, minimo);
    }

    @Test
    void cambiarFichas_actualizaYGuardaSiExiste() {
        Long idPJ = 99L;
        PaisesJugadores pj = new PaisesJugadores(); pj.setFichas(1);

        when(paisesJugadoresRepository.findById(idPJ.intValue()))
                .thenReturn(Optional.of(pj));

        paisesJugadoresService.cambiarFichas(10, idPJ);

        Assertions.assertEquals(10, pj.getFichas());
        verify(paisesJugadoresRepository).save(pj);
    }

    @Test
    void cambiarFichas_noHaceNadaSiNoExiste() {
        when(paisesJugadoresRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        paisesJugadoresService.cambiarFichas(7, 123L);

        verify(paisesJugadoresRepository, never()).save(any());
    }

    @Test
    void cambiarDuenio_actualizaYGuardaSiExiste() {
        Long idPJ = 88L;
        Jugadores nuevo = new Jugadores(); nuevo.setId(2);

        PaisesJugadores pj = new PaisesJugadores();
        pj.setJugador(new Jugadores());

        when(paisesJugadoresRepository.findById(idPJ.intValue()))
                .thenReturn(Optional.of(pj));

        paisesJugadoresService.cambiarDuenio(nuevo, idPJ);

        Assertions.assertEquals(nuevo, pj.getJugador());
        verify(paisesJugadoresRepository).save(pj);
    }

    @Test
    void cambiarDuenio_noHaceNadaSiNoExiste() {
        when(paisesJugadoresRepository.findById(anyInt()))
                .thenReturn(Optional.empty());

        paisesJugadoresService.cambiarDuenio(new Jugadores(), 777L);

        verify(paisesJugadoresRepository, never()).save(any());
    }
}