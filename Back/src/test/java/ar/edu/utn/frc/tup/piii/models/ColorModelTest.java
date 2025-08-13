package ar.edu.utn.frc.tup.piii.models;

import ar.edu.utn.frc.tup.piii.entities.Colores;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ColorModelTest {

    @Test
    void mapColorModelTest() {
        Colores entity = new Colores();
        entity.setId(1);
        entity.setColor("Rojo");

        ColorModel model = new ColorModel();
        model.mapColorModel(entity);

        Assertions.assertEquals(entity.getId(), model.getId());
        Assertions.assertEquals(entity.getColor(), model.getColor());
    }
}