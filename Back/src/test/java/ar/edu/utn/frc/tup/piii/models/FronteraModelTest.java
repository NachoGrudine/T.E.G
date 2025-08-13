package ar.edu.utn.frc.tup.piii.models;

import ar.edu.utn.frc.tup.piii.entities.Fronteras;
import ar.edu.utn.frc.tup.piii.entities.Paises;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FronteraModelTest {

    @Test
    void mapFronteraModelTest() {
        Paises pais1 = new Paises();
        pais1.setId(1);
        Paises pais2 = new Paises();
        pais2.setId(2);

        Fronteras entity = new Fronteras();
        entity.setId(10);
        entity.setPais1(pais1);
        entity.setPais2(pais2);

        FronteraModel model = new FronteraModel();
        model.mapFronteraModel(entity);

        Assertions.assertEquals(entity.getId(), model.getId());
        Assertions.assertEquals(pais1.getId(), model.getIdpais1());
        Assertions.assertEquals(pais2.getId(), model.getIdpais2());
    }
}