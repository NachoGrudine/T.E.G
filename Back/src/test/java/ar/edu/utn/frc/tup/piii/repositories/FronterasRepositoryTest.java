package ar.edu.utn.frc.tup.piii.repositories;

import ar.edu.utn.frc.tup.piii.entities.Continentes;
import ar.edu.utn.frc.tup.piii.entities.Fronteras;
import ar.edu.utn.frc.tup.piii.entities.Paises;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FronterasRepositoryTest {

    @Autowired
    private FronterasRepository fronterasRepository;

    @Autowired
    private PaisesRepository paisesRepository;

    @PersistenceContext
    private EntityManager entityManager;





    @Test
    @DisplayName("Debe encontrar fronteras por id de país 1 o país 2")
    void findAllByPais1_IdOrPais2_Id() {
        Continentes continente;
        try {
            continente = entityManager
                    .createQuery("SELECT c FROM Continentes c WHERE c.nombre = :nombre", Continentes.class)
                    .setParameter("nombre", "América del sur")
                    .getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            continente = new Continentes();
            continente.setNombre("América del sur");
            continente.setPremio(5); // poné un valor válido si es requerido
            entityManager.persist(continente);
            entityManager.flush();
        }

        Paises paisA = new Paises();
        paisA.setNombre("Argentina");
        paisA.setContinente(continente);
        paisesRepository.save(paisA);

        Paises paisB = new Paises();
        paisB.setNombre("Brasil");
        paisB.setContinente(continente);
        paisesRepository.save(paisB);

        Paises paisC = new Paises();
        paisC.setNombre("Chile");
        paisC.setContinente(continente);
        paisesRepository.save(paisC);

        Fronteras frontera1 = new Fronteras();
        frontera1.setPais1(paisA);
        frontera1.setPais2(paisB);
        fronterasRepository.save(frontera1);

        Fronteras frontera2 = new Fronteras();
        frontera2.setPais1(paisB);
        frontera2.setPais2(paisC);
        fronterasRepository.save(frontera2);

        List<Fronteras> result = fronterasRepository.findAllByPais1_IdOrPais2_Id(paisA.getId(), paisC.getId());

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(f -> f.getPais1().getId().equals(paisA.getId())));
        assertTrue(result.stream().anyMatch(f -> f.getPais2().getId().equals(paisC.getId())));
    }
}