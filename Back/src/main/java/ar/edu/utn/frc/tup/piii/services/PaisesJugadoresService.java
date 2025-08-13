package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.dtos.common.FronteraJugadorDto;
import ar.edu.utn.frc.tup.piii.dtos.common.PaisJugadorDto;
import ar.edu.utn.frc.tup.piii.entities.*;
import ar.edu.utn.frc.tup.piii.models.PaisJugadorModel;
import ar.edu.utn.frc.tup.piii.models.PaisModel;
import ar.edu.utn.frc.tup.piii.repositories.PaisesJugadoresRepository;
import ar.edu.utn.frc.tup.piii.services.interfaces.IPaisesJugadoresService;
import jakarta.servlet.ServletConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PaisesJugadoresService implements IPaisesJugadoresService {

    private final PaisesJugadoresRepository paisesJugadoresRepository;

    private final PaisesService paisesService;
    private final ServletConfig servletConfig;

    private final FronterasService fronterasService;

    @Autowired
    public PaisesJugadoresService(PaisesJugadoresRepository paisesJugadoresRepository , PaisesService paisesService, ServletConfig servletConfig, FronterasService fronterasService) {
        this.paisesJugadoresRepository = paisesJugadoresRepository;
        this.paisesService = paisesService;
        this.servletConfig = servletConfig;
        this.fronterasService = fronterasService;
    }


    @Override
    public void asignarPaisesAJugadores(List<Paises> paises, List<Jugadores> jugadores, Partidas partida) {
        Collections.shuffle(paises);
        Collections.shuffle(jugadores);


        int jugadorIndex = 0;
        int totalJugadores = jugadores.size();

        for (Paises pais : paises) {
            Jugadores jugador = jugadores.get(jugadorIndex);

            PaisesJugadores paisJugador = new PaisesJugadores();
            paisJugador.setJugador(jugador);
            paisJugador.setPartida(partida);
            paisJugador.setFichas(1);
            paisJugador.setPais(pais);

            paisesJugadoresRepository.save(paisJugador);

            jugadorIndex++;
            if (jugadorIndex == totalJugadores) {
                jugadorIndex = 0;
            }
        }
    }

    @Override
    public List<PaisJugadorModel> getPaisesFromJugadorId(Jugadores jugador) {

        List<PaisesJugadores> paisesJugadores = paisesJugadoresRepository.getAllByJugador(jugador);
        List<PaisJugadorModel> paisJugadorModels = new ArrayList<>();
        for(PaisesJugadores paisJugador : paisesJugadores){
            paisJugadorModels.add(mapPaisJugadorModel(paisJugador));
        }
        return paisJugadorModels;
    }

    @Override
    public List<PaisJugadorModel> getPaisesFromContinenteId(Integer continenteId) {

        List<PaisesJugadores> paisesJugadores = paisesJugadoresRepository.getAllByPais_Continente_Id(continenteId);
        List<PaisJugadorModel> paisJugadorModels = new ArrayList<>();
        for(PaisesJugadores paisJugador : paisesJugadores){
            paisJugadorModels.add(mapPaisJugadorModel(paisJugador));
        }
        return paisJugadorModels;
    }



    @Override
    public PaisesJugadores buscarFromJugadorId(Jugadores jugador, Integer idPais) {

        List<PaisesJugadores> paisesJugadores = paisesJugadoresRepository.getAllByJugador(jugador);
        for(PaisesJugadores pais: paisesJugadores){
            if(pais.getPais().getId().equals(idPais)){
                return pais;
            }
        }
        return null;
    }

    @Override
    public Boolean esPropio(List<PaisJugadorModel> paisesPropios,Integer id) {
        for (PaisJugadorModel paisJugador : paisesPropios) {
            if (paisJugador.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Integer> getIdsPaisesByPlayer(Integer id){
        List<PaisesJugadores> paisesJugadores = paisesJugadoresRepository.getAllByJugadorId(id);
        List<Integer> idsPaises = new ArrayList<>();
        for(PaisesJugadores paisJugador : paisesJugadores){
            idsPaises.add(paisJugador.getId());
        }
        return idsPaises;

    }

    public PaisJugadorModel getPaisJugadorByPaisId(Integer idPais, Integer idPartida){

        PaisesJugadores paisJugador = paisesJugadoresRepository.findByPartida_IdAndPais_Id(idPartida, idPais);

        return mapPaisJugadorModel(paisJugador);
    }

    @Override
    public PaisJugadorDto getPaisJugadorByIdPartida_Pais(Integer idPartida, Integer idPais) {
        PaisJugadorDto paisJugadorDto = new PaisJugadorDto();

        //nombre del pais atraves del idPais de pais
        PaisModel paisModel = paisesService.getPaisById(idPais);
        paisJugadorDto.setNombrePaisJugador(paisModel.getNombre());

        PaisesJugadores paisesJugadoress = paisesJugadoresRepository.findByPartida_IdAndPais_Id(idPartida, idPais);

        paisJugadorDto.setFichas(paisesJugadoress.getFichas());
        paisJugadorDto.setDuenio(paisesJugadoress.getJugador().getNombre());
        paisJugadorDto.setIdDuenio(paisesJugadoress.getJugador().getId());
        paisJugadorDto.setIdPais(paisesJugadoress.getPais().getId());


        List<Integer> fronteras = fronterasService.findAllByPais1_IdOrPais2_Id(idPais);
        List<FronteraJugadorDto> fronteraJugadorDtos = new ArrayList<>();

        for(Integer fronteraId : fronteras){
            FronteraJugadorDto fronteraJugadorDto = new FronteraJugadorDto();

            PaisModel paisModel1 = paisesService.getPaisById(fronteraId);
            fronteraJugadorDto.setNombreFrontera(paisModel1.getNombre());

            PaisesJugadores paisesJugadores = paisesJugadoresRepository.findByPartida_IdAndPais_Id(idPartida, fronteraId);
            fronteraJugadorDto.setFichas(paisesJugadores.getFichas());
            fronteraJugadorDto.setDuenio(paisesJugadores.getJugador().getNombre());
            fronteraJugadorDto.setIdDuenio(paisesJugadores.getJugador().getId());
            fronteraJugadorDto.setColor(paisesJugadores.getJugador().getColor().getColor());
            fronteraJugadorDto.setIdPais(paisesJugadores.getPais().getId());

            fronteraJugadorDtos.add(fronteraJugadorDto);
        }
        paisJugadorDto.setFronteras(fronteraJugadorDtos);
        return paisJugadorDto;

    }

    @Override
    public void save(PaisesJugadores pais) {
        paisesJugadoresRepository.save(pais);

    }

    public void cambiarFichas(Integer nuevaCantidad, Long idPaisJugador){
        Optional<PaisesJugadores> pais = paisesJugadoresRepository.findById(idPaisJugador.intValue());
        if(pais.isPresent()){
            pais.get().setFichas(nuevaCantidad);
            paisesJugadoresRepository.save(pais.get());
        }
    }

    public void cambiarDuenio(Jugadores jugador, Long idPaisJugador){
        Optional<PaisesJugadores> pais = paisesJugadoresRepository.findById(idPaisJugador.intValue());
        if(pais.isPresent()){
            pais.get().setJugador(jugador);
            paisesJugadoresRepository.save(pais.get());
        }
    }


    public PaisJugadorModel mapPaisJugadorModel (PaisesJugadores paisJugador) {
        PaisJugadorModel paisJugadorModel = new PaisJugadorModel();
        paisJugadorModel.mapPaisJugadorModel(paisJugador);
        return paisJugadorModel;
    }

    @Override
    public  List<PaisJugadorModel> getPaisesFromPartidaId(Partidas partida) {

        List<PaisesJugadores> paises = paisesJugadoresRepository.getAllByPartida(partida);
        List<PaisJugadorModel> paisJugadorModels = new ArrayList<>();
        for(PaisesJugadores paisJugador : paises){
            paisJugadorModels.add(mapPaisJugadorModel(paisJugador));
        }
        return paisJugadorModels;
    }


    @Override
    public List<PaisJugadorModel> getPaisesFromJugadorAndFichas(Integer id, Integer cantidad) {

        List<PaisesJugadores> paisesJugadores = paisesJugadoresRepository.getAllByJugador_IdAndFichasGreaterThan(id, cantidad);
        List<PaisJugadorModel> paisJugadorModels = new ArrayList<>();
        for(PaisesJugadores paisJugador : paisesJugadores){
            paisJugadorModels.add(mapPaisJugadorModel(paisJugador));
        }
        return paisJugadorModels;
    }

}