package ar.edu.utn.frc.tup.piii.repositories;

import ar.edu.utn.frc.tup.piii.entities.Partidas;
import ar.edu.utn.frc.tup.piii.entities.Usuarios;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class PartidasRepositoryTest {


    @Autowired
    private PartidasRepository partidasRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @DisplayName("Debe encontrar partidas por usuario")
    void getAllByUsuario() {
        Usuarios usuario = new Usuarios();
        usuario.setUsername("testuser");
        usuario.setPassword("1234");
        entityManager.persist(usuario);

        Partidas partida = new Partidas();
        partida.setUsuario(usuario);
        partida.setEstado("EN_CURSO");
        partida.setTipo("NORMAL");
        partida.setCantidadParaGanar(10);
        entityManager.persist(partida);

        entityManager.flush();

        List<Partidas> partidas = partidasRepository.getAllByUsuario(usuario);
        assertEquals(1, partidas.size());
        assertEquals("EN_CURSO", partidas.get(0).getEstado());
    }

    @Test
    void id() {
    }

    @Test
    @DisplayName("Debe encontrar partidas por tipo y estado")
    void findAllByTipoAndEstado() {
        Usuarios usuario = new Usuarios();
        usuario.setUsername("otro");
        usuario.setPassword("abcd");
        entityManager.persist(usuario);

        Partidas partida = new Partidas();
        partida.setUsuario(usuario);
        partida.setEstado("FINALIZADA");
        partida.setTipo("RAPIDA");
        partida.setCantidadParaGanar(5);
        entityManager.persist(partida);

        entityManager.flush();

        List<Partidas> partidas = partidasRepository.findAllByTipoAndEstado("RAPIDA", "FINALIZADA");
        assertEquals(1, partidas.size());
        assertEquals("RAPIDA", partidas.get(0).getTipo());
        assertEquals("FINALIZADA", partidas.get(0).getEstado());
    }
}