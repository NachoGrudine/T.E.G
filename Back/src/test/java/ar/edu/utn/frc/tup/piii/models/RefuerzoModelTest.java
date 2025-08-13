package ar.edu.utn.frc.tup.piii.models;

import ar.edu.utn.frc.tup.piii.entities.*;
import ar.edu.utn.frc.tup.piii.enums.TipoFicha;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static ar.edu.utn.frc.tup.piii.enums.TipoFicha.CONTINENTE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RefuerzoModelTest {
    @Test
    void testMapRefuerzoModel() {
        // Mocks principales
        Refuerzos refuerzo = mock(Refuerzos.class);
        TurnosRefuerzos turno = mock(TurnosRefuerzos.class);
        PaisesJugadores paisJugador = mock(PaisesJugadores.class);

        // Mocks adicionales para PaisJugadorModel
        Partidas partida = mock(Partidas.class);
        Jugadores jugador = mock(Jugadores.class);
        Paises pais = mock(Paises.class);

        // Configuración de mocks
        when(refuerzo.getId()).thenReturn(10);
        when(refuerzo.getTurnoRef()).thenReturn(turno);
        when(turno.getId()).thenReturn(20);
        when(refuerzo.getPaisJugador()).thenReturn(paisJugador);
        when(refuerzo.getCantidad()).thenReturn(5);
        when(refuerzo.getTipoFichas()).thenReturn(CONTINENTE.name());

        when(paisJugador.getPartida()).thenReturn(partida);
        when(partida.getId()).thenReturn(100);

        when(paisJugador.getJugador()).thenReturn(jugador);
        when(jugador.getId()).thenReturn(200);

        when(paisJugador.getPais()).thenReturn(pais);
        when(pais.getId()).thenReturn(300);

        when(paisJugador.getFichas()).thenReturn(7);

        // Instancia y mapeo
        RefuerzoModel model = new RefuerzoModel();
        model.mapRefuerzoModel(refuerzo);

        // Asserts
        assertEquals(10, model.getId());
        assertEquals(20, model.getIdTurnoRef());
        assertNotNull(model.getPaisJugadorModel());
        assertEquals(5, model.getCantidad());
        assertEquals(CONTINENTE, model.getTipoFichas());
        // Asserts adicionales para el paisJugadorModel
        assertEquals(100, model.getPaisJugadorModel().getIdPartida());
        assertEquals(200, model.getPaisJugadorModel().getIdJugador());
        assertEquals(300, model.getPaisJugadorModel().getIdPais());
        assertEquals(7, model.getPaisJugadorModel().getFichas());
    }

}