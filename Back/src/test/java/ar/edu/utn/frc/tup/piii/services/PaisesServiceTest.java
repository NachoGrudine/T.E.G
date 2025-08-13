package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.entities.Continentes;
import ar.edu.utn.frc.tup.piii.entities.Paises;
import ar.edu.utn.frc.tup.piii.models.PaisModel;
import ar.edu.utn.frc.tup.piii.repositories.PaisesRepository;
import ar.edu.utn.frc.tup.piii.services.interfaces.IFronterasService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaisesServiceTest {

    @Mock
    private PaisesRepository paisesRepository;
    @Mock
    private IFronterasService fronterasService;

    @InjectMocks
    private PaisesService paisesService;

    @Test
    public void getAllPaises_listaConPaises() {
        Continentes continente = new Continentes();
        continente.setId(1);
        continente.setNombre("America");

        Paises pais1 = new Paises();
        pais1.setId(1);
        pais1.setNombre("Argentina");
        pais1.setContinente(continente);

        Paises pais2 = new Paises();
        pais2.setId(2);
        pais2.setNombre("Uruguay");
        pais2.setContinente(continente);

        when(fronterasService.findAllByPais1_IdOrPais2_Id(1)).thenReturn(List.of(2));
        when(fronterasService.findAllByPais1_IdOrPais2_Id(2)).thenReturn(List.of(1));
        when(paisesRepository.findAll()).thenReturn(List.of(pais1, pais2));

        List<PaisModel> model = paisesService.getAllPaises();

        assertNotNull(model);
        assertEquals(2, model.size());
        assertEquals(List.of(2), model.get(0).getIdPaisesFronteras());
        assertEquals(List.of(1), model.get(1).getIdPaisesFronteras());
    }

    @Test
    public void getAllPaises_listaVacia() {
        when(paisesRepository.findAll()).thenReturn(Collections.emptyList());
        List<PaisModel> model = paisesService.getAllPaises();
        assertNotNull(model);
        assertTrue(model.isEmpty());
    }

    @Test
    public void getAllPaisesByContinenteId_conPaises() {
        Continentes continente = new Continentes();
        continente.setId(1);
        continente.setNombre("America");

        Paises pais = new Paises();
        pais.setId(1);
        pais.setNombre("Argentina");
        pais.setContinente(continente);

        when(paisesRepository.findAllByContinente_Id(1)).thenReturn(List.of(pais));

        List<PaisModel> result = paisesService.getAllPaisesByContinenteId(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Argentina", result.get(0).getNombre());
    }

    @Test
    public void getAllPaisesByContinenteId_listaVacia() {
        when(paisesRepository.findAllByContinente_Id(1)).thenReturn(Collections.emptyList());
        List<PaisModel> result = paisesService.getAllPaisesByContinenteId(1);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void getPaisById_existente() {
        Continentes continente = new Continentes();
        continente.setId(1);
        continente.setNombre("America");

        Paises pais = new Paises();
        pais.setId(1);
        pais.setNombre("Argentina");
        pais.setContinente(continente);

        when(paisesRepository.findById(1L)).thenReturn(Optional.of(pais));
        when(fronterasService.findAllByPais1_IdOrPais2_Id(1)).thenReturn(List.of(2, 3));

        PaisModel result = paisesService.getPaisById(1);

        assertNotNull(result);
        assertEquals("Argentina", result.getNombre());
        assertEquals(List.of(2, 3), result.getIdPaisesFronteras());
    }


    @Test
    public void getPaisByIdEntity_existente() {
        Paises pais = new Paises();
        pais.setId(1);
        when(paisesRepository.findById(1L)).thenReturn(Optional.of(pais));
        Optional<Paises> result = paisesService.getPaisByIdEntity(1L);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
    }

    @Test
    public void getPaisByIdEntity_noExistente() {
        when(paisesRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<Paises> result = paisesService.getPaisByIdEntity(99L);
        assertTrue(result.isEmpty());
    }

    @Test
    public void getAllPaisesEntity_conPaises() {
        Paises pais = new Paises();
        pais.setId(1);
        when(paisesRepository.findAll()).thenReturn(List.of(pais));
        List<Paises> result = paisesService.getAllPaisesEntity();
        assertEquals(1, result.size());
    }

    @Test
    public void getAllPaisesEntity_listaVacia() {
        when(paisesRepository.findAll()).thenReturn(Collections.emptyList());
        List<Paises> result = paisesService.getAllPaisesEntity();
        assertTrue(result.isEmpty());
    }
}