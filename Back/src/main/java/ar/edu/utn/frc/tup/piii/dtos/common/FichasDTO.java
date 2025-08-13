package ar.edu.utn.frc.tup.piii.dtos.common;


import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class FichasDTO {

    private int fichasPais;

    private Map<Integer, Integer> fichasTarjeta;

    private Map<Integer, Integer> fichasContinente;


}

