package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.dtos.common.MensajeDto;
import ar.edu.utn.frc.tup.piii.entities.Jugadores;
import ar.edu.utn.frc.tup.piii.entities.Mensajes;
import ar.edu.utn.frc.tup.piii.models.MensajeModel;
import ar.edu.utn.frc.tup.piii.repositories.MensajesRepository;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MensajesServiceTest {
    @Mock
    private MensajesRepository mensajesRepository;

    @Mock
    private JugadoresService jugadoresService;

    @InjectMocks
    private MensajesService mensajesService;

    @Test
    public void getAllMensajesTest()
    {
        Jugadores jugador1 = new Jugadores();
        jugador1.setId(1);

        Mensajes entity1 = new Mensajes();
        entity1.setId(1);
        entity1.setMensaje("Carlitos estudioso");
        entity1.setJugador(jugador1);
        entity1.setFechaHora(Instant.now());


        Mensajes entity2 = new Mensajes();
        entity2.setId(2);
        entity2.setMensaje("que feo es javascript");
        entity2.setJugador(jugador1);
        entity2.setFechaHora(Instant.now());

        List<Mensajes> mensajesList = new ArrayList<>();
        mensajesList.add(entity1);
        mensajesList.add(entity2);

        when(mensajesRepository.findAll()).thenReturn(mensajesList);

        List<MensajeModel> models = mensajesService.getAllMensajes();

        assertNotNull(models);
        assertEquals(2, models.size());

    }
    @Test
    public void saveMensajeTest()
    {
        MensajeDto dto =new MensajeDto();
        dto.setMensaje("cavani balon de oro?");
        dto.setIdJugador(1);


        Jugadores jugador = new Jugadores();
        jugador.setId(1);

        when(jugadoresService.getJugadorByIdEntity(Long.valueOf(dto.getIdJugador()))).thenReturn(jugador);

        //accion
        mensajesService.saveMensaje(dto);

        //captor -> basicamente sirve para capturar el mensaje enviado
        ArgumentCaptor<Mensajes> captor = ArgumentCaptor.forClass(Mensajes.class);

        //SI O SI SE VERIFICA SI SE LLAMO AL METODO XQ ESTO ES POST, NO GET
        verify(mensajesRepository).save(captor.capture());

        //obtenemos el valor del msj
        Mensajes guardado = captor.getValue();

        assertNotNull(guardado);
        assertNotEquals("cavani balon de oroo?", guardado.getMensaje());





    }
}
