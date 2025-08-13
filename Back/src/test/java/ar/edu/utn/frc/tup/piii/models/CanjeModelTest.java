package ar.edu.utn.frc.tup.piii.models;

import ar.edu.utn.frc.tup.piii.entities.Canjes;
import ar.edu.utn.frc.tup.piii.entities.Jugadores;
import ar.edu.utn.frc.tup.piii.entities.Tarjetas;
import ar.edu.utn.frc.tup.piii.entities.TurnosRefuerzos;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CanjeModelTest {

    @Test
    void mapCanjeModelTest() {
        Canjes canje = new Canjes();
        canje.setId(1);

        Jugadores jugador = new Jugadores();
        jugador.setId(2);
        canje.setJugador(jugador);

        TurnosRefuerzos turno = new TurnosRefuerzos();
        turno.setId(3);
        canje.setTurnoRefuerzo(turno);

        Tarjetas tarjeta1 = new Tarjetas();
        tarjeta1.setId(4);
        canje.setTarjeta1(tarjeta1);

        Tarjetas tarjeta2 = new Tarjetas();
        tarjeta2.setId(5);
        canje.setTarjeta2(tarjeta2);

        Tarjetas tarjeta3 = new Tarjetas();
        tarjeta3.setId(6);
        canje.setTarjeta3(tarjeta3);

        canje.setCantidadFichas(7);

        CanjeModel model = new CanjeModel();
        model.mapCanjeModel(canje);

        Assertions.assertEquals(1, model.getId());
        Assertions.assertEquals(2, model.getIdJugador());
        Assertions.assertEquals(3, model.getIdTurnoRefuerzo());
        Assertions.assertEquals(4, model.getIdtarjeta1());
        Assertions.assertEquals(5, model.getIdtarjeta2());
        Assertions.assertEquals(6, model.getIdtarjeta3());
        Assertions.assertEquals(7, model.getCantidadFichas());
    }
}