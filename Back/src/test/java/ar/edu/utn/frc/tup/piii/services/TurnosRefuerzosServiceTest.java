package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.dtos.common.FichasDTO;
import ar.edu.utn.frc.tup.piii.dtos.common.RefuerzoPostDTO;
import ar.edu.utn.frc.tup.piii.entities.*;
import ar.edu.utn.frc.tup.piii.enums.Estado;
import ar.edu.utn.frc.tup.piii.models.JugadorModel;
import ar.edu.utn.frc.tup.piii.models.PaisJugadorModel;
import ar.edu.utn.frc.tup.piii.models.TurnoRefuerzoModel;
import ar.edu.utn.frc.tup.piii.repositories.TurnosRefuerzosRepository;
import ar.edu.utn.frc.tup.piii.services.interfaces.ICanjesService;
import ar.edu.utn.frc.tup.piii.services.interfaces.IJugadoresService;
import ar.edu.utn.frc.tup.piii.services.interfaces.IPaisesJugadoresService;
import ar.edu.utn.frc.tup.piii.services.interfaces.IRefuerzosService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TurnosRefuerzosServiceTest {

    @Mock
    TurnosRefuerzosRepository turnosRefuerzosRepository;
    @Mock
    IRefuerzosService refuerzosService;
    @Mock
    ICanjesService canjesService;
    @Mock
    IJugadoresService jugadoresService;
    @Mock
    IPaisesJugadoresService paisesJugadoresService;

    @InjectMocks
    TurnosRefuerzosService turnosRefuerzosService;

    @Test
    void crearTurnoRefuerzo() {
        Rondas ronda = new Rondas();
        ronda.setId(1);
        JugadorModel jugadorModel = new JugadorModel();
        jugadorModel.setId(10);

        Jugadores jugadorEntity = new Jugadores();
        jugadorEntity.setId(10);

        when(jugadoresService.getJugadorByIdEntity(10L)).thenReturn(jugadorEntity);

        turnosRefuerzosService.crearTurnoRefuerzo(ronda, jugadorModel);

        verify(turnosRefuerzosRepository).save(argThat(turno ->
                turno.getEstado().equals("SIN_JUGAR") &&
                        turno.getRonda() == ronda &&
                        turno.getJugador() == jugadorEntity
        ));
    }

    @Test
    void obtenerTurnoRefuerzo() {
        TurnosRefuerzos entity = new TurnosRefuerzos();
        entity.setId(1);
        entity.setEstado("EN_CURSO");
        Jugadores jugador = new Jugadores();
        jugador.setId(2);
        entity.setJugador(jugador);

        when(turnosRefuerzosRepository.getReferenceById(1L)).thenReturn(entity);
        when(refuerzosService.getAllByTurnoRef(entity)).thenReturn(Collections.emptyList());
        when(canjesService.getAllByTurnoRefuerzo(entity)).thenReturn(Collections.emptyList());

        TurnoRefuerzoModel model = turnosRefuerzosService.obtenerTurnoRefuerzo(1L);

        assertNotNull(model);
        assertEquals(1, model.getId());
        assertEquals(2, model.getIdJugador());
        assertEquals(Estado.EN_CURSO, model.getEstado());
    }

    @Test
    void mapearTurnoRefuerzo() {
        TurnosRefuerzos entity = new TurnosRefuerzos();
        entity.setId(1);
        entity.setEstado(Estado.SIN_JUGAR.name());

        Jugadores jugador = new Jugadores();
        jugador.setId(10);
        entity.setJugador(jugador);

        Rondas ronda = new Rondas();
        ronda.setId(1);
        entity.setRonda(ronda);

        // Refuerzo con paisJugador y partida no nulos
        Refuerzos ref = new Refuerzos();
        ref.setId(1);
        ref.setTurnoRef(entity);

        PaisesJugadores paisJugador = new PaisesJugadores();
        paisJugador.setId(100);
        Partidas partida = new Partidas();
        partida.setId(200);
        paisJugador.setPartida(partida);
        paisJugador.setFichas(5);
        paisJugador.setJugador(jugador);
        Paises pais = new Paises();
        pais.setId(300);
        paisJugador.setPais(pais);
        ref.setPaisJugador(paisJugador);
        ref.setCantidad(3);
        ref.setTipoFichas("DE_PAISES");

        when(refuerzosService.getAllByTurnoRef(entity)).thenReturn(List.of(ref));
        when(canjesService.getAllByTurnoRefuerzo(entity)).thenReturn(Collections.emptyList());

        TurnoRefuerzoModel model = turnosRefuerzosService.mapearTurnoRefuerzo(entity);

        assertNotNull(model);
        assertEquals(1, model.getId());
        assertEquals(Estado.SIN_JUGAR, model.getEstado());
        assertEquals(1, model.getRefuerzoModels().size());
        assertEquals(3, model.getRefuerzoModels().get(0).getCantidad());
    }

    @Test
    void buscarTurnosByRonda() {
        // Test de ejemplo, no modificado
    }

    @Test
    void obtenerFichas_primeraRonda() {
        Rondas ronda = new Rondas();
        ronda.setId(1);
        ronda.setNumero(1);

        TurnosRefuerzos entity = new TurnosRefuerzos();
        entity.setId(1);
        entity.setRonda(ronda);
        entity.setEstado(Estado.SIN_JUGAR.name()); // CORRECCIÓN: setear estado

        Jugadores jugador = new Jugadores();
        jugador.setId(10);
        entity.setJugador(jugador);

        when(turnosRefuerzosRepository.getReferenceById(1L)).thenReturn(entity);
        when(turnosRefuerzosRepository.getAllByRondaOrderByIdAsc(ronda)).thenReturn(List.of(entity));
        when(refuerzosService.getAllByTurnoRef(any())).thenReturn(Collections.emptyList());
        when(canjesService.getAllByTurnoRefuerzo(any())).thenReturn(Collections.emptyList());

        FichasDTO fichas = turnosRefuerzosService.obtenerFichas(1L);

        assertEquals(3, fichas.getFichasPais());
    }

    @Test
    void guardarRefuerzos() {
        // Test de ejemplo, no modificado
    }

    @Test
    void terminarTurnoRefuerzo() {
        TurnosRefuerzos entity = new TurnosRefuerzos();
        entity.setId(1);
        entity.setEstado("SIN_JUGAR");
        when(turnosRefuerzosRepository.findById(1L)).thenReturn(Optional.of(entity));

        turnosRefuerzosService.terminarTurnoRefuerzo(1L);

        assertEquals("FINALIZADO", entity.getEstado());
        verify(turnosRefuerzosRepository).save(entity);
    }

    @Test
    void obtenerTurnoRefuerzo_devuelveNullSiIdNull() {
        TurnoRefuerzoModel model = turnosRefuerzosService.obtenerTurnoRefuerzo(null);
        assertNull(model);
        verifyNoInteractions(turnosRefuerzosRepository, refuerzosService, canjesService);
    }

    @Test
    void obtenerFichas_noPrimeraRonda() {
        // ── turno que pertenece a la 2.ª ronda
        Rondas ronda = new Rondas();
        ronda.setId(2);
        ronda.setNumero(2);

        TurnosRefuerzos turno = new TurnosRefuerzos();
        turno.setId(22);
        turno.setRonda(ronda);

        Jugadores jugadorEntity = new Jugadores(); jugadorEntity.setId(99);
        turno.setJugador(jugadorEntity);

        when(turnosRefuerzosRepository.getReferenceById(22L)).thenReturn(turno);

        // jugador con 5 países  →  5 / 2 = 2 fichas
        JugadorModel jugadorModel = new JugadorModel(); jugadorModel.setId(99);
        List<PaisJugadorModel> cinco = IntStream.range(0, 5)
                .mapToObj(i -> { var p = new PaisJugadorModel(); p.setId(i); return p; })
                .toList();
        jugadorModel.setPaises(cinco);
        when(jugadoresService.buscarJugador(99L)).thenReturn(jugadorModel);

        FichasDTO dto = turnosRefuerzosService.obtenerFichas(22L);

        assertEquals(2, dto.getFichasPais());
        verify(turnosRefuerzosRepository).getReferenceById(22L);
        verify(jugadoresService).buscarJugador(99L);
        verifyNoMoreInteractions(refuerzosService, canjesService); // no se usan en esta rama
    }

    @Test
    void guardarRefuerzos_actualizaPaisYGuarda() {
        // ── turno existente
        TurnosRefuerzos turno = new TurnosRefuerzos();
        Jugadores owner = new Jugadores(); owner.setId(1);
        turno.setId(50); turno.setJugador(owner);
        when(turnosRefuerzosRepository.findById(50L)).thenReturn(Optional.of(turno));

        // ── país del jugador antes de sumar fichas
        PaisesJugadores pais = new PaisesJugadores();
        pais.setId(100); pais.setJugador(owner); pais.setFichas(4);
        when(paisesJugadoresService.buscarFromJugadorId(owner, 100))
                .thenReturn(pais);

        // ── refuerzo entrante
        RefuerzoPostDTO dto = new RefuerzoPostDTO();
        dto.setIdTurnoRef(50);
        dto.setIdPais(100);
        dto.setCantidad(3);
        dto.setTipoFicha("DE_PAISES");

        turnosRefuerzosService.guardarRefuerzos(List.of(dto));

        // ── verificaciones
        ArgumentCaptor<Refuerzos> captor = ArgumentCaptor.forClass(Refuerzos.class);
        verify(refuerzosService).saveOne(captor.capture());
        Refuerzos guardado = captor.getValue();
        assertEquals(dto.getCantidad(), guardado.getCantidad());
        assertEquals(pais, guardado.getPaisJugador());
        assertEquals(turno, guardado.getTurnoRef());
        assertEquals("DE_PAISES", guardado.getTipoFichas());

        // fichas del país actualizadas
        assertEquals(7, pais.getFichas());
        verify(paisesJugadoresService).save(pais);
    }

    @Test
    void buscarTurnosByRonda_devuelveModelados() {
        Rondas ronda = new Rondas(); ronda.setId(3); ronda.setNumero(1);

        TurnosRefuerzos t1 = new TurnosRefuerzos();
        t1.setId(1);
        t1.setRonda(ronda);
        t1.setEstado(Estado.SIN_JUGAR.name());
        Jugadores j1 = new Jugadores(); j1.setId(10); t1.setJugador(j1);

        TurnosRefuerzos t2 = new TurnosRefuerzos();
        t2.setId(2);
        t2.setRonda(ronda);
        t2.setEstado(Estado.SIN_JUGAR.name());
        Jugadores j2 = new Jugadores(); j2.setId(11); t2.setJugador(j2);

        when(turnosRefuerzosRepository.getAllByRondaOrderByIdAsc(ronda))
                .thenReturn(List.of(t1, t2));
        when(refuerzosService.getAllByTurnoRef(any()))
                .thenReturn(Collections.emptyList());
        when(canjesService.getAllByTurnoRefuerzo(any()))
                .thenReturn(Collections.emptyList());

        List<TurnoRefuerzoModel> lista = turnosRefuerzosService.buscarTurnosByRonda(ronda);

        assertEquals(2, lista.size());
        assertIterableEquals(List.of(1, 2), lista.stream().map(TurnoRefuerzoModel::getId).toList());
    }

    @Test
    void terminarTurnoRefuerzo_lanzaExcepcionSiNoExiste() {
        when(turnosRefuerzosRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,
                () -> turnosRefuerzosService.terminarTurnoRefuerzo(999L));
    }
}