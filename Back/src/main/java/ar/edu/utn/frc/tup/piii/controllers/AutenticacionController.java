package ar.edu.utn.frc.tup.piii.controllers;


import ar.edu.utn.frc.tup.piii.LoginResponseDto;
import ar.edu.utn.frc.tup.piii.models.RegistroRequest;
import ar.edu.utn.frc.tup.piii.models.UsuarioModel;
import ar.edu.utn.frc.tup.piii.services.AutenticacionService;
import ar.edu.utn.frc.tup.piii.services.interfaces.IAutenticacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class AutenticacionController {

    private final IAutenticacionService autService;

    public AutenticacionController(AutenticacionService autService) {
        this.autService = autService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistroRequest req) {
        if (!req.getPassword().equals(req.getRepeatPassword())) {
            return ResponseEntity.badRequest().body("Las contraseñas no coinciden");
        }
        try {
            UsuarioModel model = new UsuarioModel(req.getUsername(), req.getPassword());
            UsuarioModel registrado = autService.registrarUsuario(model);
            return ResponseEntity.ok(registrado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioModel model) {
        try {
            LoginResponseDto response = autService.loginUsuario(model);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}


