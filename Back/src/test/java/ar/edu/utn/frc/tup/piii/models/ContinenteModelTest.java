package ar.edu.utn.frc.tup.piii.models;

import ar.edu.utn.frc.tup.piii.entities.Continentes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ContinenteModelTest {

    @Test
    void mapContinenteModelTest() {
        Continentes entity = new Continentes();
        entity.setId(1);
        entity.setNombre("Europa");
        entity.setPremio(10);

        ContinenteModel model = new ContinenteModel();
        model.mapContinenteModel(entity);

        Assertions.assertEquals(entity.getId(), model.getId());
        Assertions.assertEquals(entity.getNombre(), model.getNombre());
        Assertions.assertEquals(entity.getPremio(), model.getPremio());
    }
}