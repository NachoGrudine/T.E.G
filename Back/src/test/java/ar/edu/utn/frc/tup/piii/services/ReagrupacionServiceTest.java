package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.entities.Paises;
import ar.edu.utn.frc.tup.piii.entities.Reagrupaciones;
import ar.edu.utn.frc.tup.piii.models.PaisJugadorModel;
import ar.edu.utn.frc.tup.piii.repositories.CombatesRepository;
import ar.edu.utn.frc.tup.piii.repositories.ReagrupacionesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReagrupacionServiceTest {

    @Mock
    private ReagrupacionesRepository reagrupacionesRepository;
    @Mock
    private PaisesJugadoresService paisesJugadoresService;
    @Mock
    private PaisesService paisesService;
    @Mock
    private JugadoresService jugadoresService;
    @Mock
    private CombatesRepository combatesRepository;

    @InjectMocks
    private ReagrupacionService reagrupacionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reagrupacionService = new ReagrupacionService(
                combatesRepository,
                reagrupacionesRepository,
                paisesJugadoresService,
                paisesService,
                jugadoresService
        );
    }

    @Test
    void hacerReagrupacion_ok() {
        int idPaisOrigen = 1, idPaisDestino = 2, cantidad = 3, idPartida = 4;

        Paises paisOrigen = new Paises(); paisOrigen.setId((int) idPaisOrigen);
        Paises paisDestino = new Paises(); paisDestino.setId((int) idPaisDestino);

        PaisJugadorModel pjOrigen = new PaisJugadorModel(); pjOrigen.setId(10); pjOrigen.setFichas(5);
        PaisJugadorModel pjDestino = new PaisJugadorModel(); pjDestino.setId(20); pjDestino.setFichas(7);

        when(paisesService.getPaisByIdEntity(1L)).thenReturn(Optional.of(paisOrigen));
        when(paisesService.getPaisByIdEntity(2L)).thenReturn(Optional.of(paisDestino));
        when(paisesJugadoresService.getPaisJugadorByPaisId(idPaisDestino, idPartida)).thenReturn(pjDestino);
        when(paisesJugadoresService.getPaisJugadorByPaisId(idPaisOrigen, idPartida)).thenReturn(pjOrigen);

        Reagrupaciones reagrupacionGuardada = new Reagrupaciones();
        when(reagrupacionesRepository.save(any())).thenReturn(reagrupacionGuardada);

        Reagrupaciones result = reagrupacionService.hacerReagrupacion(idPaisOrigen, idPaisDestino, cantidad, idPartida);

        assertNotNull(result);
        verify(paisesJugadoresService).cambiarFichas(7 + 3, 20L);
        verify(paisesJugadoresService).cambiarFichas(5 - 3, 10L);
        verify(reagrupacionesRepository).save(any(Reagrupaciones.class));
    }

    @Test
    void hacerReagrupacion_paisNoExiste() {
        int idPaisOrigen = 1, idPaisDestino = 2, cantidad = 3, idPartida = 4;

        when(paisesService.getPaisByIdEntity(1L)).thenReturn(Optional.empty());
        when(paisesService.getPaisByIdEntity(2L)).thenReturn(Optional.empty());

        PaisJugadorModel pjOrigen = new PaisJugadorModel(); pjOrigen.setId(10); pjOrigen.setFichas(5);
        PaisJugadorModel pjDestino = new PaisJugadorModel(); pjDestino.setId(20); pjDestino.setFichas(7);

        when(paisesJugadoresService.getPaisJugadorByPaisId(idPaisDestino, idPartida)).thenReturn(pjDestino);
        when(paisesJugadoresService.getPaisJugadorByPaisId(idPaisOrigen, idPartida)).thenReturn(pjOrigen);

        Reagrupaciones reagrupacionGuardada = new Reagrupaciones();
        when(reagrupacionesRepository.save(any())).thenReturn(reagrupacionGuardada);

        Reagrupaciones result = reagrupacionService.hacerReagrupacion(idPaisOrigen, idPaisDestino, cantidad, idPartida);

        assertNotNull(result);
        verify(reagrupacionesRepository).save(any(Reagrupaciones.class));
    }
}