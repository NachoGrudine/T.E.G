package ar.edu.utn.frc.tup.piii.models;



import ar.edu.utn.frc.tup.piii.entities.Continentes;
import ar.edu.utn.frc.tup.piii.entities.Objetivos;
import ar.edu.utn.frc.tup.piii.entities.ObjetivosCantidadPaises;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ObjetivoCantidadPaisesModelTest {

    @Test
    void mapObjetivoCantidadPaisesModelTest() {
        Objetivos objetivo = new Objetivos();
        objetivo.setId(20);

        Continentes continente = new Continentes();
        continente.setId(2);
        continente.setNombre("Europa");
        continente.setPremio(5);

        ObjetivosCantidadPaises entity = new ObjetivosCantidadPaises();
        entity.setId(3);
        entity.setObjetivo(objetivo);
        entity.setContinente(continente);
        entity.setCantidad(4);

        ObjetivoCantidadPaisesModel model = new ObjetivoCantidadPaisesModel();
        model.mapObjetivoCantidadPaisesModel(entity);

        Assertions.assertEquals(3, model.getId());
        Assertions.assertEquals(20, model.getIdObjetivo());
        Assertions.assertNotNull(model.getContinenteModel());
        Assertions.assertEquals(2, model.getContinenteModel().getId());
        Assertions.assertEquals("Europa", model.getContinenteModel().getNombre());
        Assertions.assertEquals(5, model.getContinenteModel().getPremio());
        Assertions.assertEquals(4, model.getCantidad());
    }
}