package ar.edu.utn.frc.tup.piii.services.interfaces;

import ar.edu.utn.frc.tup.piii.dtos.common.MensajeDto;
import ar.edu.utn.frc.tup.piii.models.MensajeModel;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IMensajesService {
    List<MensajeModel> getAllMensajes();
    void saveMensaje(MensajeDto mensajeDto);
}
