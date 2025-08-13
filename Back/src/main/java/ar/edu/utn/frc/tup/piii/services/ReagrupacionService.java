package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.entities.Paises;
import ar.edu.utn.frc.tup.piii.entities.PaisesJugadores;
import ar.edu.utn.frc.tup.piii.entities.Reagrupaciones;
import ar.edu.utn.frc.tup.piii.models.PaisJugadorModel;
import ar.edu.utn.frc.tup.piii.repositories.CombatesRepository;
import ar.edu.utn.frc.tup.piii.repositories.ReagrupacionesRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReagrupacionService {
    private final ReagrupacionesRepository reagrupacionesRepository;
    private final PaisesJugadoresService paisesJugadoresService;
    private final PaisesService paisesService;
    private final JugadoresService jugadoresService;

    public ReagrupacionService(CombatesRepository combatesRepository, ReagrupacionesRepository reagrupacionesRepository, PaisesJugadoresService paisesJugadoresService, PaisesService paisesService, JugadoresService jugadoresService) {
        this.reagrupacionesRepository = reagrupacionesRepository;

        this.paisesJugadoresService = paisesJugadoresService;
        this.paisesService = paisesService;
        this.jugadoresService = jugadoresService;
    }

    public Reagrupaciones hacerReagrupacion(Integer idPaisOrigen, Integer idPaisDestino, Integer cantidad, Integer idPartida) {
        Reagrupaciones reagrupaciones = new Reagrupaciones();
        Optional<Paises> paisorigen = paisesService.getPaisByIdEntity(Long.valueOf(idPaisOrigen));
        Optional<Paises> paisdestino = paisesService.getPaisByIdEntity(Long.valueOf(idPaisDestino));

        if(paisorigen.isPresent() && paisdestino.isPresent()){
            reagrupaciones.setPaisorigen(paisorigen.get());
            reagrupaciones.setPaisdestino(paisdestino.get());
        }


        reagrupaciones.setCantidad(cantidad);

        PaisJugadorModel paisJugadorDestino = paisesJugadoresService.getPaisJugadorByPaisId(idPaisDestino,idPartida);
        paisesJugadoresService.cambiarFichas(paisJugadorDestino.getFichas() + cantidad, Long.valueOf(paisJugadorDestino.getId()) );

        PaisJugadorModel paisJugadorOrigen = paisesJugadoresService.getPaisJugadorByPaisId(idPaisOrigen,idPartida);
        paisesJugadoresService.cambiarFichas(paisJugadorOrigen.getFichas() - cantidad, Long.valueOf(paisJugadorOrigen.getId()) );

        return reagrupacionesRepository.save(reagrupaciones);
    }
}
