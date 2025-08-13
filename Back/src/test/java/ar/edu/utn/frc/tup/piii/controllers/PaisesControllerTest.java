package ar.edu.utn.frc.tup.piii.controllers;

import ar.edu.utn.frc.tup.piii.configs.TestSecurityConfig;
import ar.edu.utn.frc.tup.piii.models.PaisModel;
import ar.edu.utn.frc.tup.piii.services.interfaces.IPaisesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaisesController.class)
@Import(TestSecurityConfig.class)
class PaisesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPaisesService paisesService;

    @Test
    void getAllPaisesTest() throws Exception {
        PaisModel pais1 = new PaisModel();;
        pais1.setNombre("Argentina");
        pais1.setId(1);

        PaisModel pais2 = new PaisModel();
        pais2.setNombre("Brasil");
        pais2.setId(2);

        given(paisesService.getAllPaises()).willReturn(List.of(pais1, pais2));

        mockMvc.perform(get("/api/paises"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Argentina"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nombre").value("Brasil"));
    }
}