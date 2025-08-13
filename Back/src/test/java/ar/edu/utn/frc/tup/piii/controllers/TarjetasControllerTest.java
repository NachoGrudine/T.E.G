package ar.edu.utn.frc.tup.piii.controllers;

import ar.edu.utn.frc.tup.piii.configs.TestSecurityConfig;
import ar.edu.utn.frc.tup.piii.models.TarjetaModel;
import ar.edu.utn.frc.tup.piii.services.interfaces.ITarjetasService;
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

@WebMvcTest(TarjetasController.class)
@Import(TestSecurityConfig.class)
class TarjetasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ITarjetasService tarjetasService;

    @Test
    void getTarjetasTest() throws Exception {
        TarjetaModel tarjeta = new TarjetaModel();
        tarjeta.setId(1);
        given(tarjetasService.getAllTarjetas()).willReturn(List.of(tarjeta));

        mockMvc.perform(get("/api/tarjetas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }
}