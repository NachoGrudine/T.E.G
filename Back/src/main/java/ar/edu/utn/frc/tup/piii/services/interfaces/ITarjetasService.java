package ar.edu.utn.frc.tup.piii.services.interfaces;

import ar.edu.utn.frc.tup.piii.models.TarjetaModel;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ITarjetasService {
    List<TarjetaModel> getAllTarjetas();
}
