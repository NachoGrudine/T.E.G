package ar.edu.utn.frc.tup.piii.models;

import ar.edu.utn.frc.tup.piii.entities.Usuarios;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UsuarioModelTest {

    @Test
    void mapUsuarioModelTest() {
        Usuarios entity = new Usuarios();
        entity.setId(1);
        entity.setUsername("user");
        entity.setPassword("pass");

        UsuarioModel model = new UsuarioModel();
        model.mapUsuarioModel(entity);

        Assertions.assertEquals(entity.getId(), model.getId());
        Assertions.assertEquals(entity.getUsername(), model.getUsername());
        Assertions.assertEquals(entity.getPassword(), model.getPassword());
    }
}