package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.entities.Canjes;
import ar.edu.utn.frc.tup.piii.entities.TurnosRefuerzos;
import ar.edu.utn.frc.tup.piii.repositories.CanjesRepository;
import ar.edu.utn.frc.tup.piii.services.interfaces.ICanjesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CanjesService implements ICanjesService {
    private final CanjesRepository canjesRepository;

    @Autowired
    public CanjesService(CanjesRepository canjesRepository) {
        this.canjesRepository = canjesRepository;
    }

    @Override
    public List<Canjes> getAllByTurnoRefuerzo(TurnosRefuerzos turnosRefuerzos) {
        return canjesRepository.getAllByTurnoRefuerzo(turnosRefuerzos);
    }
}
