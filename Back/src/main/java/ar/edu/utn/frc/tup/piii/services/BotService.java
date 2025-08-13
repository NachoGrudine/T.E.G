package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.dtos.common.*;
import ar.edu.utn.frc.tup.piii.enums.TipoFicha;
import ar.edu.utn.frc.tup.piii.enums.TipoJugador;
import ar.edu.utn.frc.tup.piii.models.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BotService {
    private final PaisesJugadoresService paisesJugadoresService;
    private final TurnosRefuerzosService turnosRefuerzosService;
    private final FronterasService fronterasService;
    private final ObjetivosCantPaisesService objetivosCantPaisesService;
    private final TurnosAtaquesService turnosAtaquesService;

    public BotService(PaisesJugadoresService paisesJugadoresService, TurnosRefuerzosService turnosRefuerzosService, FronterasService fronterasService, ObjetivosCantPaisesService objetivosCantPaisesService, TurnosAtaquesService turnosAtaquesService) {
        this.paisesJugadoresService = paisesJugadoresService;
        this.turnosRefuerzosService = turnosRefuerzosService;
        this.fronterasService = fronterasService;
        this.objetivosCantPaisesService = objetivosCantPaisesService;
        this.turnosAtaquesService = turnosAtaquesService;
    }

    public void jugarRefuerzo(Long idPartida, Long idRonda, TurnoRefuerzoModel turno, JugadorModel jugador) {
        TipoJugador tipoJugador = jugador.getTipoJugador();
        FichasDTO fichas = turnosRefuerzosService.obtenerFichas(Long.valueOf(turno.getId()));

        switch (tipoJugador){
            case BOT_NOVATO: {
                jugarNovatoTurnoRefuerzo(turno, jugador, fichas);
                break;
            }
            case BOT_BALANCEADO: {
                jugarBalanceadoTurnoRefuerzo(turno, jugador, fichas);
                break;
            }
            case BOT_EXPERTO: {
                jugarExpertoTurnoRefuerzo(turno, jugador, fichas);
                break;
            }
        }

    }

    //NOVATO
    public void jugarNovatoTurnoRefuerzo(TurnoRefuerzoModel turno, JugadorModel jugador,FichasDTO fichas) {

        ponerFichasNovato(jugador.getPaises(), fichas.getFichasPais(), turno.getId() , TipoFicha.DE_PAISES);

        System.out.println(fichas.getFichasPais());

        if(fichas.getFichasContinente() != null){
            System.out.println("Entro aca? why?");
            for (Map.Entry<Integer, Integer> continente : fichas.getFichasContinente().entrySet()) {
                ponerFichasNovato(paisesJugadoresService.getPaisesFromContinenteId(continente.getKey()), continente.getValue(), turno.getId() , TipoFicha.CONTINENTE);
            }
        }
    }

    public void ponerFichasNovato(List<PaisJugadorModel> paises, int cantidad, Integer idTurno, TipoFicha tipoFicha ) {
        int fichasTotal = cantidad;
        List<RefuerzoPostDTO> listaRefuerzos = new ArrayList<>();

        while (fichasTotal!=0) {
            Collections.shuffle(paises);
            int cantidadSumar = 1 + (int) (Math.random() * fichasTotal);
            fichasTotal = fichasTotal - cantidadSumar;

            RefuerzoPostDTO refuerzoDto = new RefuerzoPostDTO();
            refuerzoDto.setCantidad(cantidadSumar);
            refuerzoDto.setIdPais(paises.get(0).getIdPais());
            refuerzoDto.setIdTurnoRef(idTurno);
            refuerzoDto.setTipoFicha(String.valueOf(tipoFicha));

            listaRefuerzos.add(refuerzoDto);
        }
        turnosRefuerzosService.guardarRefuerzos(listaRefuerzos);
    }

    //BALANCEADO
    public void jugarBalanceadoTurnoRefuerzo(TurnoRefuerzoModel turno, JugadorModel jugador,FichasDTO fichas) {

        ponerFichasBalanceado(jugador.getPaises(), fichas.getFichasPais(), turno.getId() , TipoFicha.DE_PAISES, jugador);

        System.out.println(fichas.getFichasPais());

        if(fichas.getFichasContinente() != null){
            System.out.println("Ejecutando reparticion de fichas de continente para jugador balanceado");
            for (Map.Entry<Integer, Integer> continente : fichas.getFichasContinente().entrySet()) {
                ponerFichasBalanceado(paisesJugadoresService.getPaisesFromContinenteId(continente.getKey()), continente.getValue(), turno.getId() , TipoFicha.CONTINENTE, jugador);
            }
        }
    }

    public void ponerFichasBalanceado(List<PaisJugadorModel> paises, int cantidad, Integer idTurno, TipoFicha tipoFicha, JugadorModel jugador) {
        int fichasTotal = cantidad;
        List<RefuerzoPostDTO> listaRefuerzos = new ArrayList<>();
        int vueltas = 0;
        while (fichasTotal!=0) {
            Collections.shuffle(paises);
            int cantidadSumar = 1;


            if(objetivosCantPaisesService.obtenerIdsPaisesDeObjetivo(jugador.getObjetivo().getIdObjetivo()).contains(paises.get(0).getIdPais())){ //SI EL PAIS ESTÁ DENTRO DEL OBJETIVO
                RefuerzoPostDTO refuerzoDto = new RefuerzoPostDTO();
                refuerzoDto.setCantidad(cantidadSumar);
                refuerzoDto.setIdPais(paises.get(0).getIdPais());
                refuerzoDto.setIdTurnoRef(idTurno);
                refuerzoDto.setTipoFicha(String.valueOf(tipoFicha));

                listaRefuerzos.add(refuerzoDto);

                fichasTotal = fichasTotal - cantidadSumar;

            }
            if(fichasTotal > 0){
                if(esPaisFrontera(paises.get(0).getIdPais(), jugador.getPaises())){ //SI EL PAIS ES FRONTERA
                    RefuerzoPostDTO refuerzoDto = new RefuerzoPostDTO();
                    refuerzoDto.setCantidad(cantidadSumar);
                    refuerzoDto.setIdPais(paises.get(0).getIdPais());
                    refuerzoDto.setIdTurnoRef(idTurno);
                    refuerzoDto.setTipoFicha(String.valueOf(tipoFicha));

                    listaRefuerzos.add(refuerzoDto);

                    fichasTotal = fichasTotal - cantidadSumar;
                }

            }
            if(vueltas >= paises.size() && fichasTotal > 0){
                RefuerzoPostDTO refuerzoDto = new RefuerzoPostDTO();
                refuerzoDto.setCantidad(cantidadSumar);
                refuerzoDto.setIdPais(paises.get(0).getIdPais());
                refuerzoDto.setIdTurnoRef(idTurno);
                refuerzoDto.setTipoFicha(String.valueOf(tipoFicha));

                listaRefuerzos.add(refuerzoDto);

                fichasTotal = fichasTotal - cantidadSumar;
            }
            vueltas++;

        }
        turnosRefuerzosService.guardarRefuerzos(listaRefuerzos);
    }

    private boolean esPaisFrontera(Integer id, List<PaisJugadorModel> paisesPropios) {
        List<Integer> idPaisesFronterizos = fronterasService.findAllByPais1_IdOrPais2_Id(id);
        for (Integer idpaisFronterizo : idPaisesFronterizos) {
            if (!paisesJugadoresService.esPropio(paisesPropios, idpaisFronterizo)){
                return true;
            }
        }
        return false;
    }

    //EXPERTO
    public void jugarExpertoTurnoRefuerzo(TurnoRefuerzoModel turno, JugadorModel jugador,FichasDTO fichas) {

        ponerFichasExperto(jugador.getPaises(), fichas.getFichasPais(), turno.getId() , TipoFicha.DE_PAISES, jugador);

        System.out.println(fichas.getFichasPais());

        if(fichas.getFichasContinente() != null){
            System.out.println("Ejecutando reparticion de fichas de continente para jugador experto");
            for (Map.Entry<Integer, Integer> continente : fichas.getFichasContinente().entrySet()) {
                ponerFichasExperto(paisesJugadoresService.getPaisesFromContinenteId(continente.getKey()), continente.getValue(), turno.getId() , TipoFicha.CONTINENTE, jugador);
            }
        }
    }

    public void ponerFichasExperto(List<PaisJugadorModel> paises, int cantidad, Integer idTurno, TipoFicha tipoFicha, JugadorModel jugador) {
        int fichasTotal = cantidad;
        List<RefuerzoPostDTO> listaRefuerzos = new ArrayList<>();
        int vueltas = 0;
        while (fichasTotal!=0 ) {
            Collections.shuffle(paises);
            int cantidadSumar = 1;

            if(objetivosCantPaisesService.obtenerIdsPaisesDeObjetivo(jugador.getObjetivo().getIdObjetivo()).contains(paises.get(0).getIdPais())){
                RefuerzoPostDTO refuerzoDto = new RefuerzoPostDTO();
                refuerzoDto.setCantidad(cantidadSumar);
                refuerzoDto.setIdPais(paises.get(0).getIdPais());
                refuerzoDto.setIdTurnoRef(idTurno);
                refuerzoDto.setTipoFicha(String.valueOf(tipoFicha));

                listaRefuerzos.add(refuerzoDto);
                fichasTotal = fichasTotal - cantidadSumar;

            }

            if(vueltas >= paises.size() && fichasTotal > 0){
                RefuerzoPostDTO refuerzoDto = new RefuerzoPostDTO();
                refuerzoDto.setCantidad(cantidadSumar);
                refuerzoDto.setIdPais(paises.get(0).getIdPais());
                refuerzoDto.setIdTurnoRef(idTurno);
                refuerzoDto.setTipoFicha(String.valueOf(tipoFicha));

                listaRefuerzos.add(refuerzoDto);

                fichasTotal = fichasTotal - cantidadSumar;
            }

            vueltas++;
        }
        turnosRefuerzosService.guardarRefuerzos(listaRefuerzos);
    }

    public void jugarAtaque(Long idPartida, Long idRonda, TurnoAtaqueModel turno, JugadorModel jugador) {
        TipoJugador tipoJugador = jugador.getTipoJugador();

        switch (tipoJugador){
            case BOT_NOVATO: {
                jugarNovatoTurnoAtaque(turno, jugador, idPartida);
                break;
            }
            case BOT_BALANCEADO: {
                jugarBalanceadoTurnoAtaque(turno, jugador,idPartida);
                break;
            }
            case BOT_EXPERTO: {
                jugarExpertoTurnoAtaque(turno, jugador,idPartida);
                break;
            }
        }
    }

    private void jugarNovatoTurnoAtaque(TurnoAtaqueModel turno, JugadorModel jugador, Long idPartida) {
        //trae los paises con el id de ese jugador que tengan mas de 1 ficha
        List<PaisJugadorModel> paisesJugadores = paisesJugadoresService.getPaisesFromJugadorAndFichas(jugador.getId(),1);

        for (PaisJugadorModel pais : paisesJugadores) {
            paisAlAtaqueNovato(pais, idPartida, turno.getId());
        }

    }

    private void paisAlAtaqueNovato(PaisJugadorModel pais, Long idPartida, Integer idTurno) {
        List<Integer> listaModel = fronterasService.findAllByPais1_IdOrPais2_Id(pais.getIdPais());

        for (Integer idPais : listaModel) {
            boolean bandera = true;
            while(bandera){
                PaisJugadorModel paisJugador = paisesJugadoresService.getPaisJugadorByPaisId(pais.getIdPais(), idPartida.intValue());
                PaisJugadorModel paisJugadorFrontera = paisesJugadoresService.getPaisJugadorByPaisId(idPais, idPartida.intValue());

                if(!Objects.equals(paisJugadorFrontera.getIdJugador(), paisJugador.getIdJugador())){
                    if(paisJugadorFrontera.getFichas() <= paisJugador.getFichas() && paisJugador.getFichas() > 1){
                        AccionCombatePostDTO accionCombatePostDTO = new AccionCombatePostDTO();

                        accionCombatePostDTO.setIdPaisAtk(paisJugador.getIdPais());
                        accionCombatePostDTO.setIdPaisDef(paisJugadorFrontera.getIdPais());
                        accionCombatePostDTO.setIdTurno(idTurno);

                        atacar(idTurno, paisJugador, paisJugadorFrontera, accionCombatePostDTO);
                    }
                    else {
                        bandera = false;
                    }
                }
                else {
                    bandera = false;
                }
            }

        }

    }

    private void jugarBalanceadoTurnoAtaque(TurnoAtaqueModel turno, JugadorModel jugador, Long idPartida) {

        List<PaisJugadorModel> paisesJugadores = paisesJugadoresService.getPaisesFromJugadorAndFichas(jugador.getId(), 1);
        Integer idObjetivo = jugador.getObjetivo().getIdObjetivo();
        for (PaisJugadorModel pais : paisesJugadores) {
            paisAlAtaqueBalanceado(pais, idPartida, turno.getId(), idObjetivo);
        }
    }

    private void paisAlAtaqueBalanceado(PaisJugadorModel paisPropio, Long idPartida, Integer idTurno, Integer idObjetivo) {
        List<Integer> idPaisesFronterizos = fronterasService.findAllByPais1_IdOrPais2_Id(paisPropio.getIdPais());
        int ataquesRealizados = 0;
        for (Integer idPais : idPaisesFronterizos) {
            if (ataquesRealizados >= 5) break;
            boolean bandera = true;
            while(bandera){
                PaisJugadorModel paisJugador = paisesJugadoresService.getPaisJugadorByPaisId(paisPropio.getIdPais(), idPartida.intValue());
                PaisJugadorModel paisJugadorFrontera = paisesJugadoresService.getPaisJugadorByPaisId(idPais, idPartida.intValue());

                if(!Objects.equals(paisJugadorFrontera.getIdJugador(), paisJugador.getIdJugador())){
                    if((paisJugadorFrontera.getFichas() * 2) <= paisJugador.getFichas() && paisJugador.getFichas() > 1){
                        AccionCombatePostDTO accionCombatePostDTO = new AccionCombatePostDTO();

                        accionCombatePostDTO.setIdPaisAtk(paisJugador.getIdPais());
                        accionCombatePostDTO.setIdPaisDef(paisJugadorFrontera.getIdPais());
                        accionCombatePostDTO.setIdTurno(idTurno);

                        atacar(idTurno, paisJugador, paisJugadorFrontera, accionCombatePostDTO);
                        ataquesRealizados++;
                        bandera = objetivosCantPaisesService.obtenerIdsPaisesDeObjetivo(idObjetivo).contains(idPais);
                    }
                    else {
                        bandera = false;
                    }
                }
                else {
                    bandera = false;
                }
            }

        }

    }

    private void jugarExpertoTurnoAtaque(TurnoAtaqueModel turno, JugadorModel jugador, Long idPartida) {
        List<PaisJugadorModel> paisesJugadores = paisesJugadoresService.getPaisesFromJugadorAndFichas(jugador.getId(),1);

        for (PaisJugadorModel pais : paisesJugadores) {
            paisAlAtaqueNovato(pais, idPartida, turno.getId());
        }
    }

    private void atacar(Integer idTurno, PaisJugadorModel origen, PaisJugadorModel destino, AccionCombatePostDTO comb) {
        AccionModel accion = turnosAtaquesService.hacerAtaque(comb);

        if (accion.getCombate().getFichasDef() == 0) {
            AccionReagrupacionPostDTO rear = new AccionReagrupacionPostDTO();
            rear.setIdTurno(idTurno);
            rear.setIdPaisOrigen(origen.getIdPais());
            rear.setIdPaisDestino(destino.getIdPais());
            rear.setCantidad(1);
            turnosAtaquesService.hacerReagrupacion(rear);
        }
    }
}