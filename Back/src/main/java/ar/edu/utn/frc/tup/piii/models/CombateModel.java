package ar.edu.utn.frc.tup.piii.models;

import ar.edu.utn.frc.tup.piii.entities.Combates;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CombateModel {
    private Integer id;
    private Integer idJugadorAtaque;
    private Integer idPaisAtk;
    private Integer fichasAtk;
    private Integer atkDado1;
    private Integer atkDado2;
    private Integer atkDado3;
    private Integer idJugadorDefensa;
    private Integer idPaisDef;
    private Integer fichasDef;
    private Integer defDado1;
    private Integer defDado2;
    private Integer defDado3;

    public void mapCombateModel(Combates combate) {//MODELADO
        this.id = combate.getId();
        this.idJugadorAtaque = combate.getJugadorAtaque().getId();
        this.idPaisAtk = combate.getPaisAtk().getId();
        this.fichasAtk = combate.getFichasAtk();
        this.atkDado1 = combate.getAtkDado1();
        this.atkDado2 = combate.getAtkDado2();
        this.atkDado3 = combate.getAtkDado3();
        this.idJugadorDefensa = combate.getJugadorDefensa().getId();
        this.idPaisDef = combate.getPaisDef().getId();
        this.fichasDef = combate.getFichasDef();
        this.defDado1 = combate.getDefDado1();
        this.defDado2 = combate.getDefDado2();
        this.defDado3 = combate.getDefDado3();
    }
}
