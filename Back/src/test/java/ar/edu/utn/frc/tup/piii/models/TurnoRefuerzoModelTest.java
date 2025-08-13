package ar.edu.utn.frc.tup.piii.models;

import ar.edu.utn.frc.tup.piii.entities.Jugadores;
import ar.edu.utn.frc.tup.piii.entities.TurnosRefuerzos;
import ar.edu.utn.frc.tup.piii.enums.Estado;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TurnoRefuerzoModelTest {

    @Test
    void mapTurnoRefuerzoModelTest() {
        Jugadores jugador = new Jugadores();
        jugador.setId(1);

        TurnosRefuerzos entity = new TurnosRefuerzos();
        entity.setId(10);
        entity.setJugador(jugador);
        entity.setEstado(Estado.EN_CURSO.name());

        TurnoRefuerzoModel model = new TurnoRefuerzoModel();
        model.mapTurnoRefuerzoModel(entity);

        Assertions.assertEquals(entity.getId(), model.getId());
        Assertions.assertEquals(jugador.getId(), model.getIdJugador());
        Assertions.assertEquals(Estado.EN_CURSO, model.getEstado());
    }
}