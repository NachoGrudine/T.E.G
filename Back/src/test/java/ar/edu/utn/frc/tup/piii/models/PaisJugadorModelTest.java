package ar.edu.utn.frc.tup.piii.models;

import ar.edu.utn.frc.tup.piii.entities.Jugadores;
import ar.edu.utn.frc.tup.piii.entities.Paises;
import ar.edu.utn.frc.tup.piii.entities.PaisesJugadores;
import ar.edu.utn.frc.tup.piii.entities.Partidas;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaisJugadorModelTest {

    @Test
    void testMapPaisJugadorModel() {
        PaisesJugadores paisJugador = mock(PaisesJugadores.class);
        Partidas partida = mock(Partidas.class);
        Jugadores jugador = mock(Jugadores.class);
        Paises pais = mock(Paises.class);

        when(paisJugador.getId()).thenReturn(1);
        when(paisJugador.getPartida()).thenReturn(partida);
        when(partida.getId()).thenReturn(2);
        when(paisJugador.getFichas()).thenReturn(3);
        when(paisJugador.getJugador()).thenReturn(jugador);
        when(jugador.getId()).thenReturn(4);
        when(paisJugador.getPais()).thenReturn(pais);
        when(pais.getId()).thenReturn(5);

        PaisJugadorModel model = new PaisJugadorModel();
        model.mapPaisJugadorModel(paisJugador);

        assertEquals(1, getFieldValue(model, "id"));
        assertEquals(2, getFieldValue(model, "idPartida"));
        assertEquals(3, getFieldValue(model, "fichas"));
        assertEquals(4, getFieldValue(model, "idJugador"));
        assertEquals(5, getFieldValue(model, "idPais"));
    }

    private Object getFieldValue(Object obj, String fieldName) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}