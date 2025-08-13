package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.dtos.common.MensajeDto;
import ar.edu.utn.frc.tup.piii.entities.Mensajes;
import ar.edu.utn.frc.tup.piii.models.MensajeModel;
import ar.edu.utn.frc.tup.piii.repositories.MensajesRepository;
import ar.edu.utn.frc.tup.piii.services.interfaces.IMensajesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class MensajesService implements IMensajesService {

    private final MensajesRepository mensajesRepository;
    private final JugadoresService jugadoresService;

    @Autowired
    public MensajesService(MensajesRepository mensajesRepository, JugadoresService jugadoresService) {
        this.mensajesRepository = mensajesRepository;
        this.jugadoresService = jugadoresService;
    }

    public List<MensajeModel> getAllMensajes() {
        List<Mensajes> mensajesEntity = mensajesRepository.findAll();
        List<MensajeModel> mensajesModel = new ArrayList<MensajeModel>();

        for (Mensajes mensaje : mensajesEntity) {
            MensajeModel model = new MensajeModel();
            model.mapMensajeModel(mensaje);
            mensajesModel.add(model);
        }
        return mensajesModel;
    }

    //POST
    public void saveMensaje(MensajeDto mensajeDto) {
        Mensajes mensajeEntity = new Mensajes();

        mensajeEntity.setMensaje(mensajeDto.getMensaje());
        mensajeEntity.setJugador(jugadoresService.getJugadorByIdEntity(Long.valueOf(mensajeDto.getIdJugador())));
        mensajeEntity.setFechaHora(Instant.now());
        mensajesRepository.save(mensajeEntity);
    }
}
