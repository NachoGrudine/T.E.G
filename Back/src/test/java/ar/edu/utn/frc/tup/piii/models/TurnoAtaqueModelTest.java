package ar.edu.utn.frc.tup.piii.models;

import ar.edu.utn.frc.tup.piii.entities.TurnosAtaques;
import ar.edu.utn.frc.tup.piii.enums.Estado;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TurnoAtaqueModelTest {

    @Test
    void mapTurnoAtaqueModelTest() {
        TurnosAtaques entity = new TurnosAtaques();
        entity.setId(10);
        entity.setEstado(Estado.FINALIZADO.name());

        // CORRECCIÓN: setear jugador y tarjeta
        ar.edu.utn.frc.tup.piii.entities.Jugadores jugador = new ar.edu.utn.frc.tup.piii.entities.Jugadores();
        jugador.setId(10);
        entity.setJugador(jugador);

        ar.edu.utn.frc.tup.piii.entities.Tarjetas tarjeta = new ar.edu.utn.frc.tup.piii.entities.Tarjetas();
        tarjeta.setId(10);
        entity.setTarjeta(tarjeta);

        TurnoAtaqueModel model = new TurnoAtaqueModel();
        model.mapTurnoAtaqueModel(entity);

        Assertions.assertEquals(entity.getId(), model.getId());
        Assertions.assertEquals(jugador.getId(), model.getIdJugador());
        Assertions.assertEquals(tarjeta.getId(), model.getIdTarjeta());
        Assertions.assertEquals(Estado.FINALIZADO, model.getEstado());
    }
}