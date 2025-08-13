package ar.edu.utn.frc.tup.piii.services.interfaces;

import ar.edu.utn.frc.tup.piii.entities.Canjes;
import ar.edu.utn.frc.tup.piii.entities.TurnosRefuerzos;

import java.util.List;

public interface ICanjesService {
    List<Canjes> getAllByTurnoRefuerzo(TurnosRefuerzos turnosRefuerzos);
}
