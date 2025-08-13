package ar.edu.utn.frc.tup.piii.controllers;

import ar.edu.utn.frc.tup.piii.services.interfaces.IEstadisticasService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticasController {

    private final IEstadisticasService estadisticasService;

    public EstadisticasController(IEstadisticasService estadisticasService) {
        this.estadisticasService = estadisticasService;
    }

    @GetMapping("/partidas/{idUsuario}")
    public Map<String, Object> partidasPorUsuario(@PathVariable Integer idUsuario) {
        return estadisticasService.partidasPorUsuario(idUsuario);
    }

    @GetMapping("/porcentaje/{idUsuario}")
    public Double porcentajeVictorias(@PathVariable Integer idUsuario) {
        return estadisticasService.porcentajeVictorias(idUsuario);
    }

    @GetMapping("/colores")
    public Map<String, Long> coloresMasUsados() {
        return estadisticasService.coloresMasUsados();
    }

    @GetMapping("/objetivos")
    public Map<String, Long> objetivosCumplidos() {
        return estadisticasService.objetivosCumplidos();
    }

}
