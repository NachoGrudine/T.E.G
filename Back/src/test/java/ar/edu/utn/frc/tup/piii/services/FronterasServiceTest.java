package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.entities.Fronteras;
import ar.edu.utn.frc.tup.piii.entities.Paises;
import ar.edu.utn.frc.tup.piii.repositories.FronterasRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FronterasServiceTest {

    @Mock
    private FronterasRepository fronterasRepository;

    @InjectMocks
    private FronterasService fronterasService;

    @Test
    public void findAllByPais1_IdOrPais2_IdTest()
    {
        Paises pais1 = new Paises();
        pais1.setId(1); //arg

        Paises pais2 = new Paises();
        pais2.setId(2);//bra

        Paises pais3 = new Paises();
        pais3.setId(3);//uruguay

        Paises pais4 = new Paises();
        pais4.setId(4);//chile

        // frontera 1 Pais origen 1 destino 2
        Fronteras fronteras1 = new Fronteras();
        fronteras1.setPais1(pais1);
        fronteras1.setPais2(pais2);

        Fronteras fronteras2 = new Fronteras();
        fronteras2.setPais1(pais3);
        fronteras2.setPais2(pais1);


        List<Fronteras> fronterasList = new ArrayList<>();
        fronterasList.add(fronteras1);
        fronterasList.add(fronteras2);


        when(fronterasRepository.findAllByPais1_IdOrPais2_Id(1, 1)).thenReturn(fronterasList);

        List<Integer> fronterasModel = fronterasService.findAllByPais1_IdOrPais2_Id(1);


        Assertions.assertNotNull(fronterasModel);
        Assertions.assertEquals(2, fronterasModel.size());
        Assertions.assertEquals(fronterasModel.get(0),fronteras1.getPais2().getId());
        Assertions.assertEquals(fronterasModel.get(1),fronteras2.getPais1().getId());



    }


    @Test
    void crearGrafo_ok() {
        Paises pais1 = new Paises();
        pais1.setId(1);
        Paises pais2 = new Paises();
        pais2.setId(2);
        Paises pais3 = new Paises();
        pais3.setId(3);

        Fronteras f1 = new Fronteras();
        f1.setPais1(pais1);
        f1.setPais2(pais2);

        Fronteras f2 = new Fronteras();
        f2.setPais1(pais2);
        f2.setPais2(pais3);

        when(fronterasRepository.findAll()).thenReturn(Arrays.asList(f1, f2));


        Map<Integer, List<Integer>> grafo = fronterasService.crearGrafo();

        assertEquals(List.of(2), grafo.get(1));
        assertTrue(grafo.get(2).containsAll(List.of(1, 3)));
        assertEquals(List.of(2), grafo.get(3));
        assertEquals(3, grafo.size());
    }

}
