package ar.edu.utn.frc.tup.piii.controllers;

import ar.edu.utn.frc.tup.piii.services.EstadisticasService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
class EstadisticasControllerTest {

    @Mock
    private EstadisticasService estadisticasService;
    @InjectMocks
    private EstadisticasController controller;


    @Test
    void partidasPorUsuario() {
        Map<String, Object> mockResult = new HashMap<>();
        mockResult.put("jugadas", 5L);
        mockResult.put("ganadas", 2L);

        Mockito.when(estadisticasService.partidasPorUsuario(1)).thenReturn(mockResult);

        Map<String, Object> result = controller.partidasPorUsuario(1);

        assertEquals(5L, result.get("jugadas"));
        assertEquals(2L, result.get("ganadas"));
    }

    @Test
    void porcentajeVictorias() {
        Mockito.when(estadisticasService.porcentajeVictorias(1)).thenReturn(40.0);

        Double result = controller.porcentajeVictorias(1);

        assertEquals(40.0, result);
    }

    @Test
    void coloresMasUsados() {
        Map<String, Long> mockResult = new HashMap<>();
        mockResult.put("Rojo", 3L);
        mockResult.put("Azul", 2L);

        Mockito.when(estadisticasService.coloresMasUsados()).thenReturn(mockResult);

        Map<String, Long> result = controller.coloresMasUsados();

        assertEquals(3L, result.get("Rojo"));
        assertEquals(2L, result.get("Azul"));
    }

    @Test
    void objetivosCumplidos() {
        Map<String, Long> mockResult = new HashMap<>();
        mockResult.put("Objetivo 1", 1L);
        mockResult.put("Objetivo 2", 4L);

        Mockito.when(estadisticasService.objetivosCumplidos()).thenReturn(mockResult);

        Map<String, Long> result = controller.objetivosCumplidos();

        assertEquals(1L, result.get("Objetivo 1"));
        assertEquals(4L, result.get("Objetivo 2"));
    }
}