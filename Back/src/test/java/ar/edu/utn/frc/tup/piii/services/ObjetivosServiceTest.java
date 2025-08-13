package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.entities.Colores;
import ar.edu.utn.frc.tup.piii.entities.Objetivos;
import ar.edu.utn.frc.tup.piii.models.ObjetivoCantidadPaisesModel;
import ar.edu.utn.frc.tup.piii.models.ObjetivoContModel;
import ar.edu.utn.frc.tup.piii.models.ObjetivoModel;
import ar.edu.utn.frc.tup.piii.repositories.ObjetivosRepository;
import ar.edu.utn.frc.tup.piii.services.interfaces.IObjetivosCantPaisesService;
import ar.edu.utn.frc.tup.piii.services.interfaces.IObjetivosContsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)

public class ObjetivosServiceTest {
    @Mock
    private ObjetivosRepository objetivosRepository;
    @Mock
    private IObjetivosContsService objetivosContsService;

    @Mock
    private IObjetivosCantPaisesService objetivosCantPaisesService;

    @InjectMocks
    private ObjetivosService objetivosService;

    @Test
    public void getAllObjetivosTest()
    {

        //creacion de datos

        Colores color = new Colores();
        color.setColor("rojo");

        Objetivos entity1 = new Objetivos();
        entity1.setId(1);
        entity1.setColor(color);


        List<Objetivos> listaEntity = new ArrayList<>();
        listaEntity.add(entity1);

        //simulamos comportamiento del repository

        when(objetivosRepository.findAll()).thenReturn(listaEntity);

        //simulamos lista q devuelve el service de obj continentes
        Mockito.when(objetivosContsService.findAllByObjetivo_Id(entity1.getId())).thenReturn(List.of(new ObjetivoContModel()));

        //simulamos la lista q devuelve el service de objCantPaises
        Mockito.when(objetivosCantPaisesService.findAllByObjetivo_Id(entity1.getId())).thenReturn(List.of(new ObjetivoCantidadPaisesModel()));

        //se ejecuta el metodo a testear
        List<ObjetivoModel> objetivosModel = objetivosService.getAllObjetivos();

        //verificamos q se llamen
        verify(objetivosRepository).findAll();
        verify(objetivosContsService).findAllByObjetivo_Id(entity1.getId());
        verify(objetivosCantPaisesService).findAllByObjetivo_Id(entity1.getId());

        //acersiones
        assertNotNull(objetivosModel);
        assertEquals(1, objetivosModel.get(0).getObjetivoContModels().size());
        assertEquals(1, objetivosModel.get(0).getObjetivoCantidadPaisesModels().size());

        //testeo el mapeo (probablemente esto no vaya aca y vaya en test de models)
        assertEquals(objetivosModel.get(0).getIdObjetivo(), entity1.getId());
        assertEquals(objetivosModel.get(0).getColorModel().getColor(), entity1.getColor().getColor());

    }

    @Test
    public void getByIdObjetivoTest()
    {
        Colores color = new Colores();
        color.setColor("rojo");

        Objetivos entity1 = new Objetivos();
        entity1.setId(1);
        entity1.setColor(color);


        when(objetivosRepository.findById(Long.valueOf(entity1.getId()))).thenReturn(Optional.of(entity1));
        when(objetivosCantPaisesService.findAllByObjetivo_Id(entity1.getId())).thenReturn(List.of(new ObjetivoCantidadPaisesModel()));
        when(objetivosContsService.findAllByObjetivo_Id(entity1.getId())).thenReturn(List.of(new ObjetivoContModel()));

        ObjetivoModel model = objetivosService.getByIdObjetivo(Long.valueOf(entity1.getId()));


        assertNotNull(model);
        assertEquals(entity1.getId(), model.getIdObjetivo());

    }
}
