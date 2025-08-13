package ar.edu.utn.frc.tup.piii.models;

import ar.edu.utn.frc.tup.piii.entities.Paises;
import ar.edu.utn.frc.tup.piii.entities.Reagrupaciones;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReagrupacionModelTest {

    @Test
    void testMapReagrupacionModel() {
        Reagrupaciones reagrupacion = mock(Reagrupaciones.class);
        Paises paisOrigen = mock(Paises.class);
        Paises paisDestino = mock(Paises.class);

        when(reagrupacion.getId()).thenReturn(1);
        when(reagrupacion.getPaisorigen()).thenReturn(paisOrigen);
        when(paisOrigen.getId()).thenReturn(10);
        when(reagrupacion.getPaisdestino()).thenReturn(paisDestino);
        when(paisDestino.getId()).thenReturn(20);
        when(reagrupacion.getCantidad()).thenReturn(5);

        ReagrupacionModel model = new ReagrupacionModel();
        model.mapReagrupacionModel(reagrupacion);

        assertEquals(1, getFieldValue(model, "id"));
        assertEquals(10, getFieldValue(model, "idPaisorigen"));
        assertEquals(20, getFieldValue(model, "idPaisdestino"));
        assertEquals(5, getFieldValue(model, "cantidad"));
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