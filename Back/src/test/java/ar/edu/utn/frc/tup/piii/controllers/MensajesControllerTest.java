package ar.edu.utn.frc.tup.piii.controllers;

import ar.edu.utn.frc.tup.piii.dtos.common.MensajeDto;
import ar.edu.utn.frc.tup.piii.models.MensajeModel;
import ar.edu.utn.frc.tup.piii.services.interfaces.IMensajesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class MensajesControllerTest {

    @Mock
    private IMensajesService mensajesService;

    @InjectMocks
    private MensajesController controller;

    @Test
    void getMensajes() {
        MensajeModel m1 = new MensajeModel();
        m1.setId(1);
        m1.setMensaje("Hola");
        MensajeModel m2 = new MensajeModel();
        m2.setId(2);
        m2.setMensaje("Chau");
        List<MensajeModel> mockList = Arrays.asList(m1, m2);

        when(mensajesService.getAllMensajes()).thenReturn(mockList);

        List<MensajeModel> result = controller.getMensajes();

        assertEquals(2, result.size());
        assertEquals("Hola", result.get(0).getMensaje());
        assertEquals("Chau", result.get(1).getMensaje());
        verify(mensajesService, times(1)).getAllMensajes();
    }

    @Test
    void addMensaje() {
        MensajeDto dto = new MensajeDto();
        dto.setIdJugador(1);
        dto.setMensaje("Test");

        controller.addMensaje(dto);

        verify(mensajesService, times(1)).saveMensaje(dto);
    }
}