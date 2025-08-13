package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.entities.Partidas;
import ar.edu.utn.frc.tup.piii.entities.Rondas;
import ar.edu.utn.frc.tup.piii.enums.Estado;
import ar.edu.utn.frc.tup.piii.enums.EstadoJugador;
import ar.edu.utn.frc.tup.piii.models.*;
import ar.edu.utn.frc.tup.piii.repositories.RondasRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RondasServiceTest {

    @Mock
    RondasRepository rondaRepository;
    @Mock
    TurnosAtaquesService turnosAtaquesService;
    @Mock
    TurnosRefuerzosService turnosRefuerzosService;

    @Spy
    @InjectMocks
    RondasService rondasService;
    @Test
    void crearRonda_PrimeraRonda() {
        Partidas partidaEntity = new Partidas();
        partidaEntity.setId(1);

        PartidaModel partidaModel = new PartidaModel();
        JugadorModel jugador = new JugadorModel();
        jugador.setId(1);
        jugador.setEstadoJugador(EstadoJugador.VIVO);
        partidaModel.setJugadores(List.of(jugador));

        when(rondaRepository.findTopByPartidaOrderByIdDesc(partidaEntity)).thenReturn(null);
        when(rondaRepository.save(any(Rondas.class))).thenAnswer(invocation -> {
            Rondas r = invocation.getArgument(0);
            r.setId(1);
            return r;
        });

        RondaModel result = rondasService.crearRonda(partidaModel, partidaEntity);

        assertNotNull(result);
        assertEquals(1, result.getNumero());
        verify(turnosRefuerzosService, atLeastOnce()).crearTurnoRefuerzo(any(), any());
        verify(turnosAtaquesService, atLeastOnce()).crearTurnoAtaque(any(), any());
        verify(rondaRepository).save(any(Rondas.class));
    }

    @Test
    void crearRonda_SegundaRonda() {
        Partidas partidaEntity = new Partidas();
        partidaEntity.setId(1);

        Rondas ultimaRonda = new Rondas();
        ultimaRonda.setNumero(1);

        PartidaModel partidaModel = new PartidaModel();
        JugadorModel jugador = new JugadorModel();
        jugador.setId(1);
        jugador.setEstadoJugador(EstadoJugador.VIVO);
        partidaModel.setJugadores(List.of(jugador));

        when(rondaRepository.findTopByPartidaOrderByIdDesc(partidaEntity)).thenReturn(ultimaRonda);
        when(rondaRepository.save(any(Rondas.class))).thenAnswer(invocation -> {
            Rondas r = invocation.getArgument(0);
            r.setId(2);
            return r;
        });

        RondaModel result = rondasService.crearRonda(partidaModel, partidaEntity);

        assertNotNull(result);
        assertEquals(2, result.getNumero());
        verify(turnosRefuerzosService, atLeastOnce()).crearTurnoRefuerzo(any(), any());
        verify(turnosAtaquesService, atLeastOnce()).crearTurnoAtaque(any(), any());
        verify(rondaRepository).save(any(Rondas.class));
    }

    @Test
    void getUltimaRondaPartida() {
        Partidas partida = new Partidas();
        Rondas ronda = new Rondas();
        when(rondaRepository.findTopByPartidaOrderByIdDesc(partida)).thenReturn(ronda);

        Rondas result = rondasService.getUltimaRondaPartida(partida);
        assertEquals(ronda, result);
        verify(rondaRepository).findTopByPartidaOrderByIdDesc(partida);
    }

    @Test
    void mapearRonda() {
        Rondas ronda = new Rondas();
        ronda.setNumero(1);
        ronda.setEstado(Estado.EN_CURSO.toString());

        Partidas partida = new Partidas();
        partida.setId(1);
        ronda.setPartida(partida);

        PartidaModel partidaModel = new PartidaModel();
        JugadorModel jugador = new JugadorModel();
        jugador.setId(1);
        partidaModel.setJugadores(List.of(jugador));

        when(turnosRefuerzosService.buscarTurnosByRonda(ronda)).thenReturn(List.of());
        when(turnosAtaquesService.buscarTurnosByRonda(ronda)).thenReturn(List.of());

        RondaModel result = rondasService.mapearRonda(ronda, partidaModel);

        assertNotNull(result);
        assertEquals(1, result.getNumero());
        assertEquals(Estado.EN_CURSO, result.getEstado());
        assertEquals(1, result.getJugadores().size());
        verify(turnosRefuerzosService).buscarTurnosByRonda(ronda);
        verify(turnosAtaquesService).buscarTurnosByRonda(ronda);
    }

    @Test
    void getUltimaRondaPartidaModel() {
        Partidas partida = new Partidas();
        partida.setId(1);
        Rondas ronda = new Rondas();
        ronda.setEstado(Estado.EN_CURSO.toString());
        ronda.setNumero(1);

        ronda.setPartida(partida);

        PartidaModel partidaModel = new PartidaModel();
        JugadorModel jugador = new JugadorModel();
        jugador.setId(1);
        jugador.setEstadoJugador(EstadoJugador.VIVO);
        partidaModel.setJugadores(List.of(jugador));

        when(rondaRepository.findTopByPartidaOrderByIdDesc(partida)).thenReturn(ronda);
        when(turnosRefuerzosService.buscarTurnosByRonda(ronda)).thenReturn(List.of());
        when(turnosAtaquesService.buscarTurnosByRonda(ronda)).thenReturn(List.of());

        RondaModel result = rondasService.getUltimaRondaPartidaModel(partida, partidaModel);

        assertNotNull(result);
        verify(rondaRepository).findTopByPartidaOrderByIdDesc(partida);
    }
    @Test
    void getNextTurnAtaque() {
        Long idRonda = 1L;
        Long idTurno = 10L;
        Rondas ronda = new Rondas();
        ronda.setId(1);

        TurnoAtaqueModel turno1 = new TurnoAtaqueModel();
        turno1.setId(10);
        TurnoAtaqueModel turno2 = new TurnoAtaqueModel();
        turno2.setId(20);

        RondaModel rondaModel = new RondaModel();
        rondaModel.setTurnosAtaque(List.of(turno1, turno2));

        when(rondaRepository.findById(idRonda)).thenReturn(Optional.of(ronda));

        doReturn(rondaModel).when(rondasService).mapearRonda(any(), any());

        TurnoAtaqueModel result = rondasService.getNextTurnAtaque(idRonda, new PartidaModel(), idTurno);

        assertNotNull(result);
        assertEquals(20, result.getId());
    }

    @Test
    void getNextTurnRefuerzo() {
        Long idRonda = 1L;
        Long idTurno = 10L;
        Rondas ronda = new Rondas();
        ronda.setId(1);

        TurnoRefuerzoModel turno1 = new TurnoRefuerzoModel();
        turno1.setId(10);
        TurnoRefuerzoModel turno2 = new TurnoRefuerzoModel();
        turno2.setId(20);

        RondaModel rondaModel = new RondaModel();
        rondaModel.setTurnosRefuerzo(List.of(turno1, turno2));

        when(rondaRepository.findById(idRonda)).thenReturn(Optional.of(ronda));
        doReturn(rondaModel).when(rondasService).mapearRonda(any(), any());

        TurnoRefuerzoModel result = rondasService.getNextTurnRefuerzo(idRonda, new PartidaModel(), idTurno);

        assertNotNull(result);
        assertEquals(20, result.getId());
    }
}