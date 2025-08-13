package ar.edu.utn.frc.tup.piii.models;

import ar.edu.utn.frc.tup.piii.entities.Jugadores;
import ar.edu.utn.frc.tup.piii.entities.Paises;
import ar.edu.utn.frc.tup.piii.entities.Tarjetas;
import ar.edu.utn.frc.tup.piii.entities.TarjetasJugadores;
import ar.edu.utn.frc.tup.piii.enums.Simbolo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TarjetaJugadorModelTest {

    @Test
    void mapTarjetaJugadorModelTest() {
        // 1. Crear entidad Paises
        Paises pais = new Paises();
        pais.setId(1);
        pais.setNombre("Argentina");




        Tarjetas tarjeta = new Tarjetas();
        tarjeta.setId(100);
        tarjeta.setPais(pais);
        tarjeta.setSimbolo("CANION");
        tarjeta.setEstadoUso("ACTIVA");


        Jugadores jugador = new Jugadores();
        jugador.setId(200);

        TarjetasJugadores entity = new TarjetasJugadores();
        entity.setId(999);
        entity.setTarjeta(tarjeta);
        entity.setJugador(jugador);

        TarjetaJugadorModel model = new TarjetaJugadorModel();
        model.mapTarjetaJugadorModel(entity);

        Assertions.assertEquals(999, model.getId());
        Assertions.assertEquals(100, model.getTarjeta().getId());
        Assertions.assertEquals("Argentina", model.getTarjeta().getNombre());
        Assertions.assertEquals(Simbolo.CANION, model.getTarjeta().getSimbolo());
        Assertions.assertEquals("ACTIVA", model.getTarjeta().getEstado());
        Assertions.assertEquals(200, model.getIdJugador());
    }
}
