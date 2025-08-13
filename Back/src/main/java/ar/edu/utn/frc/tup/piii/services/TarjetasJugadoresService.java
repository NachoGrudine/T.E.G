package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.entities.Jugadores;
import ar.edu.utn.frc.tup.piii.entities.Tarjetas;
import ar.edu.utn.frc.tup.piii.entities.TarjetasJugadores;
import ar.edu.utn.frc.tup.piii.models.TarjetaJugadorModel;
import ar.edu.utn.frc.tup.piii.repositories.TarjetasJugadoresRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TarjetasJugadoresService {
    private final TarjetasJugadoresRepository tarjetasJugadoresRepository;


    public TarjetasJugadoresService(TarjetasJugadoresRepository tarjetasJugadoresRepository) {
        this.tarjetasJugadoresRepository = tarjetasJugadoresRepository;

    }


    public List<TarjetaJugadorModel> getTarjetasJugadores(Integer idJugador) {

        List<TarjetasJugadores> tarjetasJugadores = this.tarjetasJugadoresRepository.findAllByJugador_Id(idJugador);
        List<TarjetaJugadorModel> tarjetasJugadoresModels = new ArrayList<>();
        for(TarjetasJugadores tarjetasJugador : tarjetasJugadores) {
            TarjetaJugadorModel model = new TarjetaJugadorModel();
            model.mapTarjetaJugadorModel(tarjetasJugador);
            tarjetasJugadoresModels.add(model);
        }
        return tarjetasJugadoresModels;
    }


    public void darTarjeta(Jugadores jugador, Tarjetas tarjeta){
        TarjetasJugadores tarjetasJugador = new TarjetasJugadores();
        tarjetasJugador.setJugador(jugador);
        tarjetasJugador.setTarjeta(tarjeta);
        tarjetasJugadoresRepository.save(tarjetasJugador);
    }
}
