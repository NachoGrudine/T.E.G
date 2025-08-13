package ar.edu.utn.frc.tup.piii.controllers;

import ar.edu.utn.frc.tup.piii.models.TarjetaModel;
import ar.edu.utn.frc.tup.piii.services.TarjetasService;
import ar.edu.utn.frc.tup.piii.services.interfaces.ITarjetasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tarjetas")
public class TarjetasController {

    private final ITarjetasService tarjetasService;

    public TarjetasController(ITarjetasService tarjetasService) {
        this.tarjetasService = tarjetasService;
    }

    @GetMapping
    public List<TarjetaModel> getTarjetas() {
        return tarjetasService.getAllTarjetas();
    }
}
