package ar.edu.utn.frc.tup.piii.models;


import ar.edu.utn.frc.tup.piii.entities.Colores;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColorModel {
    private Integer id;
    private String color;

    public void mapColorModel(Colores color) {//MODELADO
        this.color = color.getColor();
        this.id = color.getId();
    }
}
