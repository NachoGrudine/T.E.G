package ar.edu.utn.frc.tup.piii.models;

import ar.edu.utn.frc.tup.piii.entities.Partidas;
import ar.edu.utn.frc.tup.piii.entities.Rondas;
import ar.edu.utn.frc.tup.piii.enums.Estado;
import ar.edu.utn.frc.tup.piii.models.RondaModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RondaModelTest {

    @Test
    void mapRondaModelTest() {
        Partidas partida = new Partidas();
        partida.setId(99);

        Rondas entity = new Rondas();
        entity.setId(1);
        entity.setNumero(2);
        entity.setEstado(Estado.EN_CURSO.name());
        entity.setPartida(partida);

        RondaModel model = new RondaModel();
        model.mapRondaModel(entity);

        Assertions.assertEquals(entity.getId(), model.getId());
        Assertions.assertEquals(partida.getId(), model.getIdPartida());
        Assertions.assertEquals(entity.getNumero(), model.getNumero());
        Assertions.assertEquals(Estado.EN_CURSO, model.getEstado());

        Assertions.assertNull(model.getTurnosRefuerzo());
        Assertions.assertNull(model.getTurnosAtaque());
        Assertions.assertNull(model.getJugadores());
    }
}