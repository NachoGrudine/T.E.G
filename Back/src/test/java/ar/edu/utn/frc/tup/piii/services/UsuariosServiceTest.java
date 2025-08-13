package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.entities.Usuarios;
import ar.edu.utn.frc.tup.piii.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuariosServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuariosService usuariosService;

    @Test
    void getUsuarioById() {
        Usuarios usuario = new Usuarios();
        usuario.setId(1);
        usuario.setUsername("Colapinto");
        usuario.setPassword("1234");


        when(usuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(usuario));
        Usuarios result = usuariosService.getUsuarioById(1L);
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Colapinto", result.getUsername());
        assertEquals("1234", result.getPassword());
        verify(usuarioRepository).findById(1L);



    }
}