package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.entities.Jugadores;
import ar.edu.utn.frc.tup.piii.entities.Paises;
import ar.edu.utn.frc.tup.piii.entities.Tarjetas;
import ar.edu.utn.frc.tup.piii.entities.TarjetasJugadores;
import ar.edu.utn.frc.tup.piii.models.TarjetaJugadorModel;
import ar.edu.utn.frc.tup.piii.repositories.TarjetasJugadoresRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TarjetasJugadoresServiceTest {

    TarjetasJugadoresRepository repo = mock(TarjetasJugadoresRepository.class);
    TarjetasJugadoresService service = new TarjetasJugadoresService(repo);



    @Test
    void getTarjetasJugadores_ok() {
        TarjetasJugadores tj = new TarjetasJugadores();
        Jugadores jugador = new Jugadores();
        jugador.setId(1);
        jugador.setNombre("Juan");
        tj.setJugador(jugador);

        Tarjetas tarjeta = new Tarjetas();
        tarjeta.setId(2);
        Paises pais = new Paises();
        pais.setId(10);
        pais.setNombre("Argentina");
        tarjeta.setPais(pais);
        tarjeta.setSimbolo("CANION");

        tj.setTarjeta(tarjeta);

        when(repo.findAllByJugador_Id(1)).thenReturn(List.of(tj));

        List<TarjetaJugadorModel> result = service.getTarjetasJugadores(1);

        assertEquals(1, result.size());
        verify(repo).findAllByJugador_Id(1);
    }

    @Test
    void darTarjeta_ok() {
        Jugadores jugador = new Jugadores();
        Tarjetas tarjeta = new Tarjetas();

        service.darTarjeta(jugador, tarjeta);

        ArgumentCaptor<TarjetasJugadores> captor = ArgumentCaptor.forClass(TarjetasJugadores.class);
        verify(repo).save(captor.capture());
        assertEquals(jugador, captor.getValue().getJugador());
        assertEquals(tarjeta, captor.getValue().getTarjeta());
    }
}