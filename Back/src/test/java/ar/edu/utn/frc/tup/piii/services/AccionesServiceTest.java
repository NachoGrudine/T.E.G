package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.dtos.common.AccionCombatePostDTO;
import ar.edu.utn.frc.tup.piii.dtos.common.AccionReagrupacionPostDTO;
import ar.edu.utn.frc.tup.piii.entities.*;
import ar.edu.utn.frc.tup.piii.models.AccionModel;
import ar.edu.utn.frc.tup.piii.repositories.AccionesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccionesServiceTest {

    @Mock
    AccionesRepository accionesRepository;
    @Mock
    CombatesService combatesService;
    @Mock
    ReagrupacionService reagrupacionService;

    @InjectMocks
    AccionesService accionesService;

    @Test
    void buscarAccionesByTurno() {
        TurnosAtaques turno = new TurnosAtaques();
        turno.setId(1);
        Acciones accion = new Acciones();
        accion.setId(1);
        accion.setTurnoAtaque(turno);

        when(accionesRepository.getAllByTurnoAtaque(turno)).thenReturn(List.of(accion));

        List<AccionModel> result = accionesService.buscarAccionesByTurno(turno);

        assertEquals(1, result.size());
        assertEquals(accion.getId(), result.get(0).getId());
        assertEquals(turno.getId(), result.get(0).getIdTurnoAtaque());
    }

    @Test
    void hacerAtaque() {
        // Seteo de entidades necesarias
        TurnosAtaques turno = new TurnosAtaques();
        turno.setId(2);
        Rondas ronda = new Rondas();
        Partidas partida = new Partidas();
        partida.setId(5);
        ronda.setPartida(partida);
        turno.setRonda(ronda);

        AccionCombatePostDTO dto = new AccionCombatePostDTO();
        dto.setIdPaisAtk(1);
        dto.setIdPaisDef(2);

        // Mock Combates y sus dependencias mínimas
        Combates combate = new Combates();
        combate.setId(100);
        Jugadores jugadorAtk = new Jugadores();
        jugadorAtk.setId(10);
        Jugadores jugadorDef = new Jugadores();
        jugadorDef.setId(20);
        Paises paisAtk = new Paises();
        paisAtk.setId(1);
        Paises paisDef = new Paises();
        paisDef.setId(2);
        combate.setJugadorAtaque(jugadorAtk);
        combate.setJugadorDefensa(jugadorDef);
        combate.setPaisAtk(paisAtk);
        combate.setPaisDef(paisDef);
        combate.setFichasAtk(3);
        combate.setFichasDef(2);

        when(combatesService.hacerCombate(1, 2, 5)).thenReturn(combate);

        Acciones accionGuardada = new Acciones();
        accionGuardada.setId(10);
        accionGuardada.setTurnoAtaque(turno);
        accionGuardada.setCombate(combate);

        when(accionesRepository.save(any())).thenReturn(accionGuardada);

        AccionModel model = accionesService.hacerAtaque(dto, turno);

        assertNotNull(model);
        assertEquals(10, model.getId());
        assertNotNull(model.getCombate());
        assertEquals(100, model.getCombate().getId());
    }

    @Test
    void hacerReagrupacion() {
        TurnosAtaques turno = new TurnosAtaques();
        turno.setId(3);
        Rondas ronda = new Rondas();
        Partidas partida = new Partidas();
        partida.setId(7);
        ronda.setPartida(partida);
        turno.setRonda(ronda);

        AccionReagrupacionPostDTO dto = new AccionReagrupacionPostDTO();
        dto.setIdPaisOrigen(1);
        dto.setIdPaisDestino(2);
        dto.setCantidad(3);

        // Mock Reagrupaciones y sus dependencias mínimas
        Reagrupaciones reagrupacion = new Reagrupaciones();
        reagrupacion.setId(200);
        Paises paisOrigen = new Paises();
        paisOrigen.setId(1);
        Paises paisDestino = new Paises();
        paisDestino.setId(2);
        reagrupacion.setPaisorigen(paisOrigen);
        reagrupacion.setPaisdestino(paisDestino);
        reagrupacion.setCantidad(3);

        when(reagrupacionService.hacerReagrupacion(1, 2, 3, 7)).thenReturn(reagrupacion);

        Acciones accionGuardada = new Acciones();
        accionGuardada.setId(20);
        accionGuardada.setTurnoAtaque(turno);
        accionGuardada.setReagrupacion(reagrupacion);

        when(accionesRepository.save(any())).thenReturn(accionGuardada);

        AccionModel model = accionesService.hacerReagrupacion(dto, turno);

        assertNotNull(model);
        assertEquals(20, model.getId());
        assertNotNull(model.getReagrupacion());
        assertEquals(200, model.getReagrupacion().getId());
    }
}