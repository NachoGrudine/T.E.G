package ar.edu.utn.frc.tup.piii.services;


import ar.edu.utn.frc.tup.piii.entities.Combates;
import ar.edu.utn.frc.tup.piii.entities.Paises;
import ar.edu.utn.frc.tup.piii.entities.PaisesJugadores;
import ar.edu.utn.frc.tup.piii.entities.TurnosAtaques;
import ar.edu.utn.frc.tup.piii.models.PaisJugadorModel;
import ar.edu.utn.frc.tup.piii.repositories.CombatesRepository;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class CombatesService {
    private final CombatesRepository combatesRepository;
    private final PaisesJugadoresService paisesJugadoresService;
    private final PaisesService paisesService;
    private final JugadoresService jugadoresService;

    public CombatesService(CombatesRepository combatesRepository, PaisesJugadoresService paisesJugadoresService, PaisesService paisesService, JugadoresService jugadoresService) {
        this.combatesRepository = combatesRepository;
        this.paisesJugadoresService = paisesJugadoresService;
        this.paisesService = paisesService;
        this.jugadoresService = jugadoresService;
    }

    public Combates hacerCombate(Integer idPaisAtaque, Integer idPaisDefensa, Integer idPartida) {
        Combates combate = new Combates();

        Optional<Paises> paisA = paisesService.getPaisByIdEntity(Long.valueOf(idPaisAtaque));
        if (paisA.isPresent()) {
            combate.setPaisAtk(paisA.get());
        }

        PaisJugadorModel paisJugadorAtk = paisesJugadoresService.getPaisJugadorByPaisId(idPaisAtaque, idPartida);
        combate.setJugadorAtaque(jugadoresService.getJugadorByIdEntity(Long.valueOf(paisJugadorAtk.getIdJugador()) ));

        List<Integer> dadosA = tirarDadosAtacante(paisJugadorAtk);

        combate.setAtkDado1(dadosA.get(0));
        combate.setAtkDado2(dadosA.get(1));
        combate.setAtkDado3(dadosA.get(2));

        Optional<Paises> paisD = paisesService.getPaisByIdEntity(Long.valueOf(idPaisDefensa));
        if (paisD.isPresent()) {
            combate.setPaisDef(paisD.get());
        }
        PaisJugadorModel paisJugadorDef = paisesJugadoresService.getPaisJugadorByPaisId(idPaisDefensa, idPartida);
        combate.setJugadorDefensa(jugadoresService.getJugadorByIdEntity(Long.valueOf(paisJugadorDef.getIdJugador()) ));
        List<Integer> dadosD = tirarDadosDefensor(paisJugadorDef);
        combate.setDefDado1(dadosD.get(0));
        combate.setDefDado2(dadosD.get(1));
        combate.setDefDado3(dadosD.get(2));
        int fichasAtk =calcularFichasAtk(dadosA,dadosD, paisJugadorAtk.getFichas());
        combate.setFichasAtk(fichasAtk);
        paisesJugadoresService.cambiarFichas(fichasAtk, Long.valueOf(paisJugadorAtk.getId()));
        int fichasDef = calcularFichasDef(dadosA,dadosD, paisJugadorDef.getFichas());
        combate.setFichasDef(fichasDef);
        paisesJugadoresService.cambiarFichas(fichasDef, Long.valueOf(paisJugadorDef.getId()));
        if (fichasDef == 0){
            paisesJugadoresService.cambiarDuenio(jugadoresService.getJugadorByIdEntity(Long.valueOf(paisJugadorAtk.getIdJugador()) ), Long.valueOf(paisJugadorDef.getId()));
        }

        return combatesRepository.save(combate);
    }

    public List<Integer> tirarDadosAtacante(PaisJugadorModel paisJugador){
        int max = paisJugador.getFichas() - 1;
        if (max > 3){
            max = 3;
        }
        List<Integer> dados = new ArrayList<>(Arrays.asList(0, 0, 0));

        for (int i = 0; i < max; i++) {
            dados.set(i, tirarDado());
        }

        dados.sort(Comparator.reverseOrder());

        return dados;

    }
    public List<Integer> tirarDadosDefensor(PaisJugadorModel paisJugador){
        int max = paisJugador.getFichas();
        if (max > 3){
            max = 3;
        }
        List<Integer> dados = new ArrayList<>(Arrays.asList(0, 0, 0));

        for (int i = 0; i < max; i++) {
            dados.set(i, tirarDado());
        }

        dados.sort(Comparator.reverseOrder());

        return dados;
    }

    public Integer tirarDado(){
        Random random = new Random();
        return random.nextInt(6) + 1;
    }

    public Integer calcularFichasAtk(List<Integer> dadosA, List<Integer> dadosD, int fichas){
        for (int i = 0; i < 3; i++) {
            if (dadosA.get(i) <= dadosD.get(i) && dadosD.get(i) != 0 && dadosA.get(i) != 0){
                fichas--;
            }
        }
        return fichas;
    }

    public Integer calcularFichasDef(List<Integer> dadosA, List<Integer> dadosD, int fichas){
        for (int i = 0; i < 3; i++) {
            if (dadosA.get(i) > dadosD.get(i) && dadosD.get(i) != 0 && dadosA.get(i) != 0){
                fichas--;
            }
        }
        return fichas;
    }

}
