package ar.edu.utn.frc.tup.piii.models;

import ar.edu.utn.frc.tup.piii.entities.Continentes;
import ar.edu.utn.frc.tup.piii.entities.Paises;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PaisModelTest {



    @Test
    void mapPaisModelTest()
    {

        //creo la entity anidada (revisa el paisModel)
        Continentes continente = new Continentes();
        continente.setId(1);
        continente.setNombre("America");
        continente.setPremio(15);

        //entity de pais
        Paises entity = new Paises();
        entity.setId(1);
        entity.setNombre("Argentina");
        entity.setContinente(continente);


        // creo la instancia
        PaisModel paisModel = new PaisModel();
        paisModel.mapPaisModel(entity); //pruebo el metodo

        //verifico q todos los campos sean iguales
        Assertions.assertEquals(paisModel.getId(), entity.getId());
        Assertions.assertEquals(paisModel.getNombre(), entity.getNombre());
        Assertions.assertEquals(paisModel.getContinente().getId(), continente.getId());
        Assertions.assertEquals(paisModel.getContinente().getNombre(), continente.getNombre());
        Assertions.assertEquals(paisModel.getContinente().getPremio(), continente.getPremio());
    }
}
