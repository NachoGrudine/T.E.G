package ar.edu.utn.frc.tup.piii.models;

import ar.edu.utn.frc.tup.piii.entities.Continentes;
import ar.edu.utn.frc.tup.piii.entities.Objetivos;
import ar.edu.utn.frc.tup.piii.entities.ObjetivosConts;
import ar.edu.utn.frc.tup.piii.models.ObjetivoContModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ObjetivoContModelTest {

    @Test
    void mapObjetivoContModelTest() {
        Objetivos objetivo = new Objetivos();
        objetivo.setId(10);

        Continentes continente = new Continentes();
        continente.setId(5);
        continente.setNombre("Asia");
        continente.setPremio(7);

        ObjetivosConts entity = new ObjetivosConts();
        entity.setId(1);
        entity.setObjetivo(objetivo);
        entity.setContinente(continente);

        ObjetivoContModel model = new ObjetivoContModel();
        model.mapObjetivoContModel(entity);

        Assertions.assertEquals(1, model.getId());
        Assertions.assertEquals(10, model.getIdObjetivo());
        Assertions.assertNotNull(model.getContinenteModel());
        Assertions.assertEquals(5, model.getContinenteModel().getId());
        Assertions.assertEquals("Asia", model.getContinenteModel().getNombre());
        Assertions.assertEquals(7, model.getContinenteModel().getPremio());
    }
}