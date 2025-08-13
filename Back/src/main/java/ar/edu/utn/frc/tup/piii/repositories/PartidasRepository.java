package ar.edu.utn.frc.tup.piii.repositories;

import ar.edu.utn.frc.tup.piii.entities.Partidas;
import ar.edu.utn.frc.tup.piii.entities.Usuarios;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartidasRepository  extends JpaRepository<Partidas, Long> {

    List<Partidas> getAllByUsuario(@NotNull Usuarios usuarios);

    Long id(Integer id);

    List<Partidas> findAllByTipoAndEstado(String tipo, String estado);
}
