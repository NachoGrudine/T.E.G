package ar.edu.utn.frc.tup.piii.services.interfaces;

import ar.edu.utn.frc.tup.piii.entities.Usuarios;


public interface IUsuariosService {
    Usuarios getUsuarioById(Long id);
}
