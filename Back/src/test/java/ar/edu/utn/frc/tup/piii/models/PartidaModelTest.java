package ar.edu.utn.frc.tup.piii.models;

import ar.edu.utn.frc.tup.piii.entities.Partidas;
import ar.edu.utn.frc.tup.piii.entities.Usuarios;
import ar.edu.utn.frc.tup.piii.enums.Estado;
import ar.edu.utn.frc.tup.piii.enums.TipoPartida;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PartidaModelTest {

    @Test
    void testMapPartidaModel() {
        Partidas partida = mock(Partidas.class);
        Usuarios usuario = mock(Usuarios.class);

        when(partida.getId()).thenReturn(1);
        when(partida.getEstado()).thenReturn(Estado.EN_CURSO.name());
        when(partida.getUsuario()).thenReturn(usuario);
        when(usuario.getId()).thenReturn(42);
        when(partida.getCantidadParaGanar()).thenReturn(10);
        when(partida.getTipo()).thenReturn(TipoPartida.PRIVADA.name());

        PartidaModel model = new PartidaModel();
        model.mapPartidaModel(partida);

        assertEquals(1, getFieldValue(model, "id"));
        assertEquals(Estado.EN_CURSO, getFieldValue(model, "estado"));
        assertEquals(42, getFieldValue(model, "idUsuario"));
        assertEquals(10, getFieldValue(model, "cantidadParaGanar"));
        assertEquals(TipoPartida.PRIVADA, getFieldValue(model, "tipoPartida"));
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