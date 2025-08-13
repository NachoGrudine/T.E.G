package ar.edu.utn.frc.tup.piii.models;

import ar.edu.utn.frc.tup.piii.entities.Colores;
import ar.edu.utn.frc.tup.piii.entities.Objetivos;
import ar.edu.utn.frc.tup.piii.models.ObjetivoModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ObjetivoModelTest {

    @Test
    void mapObjetivoModelTest() {
        Colores color = new Colores();
        color.setId(1);
        color.setColor("Rojo");

        Objetivos entity = new Objetivos();
        entity.setId(99);
        entity.setColor(color);

        ObjetivoModel model = new ObjetivoModel();
        model.mapObjetivoModel(entity);

        Assertions.assertEquals(99, model.getIdObjetivo());
        Assertions.assertNotNull(model.getColorModel());
        Assertions.assertEquals(1, model.getColorModel().getId());
        Assertions.assertEquals("Rojo", model.getColorModel().getColor());
    }

    @Test
    void mapObjetivoModelTest_nullColor() {
        Objetivos entity = new Objetivos();
        entity.setId(100);
        entity.setColor(null);

        ObjetivoModel model = new ObjetivoModel();
        model.mapObjetivoModel(entity);

        Assertions.assertEquals(100, model.getIdObjetivo());
        Assertions.assertNull(model.getColorModel());
    }
}