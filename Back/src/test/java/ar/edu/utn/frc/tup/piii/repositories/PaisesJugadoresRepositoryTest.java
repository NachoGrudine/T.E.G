package ar.edu.utn.frc.tup.piii.repositories;

import ar.edu.utn.frc.tup.piii.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PaisesJugadoresRepositoryTest {

    @Autowired
    private PaisesJugadoresRepository paisesJugadoresRepository;

    @PersistenceContext
    private EntityManager entityManager;


    private Jugadores crearJugador(Usuarios usuario, Colores color, Partidas partida, String nombre) {
        Jugadores jugador = new Jugadores();
        jugador.setUsuario(usuario);
        jugador.setColor(color);
        jugador.setNombre(nombre);
        jugador.setTipoJugador("HUMANO");
        jugador.setPartida(partida);
        entityManager.persist(jugador);
        return jugador;
    }

    private Paises crearPais(String nombre, Continentes continente) {
        Paises pais = new Paises();
        pais.setNombre(nombre);
        pais.setContinente(continente);
        entityManager.persist(pais);
        return pais;
    }

    private PaisesJugadores crearPaisesJugadores(Partidas partida, Jugadores jugador, Paises pais, int fichas) {
        PaisesJugadores pj = new PaisesJugadores();
        pj.setPartida(partida);
        pj.setJugador(jugador);
        pj.setPais(pais);
        pj.setFichas(fichas);
        entityManager.persist(pj);
        return pj;
    }

    @Test
    void getAllByJugador_y_getAllByJugadorId() {

        Usuarios usuario = new Usuarios();
        usuario.setUsername("user1");
        usuario.setPassword("pass");
        entityManager.persist(usuario);

        Colores rojo = new Colores();
        rojo.setColor("Rojo");
        entityManager.persist(rojo);

        Partidas partida = new Partidas();
        partida.setUsuario(usuario);
        partida.setEstado("EN_CURSO");
        partida.setTipo("NORMAL");
        entityManager.persist(partida);

        Continentes america = new Continentes();
        america.setNombre("América");
        entityManager.persist(america);

        Paises brasil = crearPais("Brasil", america);
        Paises chile  = crearPais("Chile",  america);

        Jugadores jugador1 = crearJugador(usuario, rojo, partida, "Jugador Uno");
        Jugadores jugador2 = crearJugador(usuario, rojo, partida, "Jugador Dos");

        crearPaisesJugadores(partida, jugador1, brasil, 3);
        crearPaisesJugadores(partida, jugador1, chile,  1);
        crearPaisesJugadores(partida, jugador2, chile,  2);

        entityManager.flush();

        List<PaisesJugadores> porJugador = paisesJugadoresRepository.getAllByJugador(jugador1);
        assertEquals(2, porJugador.size());
        assertTrue(porJugador.stream().allMatch(pj -> pj.getJugador().equals(jugador1)));

        List<PaisesJugadores> porId = paisesJugadoresRepository.getAllByJugadorId(jugador1.getId());
        assertEquals(2, porId.size());
        assertTrue(porId.stream().allMatch(pj -> pj.getJugador().getId().equals(jugador1.getId())));
    }

    @Test
    void findByPartida_IdAndPais_Id() {

        Usuarios usuario = new Usuarios();
        usuario.setUsername("user2");
        usuario.setPassword("pass2");
        entityManager.persist(usuario);

        Colores azul = new Colores();
        azul.setColor("Azul");
        entityManager.persist(azul);

        Partidas partida = new Partidas();
        partida.setUsuario(usuario);
        partida.setEstado("EN_CURSO");
        partida.setTipo("NORMAL");
        entityManager.persist(partida);

        Continentes europa = new Continentes();
        europa.setNombre("Europa");
        entityManager.persist(europa);

        Paises francia = crearPais("Francia", europa);

        Jugadores jugador = crearJugador(usuario, azul, partida, "Jugador Tres");

        PaisesJugadores pj = crearPaisesJugadores(partida, jugador, francia, 5);

        entityManager.flush();

        PaisesJugadores encontrado = paisesJugadoresRepository
                .findByPartida_IdAndPais_Id(partida.getId(), francia.getId());

        assertNotNull(encontrado);
        assertEquals(pj.getId(), encontrado.getId());
    }

    @Test
    void getAllByPartida_y_getAllByPais_Continente_Id() {

        Usuarios usuario = new Usuarios();
        usuario.setUsername("user3");
        usuario.setPassword("pass3");
        entityManager.persist(usuario);

        Colores verde = new Colores();
        verde.setColor("Verde");
        entityManager.persist(verde);

        Partidas partida = new Partidas();
        partida.setUsuario(usuario);
        partida.setEstado("EN_CURSO");
        partida.setTipo("NORMAL");
        entityManager.persist(partida);

        Continentes asia      = new Continentes(); asia.setNombre("Asia");      entityManager.persist(asia);
        Continentes oceania   = new Continentes(); oceania.setNombre("Oceanía"); entityManager.persist(oceania);

        Paises china   = crearPais("China",   asia);
        Paises india   = crearPais("India",   asia);
        Paises sydney  = crearPais("Sydney",  oceania);

        Jugadores jugador = crearJugador(usuario, verde, partida, "Jugador Cuatro");

        crearPaisesJugadores(partida, jugador, china, 2);
        crearPaisesJugadores(partida, jugador, india, 4);
        crearPaisesJugadores(partida, jugador, sydney,1);

        entityManager.flush();

        List<PaisesJugadores> porPartida = paisesJugadoresRepository.getAllByPartida(partida);
        assertEquals(3, porPartida.size());

        List<PaisesJugadores> porContinenteAsia = paisesJugadoresRepository.getAllByPais_Continente_Id(asia.getId());
        assertEquals(2, porContinenteAsia.size());
        assertTrue(porContinenteAsia.stream().allMatch(pj -> pj.getPais().getContinente().equals(asia)));
    }

    @Test
    void getAllByJugador_IdAndFichasGreaterThan() {

        Usuarios usuario = new Usuarios();
        usuario.setUsername("user4");
        usuario.setPassword("pass4");
        entityManager.persist(usuario);

        Colores amarillo = new Colores();
        amarillo.setColor("Amarillo");
        entityManager.persist(amarillo);

        Partidas partida = new Partidas();
        partida.setUsuario(usuario);
        partida.setEstado("EN_CURSO");
        partida.setTipo("NORMAL");
        entityManager.persist(partida);

        Continentes africa = new Continentes();
        africa.setNombre("África");
        entityManager.persist(africa);

        Paises egipto  = crearPais("Egipto",  africa);
        Paises kenia   = crearPais("Kenia",   africa);

        Jugadores jugador = crearJugador(usuario, amarillo, partida, "Jugador Cinco");

        crearPaisesJugadores(partida, jugador, egipto, 5);
        crearPaisesJugadores(partida, jugador, kenia,  1);

        entityManager.flush();

        List<PaisesJugadores> conMasDeDosFichas =
                paisesJugadoresRepository.getAllByJugador_IdAndFichasGreaterThan(jugador.getId(), 2);

        assertEquals(1, conMasDeDosFichas.size());
        assertTrue(conMasDeDosFichas.get(0).getFichas() > 2);
        assertEquals(egipto.getId(), conMasDeDosFichas.get(0).getPais().getId());
    }

}
