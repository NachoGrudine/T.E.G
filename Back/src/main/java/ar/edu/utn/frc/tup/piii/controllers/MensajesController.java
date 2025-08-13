package ar.edu.utn.frc.tup.piii.controllers;

import ar.edu.utn.frc.tup.piii.dtos.common.MensajeDto;
import ar.edu.utn.frc.tup.piii.models.MensajeModel;
import ar.edu.utn.frc.tup.piii.repositories.MensajesRepository;
import ar.edu.utn.frc.tup.piii.services.MensajesService;
import ar.edu.utn.frc.tup.piii.services.interfaces.IMensajesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mensajes")
public class MensajesController {

    private final IMensajesService mensajesService;

    public MensajesController(IMensajesService mensajesService) {
        this.mensajesService = mensajesService;
    }

    @GetMapping
    public List<MensajeModel> getMensajes(){
        return mensajesService.getAllMensajes();
    }

    @PostMapping
    public void addMensaje(@RequestBody MensajeDto mensajeDto){
         mensajesService.saveMensaje(mensajeDto);
    }
}
