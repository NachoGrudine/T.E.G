package ar.edu.utn.frc.tup.piii.controllers;

import ar.edu.utn.frc.tup.piii.configs.TestSecurityConfig;
import ar.edu.utn.frc.tup.piii.dtos.common.FichasDTO;
import ar.edu.utn.frc.tup.piii.dtos.common.JugadorDTO;
import ar.edu.utn.frc.tup.piii.models.*;
import ar.edu.utn.frc.tup.piii.services.interfaces.IPartidasService;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PartidasController.class)
@Import(TestSecurityConfig.class)
class PartidasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPartidasService partidasService;

    @Test
    void obtenerPartidasPorUsuarioTest() throws Exception {
        PartidaModel partida = new PartidaModel();
        partida.setId(1);
        partida.setCantidadParaGanar(10);

        given(partidasService.getPartidas(5L)).willReturn(List.of(partida));

        mockMvc.perform(get("/api/partidas/usuario/5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].cantidadParaGanar").value(10));
    }
    @Test
    void crearPartidaTest() throws Exception {
        PartidaModel partida = new PartidaModel();
        partida.setId(1);
        given(partidasService.crearPartida(5L)).willReturn(partida);

        mockMvc.perform(post("/api/partidas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }
    @Test
    void configPartidaNotFoundTest() throws Exception {
        doThrow(new EntityNotFoundException()).when(partidasService)
                .configurarPartida(eq(1L), any(), any());

        String body = "{\"objetivo\":1,\"tipo\":\"PUBLICA\"}";
        mockMvc.perform(put("/api/partidas/1/configurar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNotFound());
    }
    @Test
    void configPartidaBadRequestTest() throws Exception {
        doThrow(new IllegalArgumentException()).when(partidasService)
                .configurarPartida(eq(1L), any(), any());

        String body = "{\"objetivo\":1,\"tipo\":\"PUBLICA\"}";
        mockMvc.perform(put("/api/partidas/1/configurar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }
    @Test
    void empezarPartidaBadRequestTest() throws Exception {
        doThrow(new IllegalStateException()).when(partidasService).empezarPartida(2L);

        mockMvc.perform(put("/api/partidas/2/empezar"))
                .andExpect(status().isBadRequest());
    }
    @Test
    void buscarPartidaTest() throws Exception {
        PartidaModel partida = new PartidaModel();
        partida.setId(2);
        partida.setCantidadParaGanar(20);

        given(partidasService.buscarPartida(2L)).willReturn(partida);

        mockMvc.perform(get("/api/partidas/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.cantidadParaGanar").value(20));
    }

    @Test
    void buscarColoresTest() throws Exception {
        ColorModel color = new ColorModel();
        color.setId(1);
        color.setColor("Rojo");

        given(partidasService.buscarColores(3L)).willReturn(List.of(color));

        mockMvc.perform(get("/api/partidas/3/colores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].color").value("Rojo"));
    }

    @Test
    void buscarPaisesTest() throws Exception {
        PaisJugadorModel pais = new PaisJugadorModel();
        pais.setId(1);

        PaisModel paisModel = new PaisModel();

        pais.setIdPais(paisModel.getId());

        given(partidasService.getPaisesFromPartida(4L)).willReturn(List.of(pais));

        mockMvc.perform(get("/api/partidas/4/paises"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));

    }

    @Test
    void buscarUltimaRondaTest() throws Exception {
        RondaModel ronda = new RondaModel();
        ronda.setId(1);
        ronda.setNumero(5);

        given(partidasService.buscarUltimaRonda(10L, 20L)).willReturn(ronda);

        mockMvc.perform(get("/api/partidas/10/rondas/ultima/20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.numero").value(5));
    }




    @Test
    void obtenerPartidasPublicasTest() throws Exception {
        PartidaModel partida = new PartidaModel();
        partida.setId(1);
        given(partidasService.obtenerPartidasPorTipoYEstado()).willReturn(List.of(partida));

        mockMvc.perform(get("/api/partidas/publicas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void empezarPartidaOkTest() throws Exception {
        mockMvc.perform(put("/api/partidas/1/empezar"))
                .andExpect(status().isOk());
    }

    @Test
    void empezarPartidaNotFoundTest() throws Exception {
        doThrow(new EntityNotFoundException()).when(partidasService).empezarPartida(99L);

        mockMvc.perform(put("/api/partidas/99/empezar"))
                .andExpect(status().isNotFound());
    }

    @Test
    void configPartidaOkTest() throws Exception {
        String body = "{\"objetivo\":1,\"tipo\":\"PUBLICA\"}";
        mockMvc.perform(put("/api/partidas/1/configurar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }

    @Test
    void agregarJugadorTest() throws Exception {
        PartidaModel partida = new PartidaModel();
        partida.setId(1);
        JugadorDTO jugador = new JugadorDTO();
        given(partidasService.agregarJugador(eq(1L), any(JugadorDTO.class))).willReturn(partida);

        mockMvc.perform(post("/api/partidas/1/jugadores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":5}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void editarJugadorTest() throws Exception {
        PartidaModel partida = new PartidaModel();
        partida.setId(1);
        given(partidasService.agregarJugador(eq(1L), any(JugadorDTO.class))).willReturn(partida);

        mockMvc.perform(put("/api/partidas/1/jugadores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":5}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void obtenerTurnoRefuerzoOkTest() throws Exception {
        TurnoRefuerzoModel turno = new TurnoRefuerzoModel();
        turno.setId(1);
        given(partidasService.obtenerTurnoRefuerzo(1L)).willReturn(turno);

        mockMvc.perform(get("/api/partidas/turnos-refuerzos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void obtenerTurnoRefuerzoNotFoundTest() throws Exception {
        given(partidasService.obtenerTurnoRefuerzo(2L)).willReturn(null);

        mockMvc.perform(get("/api/partidas/turnos-refuerzos/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void obtenerFichasTest() throws Exception {
        FichasDTO fichas = new FichasDTO();
        fichas.setFichasPais(10);
        given(partidasService.obtenerFichas(1L)).willReturn(fichas);

        mockMvc.perform(get("/api/partidas/turnos-refuerzos/1/fichas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fichasPais").value(10));
    }

    @Test
    void recibirRefuerzosTest() throws Exception {
        mockMvc.perform(post("/api/partidas/turnos-refuerzos/refuerzos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[]"))
                .andExpect(status().isOk());
    }

    @Test
    void terminarTurnoRefuerzoTest() throws Exception {
        mockMvc.perform(get("/api/partidas/1/rondas/2/turnos-refuerzos/3/finalizar"))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerTurnoAtaqueOkTest() throws Exception {
        TurnoAtaqueModel turno = new TurnoAtaqueModel();
        turno.setId(1);
        given(partidasService.obtenerTurnoAtaque(1L)).willReturn(turno);

        mockMvc.perform(get("/api/partidas/turnos-ataques/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void obtenerTurnoAtaqueNotFoundTest() throws Exception {
        given(partidasService.obtenerTurnoAtaque(2L)).willReturn(null);

        mockMvc.perform(get("/api/partidas/turnos-ataques/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void recibirCombateTest() throws Exception {
        AccionModel accion = new AccionModel();
        given(partidasService.hacerAtaque(any())).willReturn(accion);

        mockMvc.perform(post("/api/partidas/turnos-ataque/acciones/combates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void recibirReagrupacionTest() throws Exception {
        AccionModel accion = new AccionModel();
        given(partidasService.hacerReagrupacion(any())).willReturn(accion);

        mockMvc.perform(post("/api/partidas/turnos-ataque/acciones/reagrupaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void terminarTurnoAtaqueTest() throws Exception {
        mockMvc.perform(get("/api/partidas/1/rondas/2/turnos-ataques/3/finalizar"))
                .andExpect(status().isOk());
    }
}