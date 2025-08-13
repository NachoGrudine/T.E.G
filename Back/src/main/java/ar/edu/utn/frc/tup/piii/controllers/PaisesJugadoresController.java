package ar.edu.utn.frc.tup.piii.controllers;

import ar.edu.utn.frc.tup.piii.dtos.common.PaisJugadorDto;
import ar.edu.utn.frc.tup.piii.services.PaisesJugadoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/paisesJugadores")
public class PaisesJugadoresController {

    private final PaisesJugadoresService paisesJugadoresService;

    public PaisesJugadoresController(PaisesJugadoresService paisesJugadoresService) {
        this.paisesJugadoresService = paisesJugadoresService;
    }

    @GetMapping
    public PaisJugadorDto getPaisJugador(@RequestParam Integer idPartida, @RequestParam Integer idPais)
    {
        return paisesJugadoresService.getPaisJugadorByIdPartida_Pais(idPartida, idPais);
    }
}
