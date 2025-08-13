package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.dtos.common.AccionCombatePostDTO;
import ar.edu.utn.frc.tup.piii.dtos.common.AccionReagrupacionPostDTO;
import ar.edu.utn.frc.tup.piii.entities.*;
import ar.edu.utn.frc.tup.piii.enums.Estado;
import ar.edu.utn.frc.tup.piii.models.*;
import ar.edu.utn.frc.tup.piii.repositories.TurnosAtaquesRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class TurnosAtaquesServiceTest {

    @Mock
    TurnosAtaquesRepository turnosAtaquesRepository;
    @Mock
    JugadoresService jugadoresService;
    @Mock
    AccionesService accionesService;

    @InjectMocks
    TurnosAtaquesService turnosAtaquesService;

    @Test
    void crearTurnoAtaque_ok() {
        Rondas ronda = new Rondas();
        JugadorModel jugadorModel = new JugadorModel();
        jugadorModel.setId(1);
        Jugadores jugadorEntity = new Jugadores();
        when(jugadoresService.getJugadorByIdEntity(1L)).thenReturn(jugadorEntity);

        turnosAtaquesService.crearTurnoAtaque(ronda, jugadorModel);

        verify(turnosAtaquesRepository).save(any(TurnosAtaques.class));
    }

    @Test
    void mapearTurnoAtaque_ok() {
        TurnosAtaques entity = new TurnosAtaques();
        Jugadores jugador = new Jugadores();
        jugador.setId(1);
        entity.setJugador(jugador);
        entity.setEstado(Estado.SIN_JUGAR.toString());

        List<AccionModel> acciones = List.of(new AccionModel());
        when(accionesService.buscarAccionesByTurno(entity)).thenReturn(acciones);

        TurnoAtaqueModel result = turnosAtaquesService.mapearTurnoAtaque(entity);

        assertNotNull(result);
        assertEquals(acciones, result.getAcciones());
    }

    @Test
    void buscarTurnosByRonda_ok() {
        Rondas ronda = new Rondas();
        TurnosAtaques entity = new TurnosAtaques();
        entity.setEstado(Estado.SIN_JUGAR.toString());
        Jugadores jugador = new Jugadores();
        jugador.setId(1);
        entity.setJugador(jugador);

        when(turnosAtaquesRepository.getAllByRondaOrderByIdAsc(ronda)).thenReturn(List.of(entity));
        List<AccionModel> acciones = List.of(new AccionModel());
        when(accionesService.buscarAccionesByTurno(entity)).thenReturn(acciones);

        List<TurnoAtaqueModel> result = turnosAtaquesService.buscarTurnosByRonda(ronda);

        assertEquals(1, result.size());
        assertEquals(acciones, result.get(0).getAcciones());
    }

    @Test
    void obtenerTurnoAtaque_nullId() {
        assertNull(turnosAtaquesService.obtenerTurnoAtaque(null));
    }

    @Test
    void obtenerTurnoAtaque_ok() {
        TurnosAtaques entity = new TurnosAtaques();
        entity.setEstado(Estado.SIN_JUGAR.toString());
        TurnoAtaqueModel model = new TurnoAtaqueModel();
        when(turnosAtaquesRepository.getReferenceById(1L)).thenReturn(entity);
        TurnosAtaquesService spyService = spy(turnosAtaquesService);
        doReturn(model).when(spyService).mapearTurnoAtaque(entity);

        TurnoAtaqueModel result = spyService.obtenerTurnoAtaque(1L);

        assertEquals(model, result);
    }

    @Test
    void hacerAtaque_ok() {
        AccionCombatePostDTO dto = new AccionCombatePostDTO();
        dto.setIdTurno(1);
        TurnosAtaques entity = new TurnosAtaques();
        AccionModel accion = new AccionModel();
        when(turnosAtaquesRepository.getReferenceById(1L)).thenReturn(entity);
        when(accionesService.hacerAtaque(dto, entity)).thenReturn(accion);

        AccionModel result = turnosAtaquesService.hacerAtaque(dto);

        assertEquals(accion, result);
    }

    @Test
    void hacerReagrupacion_ok() {
        AccionReagrupacionPostDTO dto = new AccionReagrupacionPostDTO();
        dto.setIdTurno(1);
        TurnosAtaques entity = new TurnosAtaques();
        AccionModel accion = new AccionModel();
        when(turnosAtaquesRepository.getReferenceById(1L)).thenReturn(entity);
        when(accionesService.hacerReagrupacion(dto, entity)).thenReturn(accion);

        AccionModel result = turnosAtaquesService.hacerReagrupacion(dto);

        assertEquals(accion, result);
    }

    @Test
    void terminarTurnoAtaque_ok() {
        TurnosAtaques entity = new TurnosAtaques();
        entity.setEstado(Estado.SIN_JUGAR.toString());
        Jugadores jugador = new Jugadores();
        jugador.setId(1);
        entity.setJugador(jugador);

        when(turnosAtaquesRepository.findById(1L)).thenReturn(Optional.of(entity));

        turnosAtaquesService.terminarTurnoAtaque(1L);

        assertEquals(Estado.FINALIZADO.toString(), entity.getEstado());
        verify(turnosAtaquesRepository).save(entity);
    }

    @Test
    void terminarTurnoAtaque_notFound() {
        when(turnosAtaquesRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () ->
                turnosAtaquesService.terminarTurnoAtaque(1L)
        );
        assertTrue(ex.getMessage().contains("Turno no encontrado"));
    }
}