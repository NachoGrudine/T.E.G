package ar.edu.utn.frc.tup.piii.controllers;

import ar.edu.utn.frc.tup.piii.models.PaisModel;
import ar.edu.utn.frc.tup.piii.services.PaisesService;
import ar.edu.utn.frc.tup.piii.services.interfaces.IPaisesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paises")
public class PaisesController {

    private final IPaisesService paisesService;

    public PaisesController(IPaisesService paisesService) {
        this.paisesService = paisesService;
    }

    @GetMapping
    public List<PaisModel> getAllPaises() {
        return paisesService.getAllPaises();
    }
}
