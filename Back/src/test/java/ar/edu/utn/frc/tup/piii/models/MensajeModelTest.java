package ar.edu.utn.frc.tup.piii.models;

import ar.edu.utn.frc.tup.piii.entities.Jugadores;
import ar.edu.utn.frc.tup.piii.entities.Mensajes;
import ar.edu.utn.frc.tup.piii.models.MensajeModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MensajeModelTest {

    @Test
    void mapMensajeModelTest() {
        Mensajes entity = new Mensajes();
        entity.setId(1);
        entity.setMensaje("Hola mundo");
        Jugadores jugador = new Jugadores();
        jugador.setId(5);
        entity.setJugador(jugador);

        MensajeModel model = new MensajeModel();
        model.mapMensajeModel(entity);

        Assertions.assertEquals(1, model.getId());
        Assertions.assertEquals("Hola mundo", model.getMensaje());
        Assertions.assertEquals(5, model.getIdJugador());
        Assertions.assertNotNull(model.getFechaHora());
    }
}