
package ar.edu.utn.frc.tup.piii.repositories;

import ar.edu.utn.frc.tup.piii.entities.Continentes;
import ar.edu.utn.frc.tup.piii.entities.Paises;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PaisesRepositoryTest {

    @Autowired
    private PaisesRepository paisesRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @DisplayName("Debe encontrar todos los países por id de continente")
    void findAllByContinente_Id() {
        Continentes continente = new Continentes();
        continente.setNombre("Europa");
        continente.setPremio(5);
        entityManager.persist(continente);

        Paises pais1 = new Paises();
        pais1.setNombre("España");
        pais1.setContinente(continente);
        paisesRepository.save(pais1);

        Paises pais2 = new Paises();
        pais2.setNombre("Francia");
        pais2.setContinente(continente);
        paisesRepository.save(pais2);

        entityManager.flush();

        List<Paises> paises = paisesRepository.findAllByContinente_Id(continente.getId());

        assertEquals(2, paises.size());
        assertTrue(paises.stream().anyMatch(p -> p.getNombre().equals("España")));
        assertTrue(paises.stream().anyMatch(p -> p.getNombre().equals("Francia")));
    }
}