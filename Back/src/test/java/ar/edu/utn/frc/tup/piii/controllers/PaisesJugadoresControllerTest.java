package ar.edu.utn.frc.tup.piii.controllers;

import ar.edu.utn.frc.tup.piii.configs.TestSecurityConfig;
import ar.edu.utn.frc.tup.piii.dtos.common.FronteraJugadorDto;
import ar.edu.utn.frc.tup.piii.dtos.common.PaisJugadorDto;
import ar.edu.utn.frc.tup.piii.services.PaisesJugadoresService;
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

@WebMvcTest(PaisesJugadoresController.class)
@Import(TestSecurityConfig.class) //para desactivar seguridad en test
class PaisesJugadoresControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaisesJugadoresService paisesJugadoresService;

    @Test
    void getPaisJugadorTest() throws Exception {
        // Preparar DTO con fronteras
        FronteraJugadorDto frontera1 = new FronteraJugadorDto();
        frontera1.setNombreFrontera("PaisFrontera1");
        frontera1.setFichas(3);
        frontera1.setDuenio("Jugador1");

        FronteraJugadorDto frontera2 = new FronteraJugadorDto();
        frontera2.setNombreFrontera("PaisFrontera2");
        frontera2.setFichas(1);
        frontera2.setDuenio("Jugador2");

        PaisJugadorDto dto = new PaisJugadorDto();
        dto.setNombrePaisJugador("PaisPrincipal");
        dto.setFichas(5);
        dto.setDuenio("JugadorPrincipal");
        dto.setFronteras(List.of(frontera1, frontera2));

        //mockear comportamiento del servicio (en controllerss)
        given(paisesJugadoresService.getPaisJugadorByIdPartida_Pais(100, 1)).willReturn(dto);

        //ejecutar y verificar
        mockMvc.perform(get("/api/paisesJugadores")
                        .param("idPartida", "100")
                        .param("idPais", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombrePaisJugador").value("PaisPrincipal"))
                .andExpect(jsonPath("$.fichas").value(5))
                .andExpect(jsonPath("$.duenio").value("JugadorPrincipal"))
                .andExpect(jsonPath("$.fronteras").isArray())
                .andExpect(jsonPath("$.fronteras.length()").value(2))
                .andExpect(jsonPath("$.fronteras[0].nombreFrontera").value("PaisFrontera1"))
                .andExpect(jsonPath("$.fronteras[0].fichas").value(3))
                .andExpect(jsonPath("$.fronteras[0].duenio").value("Jugador1"))
                .andExpect(jsonPath("$.fronteras[1].nombreFrontera").value("PaisFrontera2"))
                .andExpect(jsonPath("$.fronteras[1].fichas").value(1))
                .andExpect(jsonPath("$.fronteras[1].duenio").value("Jugador2"));
    }
}
