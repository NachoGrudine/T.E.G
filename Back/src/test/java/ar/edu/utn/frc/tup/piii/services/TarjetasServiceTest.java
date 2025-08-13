package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.entities.Continentes;
import ar.edu.utn.frc.tup.piii.entities.Paises;
import ar.edu.utn.frc.tup.piii.entities.Tarjetas;
import ar.edu.utn.frc.tup.piii.models.TarjetaModel;
import ar.edu.utn.frc.tup.piii.repositories.TarjetasRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TarjetasServiceTest {

    @Mock
    private TarjetasRepository tarjetasRepository;


    @InjectMocks
    private TarjetasService tarjetasService;


    @Test
    void getAllTarjetas_returnListOfAllTarjetas() {
        Continentes continente = new Continentes();
        continente.setId(1);
        continente.setNombre("America del sur");
        continente.setPremio(1);

        Paises pais1 = new Paises();
        pais1.setId(1);
        pais1.setNombre("Argentina");
        pais1.setContinente(continente);

        Paises pais2 = new Paises();
        pais2.setId(2);
        pais2.setNombre("Bolivia");
        pais2.setContinente(continente);

        Paises pais3 = new Paises();
        pais3.setId(3);
        pais3.setNombre("Chile");
        pais3.setContinente(continente);

        Tarjetas tarjeta1 = new Tarjetas();
        tarjeta1.setId(1);
        tarjeta1.setPais(pais1);
        tarjeta1.setSimbolo("BARCO");
        tarjeta1.setEstadoUso("no se");

        Tarjetas tarjeta2 = new Tarjetas();
        tarjeta2.setId(2);
        tarjeta2.setPais(pais2);
        tarjeta2.setSimbolo("CANION");

        Tarjetas tarjeta3 = new Tarjetas();
        tarjeta3.setId(3);
        tarjeta3.setPais(pais3);
        tarjeta3.setSimbolo("COMODIN");

        List<Tarjetas> tarjetasList = Arrays.asList(tarjeta1, tarjeta2, tarjeta3);

        when(tarjetasRepository.findAll()).thenReturn(tarjetasList);

        List<TarjetaModel> result = tarjetasService.getAllTarjetas();

        assertNotNull(result);
        assertEquals(3, result.size());

        assertEquals("Argentina", result.get(0).getNombre());
        assertEquals("BARCO", result.get(0).getSimbolo().name());

        assertEquals("Bolivia", result.get(1).getNombre());
        assertEquals("CANION", result.get(1).getSimbolo().name());

        assertEquals("Chile", result.get(2).getNombre());
        assertEquals("COMODIN", result.get(2).getSimbolo().name());
    }


}