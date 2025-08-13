package ar.edu.utn.frc.tup.piii.models;

import ar.edu.utn.frc.tup.piii.entities.Combates;
import ar.edu.utn.frc.tup.piii.entities.Jugadores;
import ar.edu.utn.frc.tup.piii.entities.Paises;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CombateModelTest {

    @Test
    void mapCombateModelTest() {
        Jugadores atacante = new Jugadores();
        atacante.setId(1);
        Jugadores defensor = new Jugadores();
        defensor.setId(2);
        Paises paisAtk = new Paises();
        paisAtk.setId(3);
        Paises paisDef = new Paises();
        paisDef.setId(4);

        Combates entity = new Combates();
        entity.setId(10);
        entity.setJugadorAtaque(atacante);
        entity.setPaisAtk(paisAtk);
        entity.setFichasAtk(5);
        entity.setAtkDado1(6);
        entity.setAtkDado2(4);
        entity.setAtkDado3(2);
        entity.setJugadorDefensa(defensor);
        entity.setPaisDef(paisDef);
        entity.setFichasDef(3);
        entity.setDefDado1(5);
        entity.setDefDado2(3);
        entity.setDefDado3(1);

        CombateModel model = new CombateModel();
        model.mapCombateModel(entity);

        Assertions.assertEquals(10, model.getId());
        Assertions.assertEquals(1, model.getIdJugadorAtaque());
        Assertions.assertEquals(3, model.getIdPaisAtk());
        Assertions.assertEquals(5, model.getFichasAtk());
        Assertions.assertEquals(6, model.getAtkDado1());
        Assertions.assertEquals(4, model.getAtkDado2());
        Assertions.assertEquals(2, model.getAtkDado3());
        Assertions.assertEquals(2, model.getIdJugadorDefensa());
        Assertions.assertEquals(4, model.getIdPaisDef());
        Assertions.assertEquals(3, model.getFichasDef());
        Assertions.assertEquals(5, model.getDefDado1());
        Assertions.assertEquals(3, model.getDefDado2());
        Assertions.assertEquals(1, model.getDefDado3());
    }
}