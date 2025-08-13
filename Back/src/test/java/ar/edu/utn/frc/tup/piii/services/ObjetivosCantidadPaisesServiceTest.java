package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.entities.Continentes;
import ar.edu.utn.frc.tup.piii.entities.Objetivos;
import ar.edu.utn.frc.tup.piii.entities.ObjetivosCantidadPaises;
import ar.edu.utn.frc.tup.piii.models.ContinenteModel;
import ar.edu.utn.frc.tup.piii.models.ObjetivoCantidadPaisesModel;
import ar.edu.utn.frc.tup.piii.models.PaisModel;
import ar.edu.utn.frc.tup.piii.repositories.ObjetivosCantidadPaisesRepository;
import ar.edu.utn.frc.tup.piii.services.ObjetivosCantPaisesService;
import ar.edu.utn.frc.tup.piii.services.interfaces.IPaisesService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ObjetivosCantPaisesServiceTest {

    ObjetivosCantidadPaisesRepository repo = mock(ObjetivosCantidadPaisesRepository.class);
    IPaisesService paisesService = mock(IPaisesService.class);
    ObjetivosCantPaisesService service = new ObjetivosCantPaisesService(repo, paisesService);


    @Test
    void findAllByObjetivo_Id_ok() {

        Continentes continente = new Continentes();
        ObjetivosCantidadPaises obj = new ObjetivosCantidadPaises();
        Objetivos objetivo = new Objetivos();
        objetivo.setId(1);
        obj.setObjetivo(objetivo);
        obj.setContinente(continente);

        when(repo.findAllByObjetivo_Id(1)).thenReturn(List.of(obj));

        List<ObjetivoCantidadPaisesModel> result = service.findAllByObjetivo_Id(1);

        assertEquals(1, result.size());
        verify(repo).findAllByObjetivo_Id(1);
    }
    @Test
    void obtenerIdsPaisesDeObjetivo_ok() {
        ObjetivoCantidadPaisesModel objetivo = new ObjetivoCantidadPaisesModel();
        ContinenteModel continente = new ContinenteModel();
        continente.setId(10);
        objetivo.setContinenteModel(continente);

        ObjetivosCantPaisesService spyService = spy(service);
        doReturn(List.of(objetivo)).when(spyService).findAllByObjetivo_Id(1);

        PaisModel pais1 = new PaisModel();
        pais1.setId(100);
        PaisModel pais2 = new PaisModel();
        pais2.setId(200);

        when(paisesService.getAllPaisesByContinenteId(10)).thenReturn(List.of(pais1, pais2));

        List<Integer> result = spyService.obtenerIdsPaisesDeObjetivo(1);

        assertEquals(List.of(100, 200), result);
        verify(paisesService).getAllPaisesByContinenteId(10);
    }
}