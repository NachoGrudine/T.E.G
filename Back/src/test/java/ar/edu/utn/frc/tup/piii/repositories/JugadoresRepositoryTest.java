package ar.edu.utn.frc.tup.piii.repositories;

import ar.edu.utn.frc.tup.piii.entities.Colores;
import ar.edu.utn.frc.tup.piii.entities.Jugadores;
import ar.edu.utn.frc.tup.piii.entities.Partidas;
import ar.edu.utn.frc.tup.piii.entities.Usuarios;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class JugadoresRepositoryTest {

    @Autowired
    private JugadoresRepository jugadoresRepository;

    @Autowired
    private PartidasRepository partidasRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void getAllByPartidaOrderByIdAsc() {
        Usuarios usuario = new Usuarios();
        usuario.setUsername("jugador1");
        usuario.setPassword("pass");
        entityManager.persist(usuario);

        Partidas partida = new Partidas();
        partida.setUsuario(usuario);
        partida.setEstado("EN_CURSO");
        partida.setTipo("NORMAL");
        entityManager.persist(partida);

        Colores color = new Colores();
        color.setColor("Rojo");
        entityManager.persist(color);

        // Crear jugadores
        Jugadores jugador1 = new Jugadores();
        jugador1.setPartida(partida);
        jugador1.setNombre("Jugador Uno");
        jugador1.setTipoJugador("HUMANO");
        jugador1.setColor(color);
        jugador1.setUsuario(usuario);
        entityManager.persist(jugador1);

        Jugadores jugador2 = new Jugadores();
        jugador2.setPartida(partida);
        jugador2.setNombre("Jugador Dos");
        jugador2.setTipoJugador("HUMANO");
        jugador2.setColor(color);
        jugador2.setUsuario(usuario);
        entityManager.persist(jugador2);

        entityManager.flush();

        List<Jugadores> jugadores = jugadoresRepository.getAllByPartidaOrderByIdAsc(partida);
        assertEquals(2, jugadores.size());
        assertTrue(jugadores.get(0).getId() < jugadores.get(1).getId());
    }

    @Test
    void findByPartidaAndUsuario() {
        Usuarios usuario = new Usuarios();
        usuario.setUsername("jugador2");
        usuario.setPassword("pass2");
        entityManager.persist(usuario);

        Partidas partida = new Partidas();
        partida.setUsuario(usuario);
        partida.setEstado("EN_CURSO");
        partida.setTipo("NORMAL");
        entityManager.persist(partida);

        Colores color = new Colores();
        color.setColor("Azul");
        entityManager.persist(color);

        Jugadores jugador = new Jugadores();
        jugador.setPartida(partida);
        jugador.setNombre("Jugador Dos");
        jugador.setTipoJugador("HUMANO");
        jugador.setColor(color);
        jugador.setUsuario(usuario);
        entityManager.persist(jugador);

        entityManager.flush();

        Optional<Jugadores> encontrado = jugadoresRepository.findByPartidaAndUsuario(partida, usuario);
        assertTrue(encontrado.isPresent());
        assertEquals("Jugador Dos", encontrado.get().getNombre());
    }
}