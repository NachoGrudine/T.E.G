package ar.edu.utn.frc.tup.piii.models;

import ar.edu.utn.frc.tup.piii.entities.Fronteras;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FronteraModel {
    private Integer id;
    private Integer idpais1;
    private Integer idpais2;

    public void mapFronteraModel(Fronteras fronteras) {//MODELADO
        this.id = fronteras.getId();
        this.idpais1 = fronteras.getPais1().getId();
        this.idpais2 = fronteras.getPais2().getId();
    }
}
