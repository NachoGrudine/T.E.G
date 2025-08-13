package ar.edu.utn.frc.tup.piii.repositories;

import ar.edu.utn.frc.tup.piii.entities.Usuarios;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Debe encontrar un usuario por username")
    void findByUsername() {
        Usuarios usuario = new Usuarios();
        usuario.setUsername("testuser");
        usuario.setPassword("1234");
        usuarioRepository.save(usuario);

        Optional<Usuarios> encontrado = usuarioRepository.findByUsername("testuser");
        assertTrue(encontrado.isPresent());
        assertEquals("testuser", encontrado.get().getUsername());
    }

}