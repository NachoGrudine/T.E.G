package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.entities.Canjes;
import ar.edu.utn.frc.tup.piii.entities.TurnosRefuerzos;
import ar.edu.utn.frc.tup.piii.repositories.CanjesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CanjesServiceTest {

    @Mock
    CanjesRepository canjesRepository;
    @InjectMocks
    CanjesService canjesService;


    @Test
    void getAllByTurnoRefuerzo() {
        TurnosRefuerzos turnosRefuerzos = new TurnosRefuerzos();
        Canjes canjes = new Canjes();
        List<Canjes> canjesList = List.of(canjes);


        when(canjesRepository.getAllByTurnoRefuerzo(turnosRefuerzos)).thenReturn(canjesList);
        List<Canjes> result = canjesService.getAllByTurnoRefuerzo(turnosRefuerzos);

        assertNotNull(result);
        assertEquals(canjesList, result);
        assertEquals(1, result.size());
        verify(canjesRepository).getAllByTurnoRefuerzo(turnosRefuerzos);
    }
}