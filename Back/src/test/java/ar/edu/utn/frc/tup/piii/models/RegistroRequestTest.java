package ar.edu.utn.frc.tup.piii.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistroRequestTest {

    @Test
    void testRegistroRequest() {
        RegistroRequest registro = new RegistroRequest();
        registro.setUsername("usuario");
        registro.setPassword("clave123");
        registro.setRepeatPassword("clave123");

        assertEquals("usuario", registro.getUsername());
        assertEquals("clave123", registro.getPassword());
        assertEquals("clave123", registro.getRepeatPassword());
    }

    @Test
    void testRegistroRequestConstructor() {
        RegistroRequest registro = new RegistroRequest("user", "pass", "pass");
        assertEquals("user", registro.getUsername());
        assertEquals("pass", registro.getPassword());
        assertEquals("pass", registro.getRepeatPassword());
    }
}