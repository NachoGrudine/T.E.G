package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.entities.Refuerzos;
import ar.edu.utn.frc.tup.piii.entities.TurnosRefuerzos;
import ar.edu.utn.frc.tup.piii.repositories.RefuerzosRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RefuerzosServiceTest {
    @Mock
    RefuerzosRepository refuerzosRepository;

    @InjectMocks
    RefuerzosService refuerzosService;

    @Test
    void getAllByTurnoRef() {
        TurnosRefuerzos turnoRefuerzos = new TurnosRefuerzos();
        Refuerzos refuerzos = new Refuerzos();
        List<Refuerzos> refuerzosList = List.of(refuerzos);

        when(refuerzosRepository.getAllByTurnoRef(turnoRefuerzos)).thenReturn(refuerzosList);

        List<Refuerzos> result = refuerzosService.getAllByTurnoRef(turnoRefuerzos);
        assertNotNull(result);
        assertEquals(refuerzosList, result);
        assertEquals(1, result.size());
        verify(refuerzosRepository).getAllByTurnoRef(turnoRefuerzos);
    }

    @Test
    void saveAll() {
        Refuerzos ref1 = new Refuerzos();
        Refuerzos ref2 = new Refuerzos();
        List<Refuerzos> refuerzosList = List.of(ref1, ref2);

        refuerzosService.saveAll(refuerzosList);
        verify(refuerzosRepository).saveAll(refuerzosList);


    }

    @Test
    void saveOne() {
        Refuerzos refuerzo = new Refuerzos();

        refuerzosService.saveOne(refuerzo);
        verify(refuerzosRepository).save(refuerzo);
    }
}