package ar.edu.utn.frc.tup.piii.controllers;

import ar.edu.utn.frc.tup.piii.configs.TestSecurityConfig;
import ar.edu.utn.frc.tup.piii.models.ObjetivoModel;
import ar.edu.utn.frc.tup.piii.services.interfaces.IObjetivosService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ObjetivosController.class)
@Import(TestSecurityConfig.class)
class ObjetivosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IObjetivosService objetivosService;

    @Test
    void getObjetivosTest() throws Exception {
        ObjetivoModel objetivo = new ObjetivoModel();
        objetivo.setIdObjetivo(1);
        given(objetivosService.getAllObjetivos()).willReturn(List.of(objetivo));

        mockMvc.perform(get("/api/objetivos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void getObjetivoByIdTest() throws Exception {
        ObjetivoModel objetivo = new ObjetivoModel();
        objetivo.setIdObjetivo(2);
        given(objetivosService.getByIdObjetivo(2L)).willReturn(objetivo);

        mockMvc.perform(get("/api/objetivos/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L));
    }
}