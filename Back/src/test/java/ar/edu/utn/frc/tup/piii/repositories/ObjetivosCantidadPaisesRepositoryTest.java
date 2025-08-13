package ar.edu.utn.frc.tup.piii.repositories;

import ar.edu.utn.frc.tup.piii.entities.Colores;
import ar.edu.utn.frc.tup.piii.entities.Continentes;
import ar.edu.utn.frc.tup.piii.entities.Objetivos;
import ar.edu.utn.frc.tup.piii.entities.ObjetivosCantidadPaises;
import org.apache.catalina.LifecycleState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.junit.jupiter.api.Assertions.assertEquals;




import java.util.List;

@DataJpaTest
public class ObjetivosCantidadPaisesRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ObjetivosCantidadPaisesRepository objetivosCantidadPaisesRepository;

    @Test
    public void findAllByObjetivo_IdTest()
    {
        Colores colores = new Colores();
        colores.setColor("Violeta");
        entityManager.persist(colores);

        Objetivos objetivo = new Objetivos();
        objetivo.setColor(colores);
        entityManager.persist(objetivo);

        Continentes continente = new Continentes();
        continente.setNombre("Europa");
        continente.setPremio(5);
        entityManager.persist(continente);

        ObjetivosCantidadPaises objetivosCantidadPaises = new ObjetivosCantidadPaises();
        objetivosCantidadPaises.setObjetivo(objetivo);
        objetivosCantidadPaises.setCantidad(10);
        objetivosCantidadPaises.setContinente(continente);
        entityManager.persist(objetivosCantidadPaises);

        entityManager.flush();

        List<ObjetivosCantidadPaises> resultados = objetivosCantidadPaisesRepository.findAllByObjetivo_Id(objetivo.getId());
        assertEquals(1, resultados.size());

    }
}
