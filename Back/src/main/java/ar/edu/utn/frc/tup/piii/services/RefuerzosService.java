package ar.edu.utn.frc.tup.piii.services;

import ar.edu.utn.frc.tup.piii.entities.Refuerzos;
import ar.edu.utn.frc.tup.piii.entities.TurnosRefuerzos;
import ar.edu.utn.frc.tup.piii.repositories.RefuerzosRepository;
import ar.edu.utn.frc.tup.piii.services.interfaces.IRefuerzosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RefuerzosService implements IRefuerzosService {

    private final RefuerzosRepository refuerzosRepository;

    @Autowired
    public RefuerzosService(RefuerzosRepository refuerzosRepository) {
        this.refuerzosRepository = refuerzosRepository;
    }

    @Override
    public List<Refuerzos> getAllByTurnoRef(TurnosRefuerzos turnoRefuerzo) {
        return refuerzosRepository.getAllByTurnoRef(turnoRefuerzo);
    }

    public void saveAll(List<Refuerzos> refuerzos) {
         refuerzosRepository.saveAll(refuerzos);
    }
    public void saveOne(Refuerzos refuerzo) {
        refuerzosRepository.save(refuerzo);
    }
}
