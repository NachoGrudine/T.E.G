package ar.edu.utn.frc.tup.piii.services;
import static org.junit.jupiter.api.Assertions.*;
import ar.edu.utn.frc.tup.piii.entities.Colores;
import ar.edu.utn.frc.tup.piii.entities.Continentes;
import ar.edu.utn.frc.tup.piii.entities.Objetivos;
import ar.edu.utn.frc.tup.piii.entities.ObjetivosConts;
import ar.edu.utn.frc.tup.piii.models.ObjetivoContModel;
import ar.edu.utn.frc.tup.piii.repositories.ObjetivosContsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ObjetivosContsServiceTest {


    @Mock
    private ObjetivosContsRepository objetivosContsRepository;

    @InjectMocks
    private ObjetivosContsService service;

    @Test
    public void findAllByObjetivo_IdTest()
    {
        //primer paso: carga de datos
        Colores colores = new Colores();
        colores.setColor("Azul");

        Continentes continente = new Continentes();
        continente.setNombre("Asia");
        continente.setPremio(3);

        Objetivos objetivoE = new Objetivos();
        objetivoE.setColor(colores);

        ObjetivosConts entity1 = new ObjetivosConts();
        entity1.setObjetivo(objetivoE);
        entity1.setContinente(continente);

        ObjetivosConts entity2 = new ObjetivosConts();
        entity2.setObjetivo(objetivoE);
        entity2.setContinente(continente);

        List<ObjetivosConts> listaEntity = new ArrayList<>();
        listaEntity.add(entity1);
        listaEntity.add(entity2);
        //cuando se llama en el service a este metodo del repository q retorne la lista entity
        //basicamente simula el comportamiento del repository
        when(objetivosContsRepository.findAllByObjetivo_Id(objetivoE.getId())).thenReturn(listaEntity);

        //ejecuto metodo del service
        List<ObjetivoContModel> objetivosModel = service.findAllByObjetivo_Id(objetivoE.getId());

        //verifico que el mock se ejecuta
        verify(objetivosContsRepository).findAllByObjetivo_Id(objetivoE.getId());

        //etapa de assersions
        assertEquals(2, objetivosModel.size());
        assertNotNull(objetivosModel);

        //verificamos que el mapeo se haga correctamente con algunos atributardos

        assertEquals(entity1.getContinente().getNombre(), objetivosModel.get(0).getContinenteModel().getNombre());
        assertEquals(entity1.getObjetivo().getId(), objetivosModel.get(0).getIdObjetivo());

    }
}
