package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.entities.Colores;
import ar.edu.utn.frc.tup.piii.models.ColorModel;
import ar.edu.utn.frc.tup.piii.repositories.ColoresRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ColoresServiceTest {

    @Mock
    private ColoresRepository coloresRepository;

    @InjectMocks
    private ColoresService coloresService;

    @Test
    void getColorById_returnColorWhenExists() {
        Colores colores = new Colores();
        colores.setId(1);
        colores.setColor("marronazo");

        when(coloresRepository.findById(1L)).thenReturn(Optional.of(colores));
        Colores result = coloresService.getColorById(1L);
        assertEquals("marronazo", result.getColor());

    }
    @Test
    void getColorById_returnColorWhenDoesNotExist() {

        Colores colores = new Colores();
        colores.setId(1);
        colores.setColor("senegales");
        //simulo que tengo un solo color

        //cuando intente buscar un color con el Id 2 me va a devolver un empty(vacio jaja)
        when(coloresRepository.findById(2L)).thenReturn(Optional.empty());

        //esto verifica lanzar la excepcion correcta cuando se llame a getcolorById : 2
        assertThrows(RuntimeException.class, () -> {
            coloresService.getColorById(2L);
        });

    }

    @Test
    void getAllColores_returnListOfAllColors() {
        //bueno creo unos colores que serian los moqueados

        Colores color1 = new Colores();
        color1.setId(1);
        color1.setColor("Aleman");

        Colores color2 = new Colores();
        color2.setId(2);
        color2.setColor("Peruano");


        //meto los colores moqueados en la lista de colores
        List<Colores> coloresList = Arrays.asList(color1, color2);

        //cuando el repositorio busque todos los colores tengo que devolverle la lista de los colores
        when(coloresRepository.findAll()).thenReturn(coloresList);

        //aca voy a guardar el metodo testeado o sea la lista de los colores xd
        List<ColorModel> result = coloresService.getAllColores();



        assertNotNull(result);
        assertEquals(2, result.size()); //tamaño de la lista

        // y aca espero que el color con id 0 se llame aleman , 0 pq la lista arranca de ahi
        assertEquals("Aleman", result.get(0).getColor());
        //lo mismo con el otro color
        assertEquals("Peruano", result.get(1).getColor());

    }
}