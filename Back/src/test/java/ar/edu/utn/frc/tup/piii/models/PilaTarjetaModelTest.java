package ar.edu.utn.frc.tup.piii.models;

import ar.edu.utn.frc.tup.piii.entities.Paises;
import ar.edu.utn.frc.tup.piii.entities.Partidas;
import ar.edu.utn.frc.tup.piii.entities.PilasTarjetas;
import ar.edu.utn.frc.tup.piii.entities.Tarjetas;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.util.FieldUtils.getFieldValue;

@ExtendWith(MockitoExtension.class)
class PilaTarjetaModelTest {
    @Test
    void testMapPilaTarjetaModel() throws IllegalAccessException {
        PilasTarjetas pila = mock(PilasTarjetas.class);
        Partidas partida = mock(Partidas.class);
        Tarjetas tarjeta = mock(Tarjetas.class);

        when(pila.getId()).thenReturn(1);
        when(pila.getPartida()).thenReturn(partida);
        when(partida.getId()).thenReturn(2);
        when(pila.getTarjeta()).thenReturn(tarjeta);

        PilaTarjetaModel model = new PilaTarjetaModel();

        Paises pais = mock(Paises.class);
        when(tarjeta.getPais()).thenReturn(pais);
        when(pais.getNombre()).thenReturn("Argentina");
        when(tarjeta.getSimbolo()).thenReturn("BARCO"); // <- valor válido
        when(tarjeta.getEstadoUso()).thenReturn("ACTIVA"); // si tu modelo lo requiere

        model.mapPilaTarjetaModel(pila);

        assertEquals(1, getFieldValue(model, "id"));
        assertEquals(2, getFieldValue(model, "idPartida"));
        assertNotNull(getFieldValue(model, "tarjeta"));
    }

}