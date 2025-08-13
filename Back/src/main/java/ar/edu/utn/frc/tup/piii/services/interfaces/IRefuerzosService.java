package ar.edu.utn.frc.tup.piii.services.interfaces;

import ar.edu.utn.frc.tup.piii.entities.Refuerzos;
import ar.edu.utn.frc.tup.piii.entities.TurnosRefuerzos;

import java.util.List;

public interface IRefuerzosService {
    List<Refuerzos> getAllByTurnoRef(TurnosRefuerzos turnoRefuerzo);
    public void saveOne(Refuerzos refuerzo);
}
