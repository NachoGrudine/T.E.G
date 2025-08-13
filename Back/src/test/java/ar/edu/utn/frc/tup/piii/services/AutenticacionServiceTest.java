package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.LoginResponseDto;
import ar.edu.utn.frc.tup.piii.entities.Usuarios;
import ar.edu.utn.frc.tup.piii.models.UsuarioModel;
import ar.edu.utn.frc.tup.piii.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
@ExtendWith(MockitoExtension.class)
class AutenticacionServiceTest {


    @Mock
    UsuarioRepository usuarioRepository;


    @Mock
    BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    AutenticacionService autenticacionService;

    @InjectMocks
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Test
    void registrarUsuario_exitoso() throws Exception {
        UsuarioModel model = new UsuarioModel();
        model.setUsername("santi");
        model.setPassword("123");


       when(usuarioRepository.findByUsername("santi")).thenReturn(Optional.empty());

        when(passwordEncoder.encode("123")).thenReturn("hash123");

        Usuarios userSaved = new Usuarios();
        userSaved.setUsername("santi");
        userSaved.setPassword("has123");
        userSaved.setId(1);

        when(usuarioRepository.save(any(Usuarios.class))).thenReturn(userSaved);

        UsuarioModel resultao = autenticacionService.registrarUsuario(model);

        assertEquals("santi", resultao.getUsername());
        assertEquals("has123", resultao.getPassword());

    }

    @Test
    void loginUsuario() throws Exception {
        //Usuario que entra en el login
        UsuarioModel model = new UsuarioModel();
        model.setUsername("santi");
        model.setPassword("123");

        //usuario que creo para testear falso
        Usuarios userSaved = new Usuarios();
        userSaved.setUsername("santi");
        userSaved.setPassword("hash123");
        userSaved.setId(1);

        // cuando el repositorio busque "santi" va a devolver el usuario falso
        when(usuarioRepository.findByUsername("santi")).thenReturn(Optional.of(userSaved));
        //comparo la contraseña que tengo mockeada(user en la bd: hash123) con el usuairo que entra que sera 123
        when(passwordEncoder.matches("123","hash123")).thenReturn(true);

        //llamo al login y le paso mi usuario de entrada (model) que para que despues lo pueda comparar con mi usuario fake que seria el user en la BD
        LoginResponseDto result = autenticacionService.loginUsuario(model);

        assertNotNull(result);
        //aca verifico si el usuario mockeado tiene el mismo nombre que el usuario que se intenta loguear
        assertEquals("santi", result.getUsername());



    }



    }
