package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.entities.Colores;
import ar.edu.utn.frc.tup.piii.models.ColorModel;
import ar.edu.utn.frc.tup.piii.repositories.ColoresRepository;
import ar.edu.utn.frc.tup.piii.services.interfaces.IColoresService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Id;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ColoresService implements IColoresService {
    private final ColoresRepository coloresRepository;

    public ColoresService(ColoresRepository coloresRepository) {
        this.coloresRepository = coloresRepository;
    }

    @Override
    public Colores getColorById(Long id) {
        return coloresRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new EntityNotFoundException("Color no encontrado"));
    }

    @Override
    public List<ColorModel> getAllColores() {
        List<Colores> colores = coloresRepository.findAll();
        List<ColorModel> coloresModel = new ArrayList<>();
        for(Colores color : colores) {
            ColorModel colorModel = new ColorModel();
            colorModel.mapColorModel(color);
            coloresModel.add(colorModel);
        }
        return coloresModel;
    }
}
