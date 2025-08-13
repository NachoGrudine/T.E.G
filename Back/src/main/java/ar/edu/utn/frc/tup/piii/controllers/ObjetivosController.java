package ar.edu.utn.frc.tup.piii.controllers;

import ar.edu.utn.frc.tup.piii.models.ObjetivoModel;
import ar.edu.utn.frc.tup.piii.services.ObjetivosService;
import ar.edu.utn.frc.tup.piii.services.interfaces.IObjetivosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/objetivos")

public class ObjetivosController {

    private final IObjetivosService objetivosService;

    public ObjetivosController(IObjetivosService objetivosService) {
        this.objetivosService = objetivosService;
    }

    @GetMapping
    public List<ObjetivoModel> getObjetivos() {
        return objetivosService.getAllObjetivos();
    }

    @GetMapping("/{id}")
    public ObjetivoModel getObjetivo(@PathVariable Long id) {
        return objetivosService.getByIdObjetivo(id);
    }
}
