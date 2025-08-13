package ar.edu.utn.frc.tup.piii.controllers;

import ar.edu.utn.frc.tup.piii.LoginResponseDto;
import ar.edu.utn.frc.tup.piii.entities.Usuarios;
import ar.edu.utn.frc.tup.piii.models.RegistroRequest;
import ar.edu.utn.frc.tup.piii.models.UsuarioModel;
import ar.edu.utn.frc.tup.piii.services.AutenticacionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class AutenticacionControllerTest {

    private AutenticacionService autService;
    private AutenticacionController controller;

    @BeforeEach
    void setUp() {
        autService = Mockito.mock(AutenticacionService.class);
        controller = new AutenticacionController(autService);
    }

    @Test
    void register_ok() throws Exception {
        RegistroRequest req = new RegistroRequest();
        req.setUsername("user");
        req.setPassword("pass");
        req.setRepeatPassword("pass");

        UsuarioModel user = new UsuarioModel("user", "pass");
        Mockito.when(autService.registrarUsuario(any(UsuarioModel.class))).thenReturn(user);

        ResponseEntity<?> response = controller.register(req);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(user, response.getBody());
    }

    @Test
    void register_passwordsNoCoinciden() {
        RegistroRequest req = new RegistroRequest();
        req.setUsername("user");
        req.setPassword("pass1");
        req.setRepeatPassword("pass2");

        ResponseEntity<?> response = controller.register(req);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Las contraseñas no coinciden", response.getBody());
    }

    @Test
    void register_exception() throws Exception {
        RegistroRequest req = new RegistroRequest();
        req.setUsername("user");
        req.setPassword("pass");
        req.setRepeatPassword("pass");

        Mockito.when(autService.registrarUsuario(any(UsuarioModel.class)))
                .thenThrow(new Exception("Error"));

        ResponseEntity<?> response = controller.register(req);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Error", response.getBody());
    }

    @Test
    void login_ok() throws Exception {
        UsuarioModel user = new UsuarioModel("user", "pass");
        // Mock de Usuarios para el constructor de LoginResponseDto
        Usuarios usuarioEntity = Mockito.mock(Usuarios.class);
        Mockito.when(usuarioEntity.getId()).thenReturn(1);
        Mockito.when(usuarioEntity.getUsername()).thenReturn("user");
        LoginResponseDto dto = new LoginResponseDto(usuarioEntity);

        Mockito.when(autService.loginUsuario(any(UsuarioModel.class))).thenReturn(dto);

        ResponseEntity<?> response = controller.login(user);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dto, response.getBody());
    }

    @Test
    void login_exception() throws Exception {
        UsuarioModel user = new UsuarioModel("user", "pass");
        Mockito.when(autService.loginUsuario(any(UsuarioModel.class)))
                .thenThrow(new Exception("Credenciales inválidas"));

        ResponseEntity<?> response = controller.login(user);

        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Credenciales inválidas", response.getBody());
    }
}