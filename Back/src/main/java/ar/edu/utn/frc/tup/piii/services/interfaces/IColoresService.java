package ar.edu.utn.frc.tup.piii.services.interfaces;

import java.util.List;
import ar.edu.utn.frc.tup.piii.entities.Colores;
import ar.edu.utn.frc.tup.piii.models.ColorModel;

public interface IColoresService {
    Colores getColorById(Long id);

    List<ColorModel> getAllColores();
}
